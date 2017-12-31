/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.dataAccessMgr.types;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dataAccessMgr.OfferData;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SqliteType implements DatabaseType {

    private String databasePath;

    public SqliteType() {
        this.databasePath = CubitBukkitPlugin.inst().getDataFolder().getAbsolutePath() + "/database.db";
    }

    @Override
    public boolean setupDatabase() {
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            state.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS offerManager (Id INTEGER PRIMARY KEY   AUTOINCREMENT, regionID text, world text, uuid text, value double);");
            state.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS uuidcache (Id INTEGER PRIMARY KEY   AUTOINCREMENT, UUID text, NAME text, TIMESTAMP bigint);");
            state.close();
            return this.releaseConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection createConnection() {
        Connection con;
        // TODO Auto-generated method stub
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + this.databasePath);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return con;
    }

    @Override
    public boolean releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean set_create_offer(OfferData data) {
        // TODO Auto-generated method stub
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT value FROM offerManager WHERE regionID = '"
                    + data.getRegionID() + "' AND world = '" + data.getWorld().getName().toLowerCase() + "';");
            String uuidData = "NULL";
            if (data.getPlayerUUID() != null) {
                uuidData = data.getPlayerUUID().toString();
            }
            if (result.next()) {
                state.executeUpdate("UPDATE offerManager SET value = '" + data.getValue() + "', uuid = '" + uuidData
                        + "' WHERE regionID = '" + data.getRegionID() + "' AND world = '"
                        + data.getWorld().getName().toLowerCase() + "';");
            } else {
                state.executeUpdate("INSERT INTO offerManager (regionID, value, world, uuid) VALUES ('"
                        + data.getRegionID() + "', '" + data.getValue() + "', '"
                        + data.getWorld().getName().toLowerCase() + "', '" + uuidData + "');");
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        this.releaseConnection(con);
        return true;
    }

    @Override
    public boolean set_remove_offer(String regionID, World world) {
        // TODO Auto-generated method stub
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT value FROM offerManager WHERE regionID = '" + regionID
                    + "' AND world = '" + world.getName() + "';");

            if (result.next()) {
                state.executeUpdate("DELETE FROM offerManager WHERE regionID = '" + regionID + "' AND world = '"
                        + world.getName() + "';");
            }
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        this.releaseConnection(con);
        return true;
    }

    @Override
    public boolean set_update_profile(UUID uuid, String player, long time) {
        // TODO Auto-generated method stub
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT NAME FROM uuidcache WHERE UUID = '" + uuid + "';");

            if (result.next()) {

                state.executeUpdate("UPDATE uuidcache SET NAME = '" + player + "', TIMESTAMP = '" + time
                        + "' WHERE UUID = '" + uuid.toString() + "';");
            } else {
                state.executeUpdate("INSERT INTO uuidcache (UUID, NAME, TIMESTAMP) VALUES ('" + uuid.toString() + "', '"
                        + player + "', '" + time + "');");

            }
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        this.releaseConnection(con);
        return true;
    }

    @Override
    public long get_last_login_profile(UUID uuid) {
        // TODO Auto-generated method stub
        long lastlogin = CubitBukkitPlugin.inst().getYamlManager().getSettings().cubitSetupDate;
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT TIMESTAMP FROM uuidcache WHERE UUID = '" + uuid + "';");
            if (result.next()) {
                lastlogin = result.getLong(1);
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.releaseConnection(con);
        return lastlogin;
    }

    @Override
    public String get_profile_name(UUID uuid) {
        // TODO Auto-generated method stub
        String name = null;
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT NAME FROM uuidcache WHERE UUID = '" + uuid + "';");
            if (result.next()) {
                name = result.getString(1);
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.releaseConnection(con);
        return name;
    }

    @Override
    public long get_last_login_profile(String p) {
        // TODO Auto-generated method stub
        @SuppressWarnings("deprecation")
        UUID uuid = Bukkit.getOfflinePlayer(p).getUniqueId();
        return get_last_login_profile(uuid);
    }

    @Override
    public OfferData get_offer(String regionID, World world) {
        // TODO Auto-generated method stub
        OfferData data = null;
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT value, uuid FROM offerManager WHERE regionID = '" + regionID
                    + "' AND world = '" + world.getName().toLowerCase() + "';");

            if (result.next()) {
                double value = result.getDouble(1);
                UUID playerUUID = null;
                if (!result.getString(2).equalsIgnoreCase("NULL")) {
                    playerUUID = UUID.fromString(result.getString(2));
                }
                data = new OfferData(regionID, world);
                data.setPlayerUUID(playerUUID);
                data.setValue(value);
            }
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.releaseConnection(con);
        return data;
    }

    @Override
    public boolean get_is_offer(String regionID, World world) {
        // TODO Auto-generated method stub
        boolean isoffered = false;
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT uuid FROM offerManager WHERE regionID = '" + regionID
                    + "' AND world = '" + world.getName() + "';");

            if (result.next()) {
                isoffered = true;
            }
            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.releaseConnection(con);
        return isoffered;
    }

    @Override
    public String get_formate_date(long date) {
        // TODO Auto-generated method stub
        return new SimpleDateFormat("dd.MM.yyyy '-' HH:mm 'Uhr'").format(new Date(date));
    }

}
