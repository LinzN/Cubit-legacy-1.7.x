/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
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

public class MySqlType implements DatabaseType {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    private String offerDatabase = "offerManager";
    private String uuidCacheDatabase = "uuidcache";

    @Override
    public boolean setupDatabase() {
        this.database = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlDataBase;
        this.port = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlPort;
        this.host = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlHostname;
        this.username = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlUser;
        this.password = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlPassword;
        // TODO Auto-generated method stub

        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            if (CubitBukkitPlugin.inst().getYamlManager().getSettings().useMineSuite && CubitBukkitPlugin.inst().getYamlManager().getSettings().useMineSuiteDatabase) {
                CubitBukkitPlugin.inst().getLogger().info("Use MineSuite integration");
                offerDatabase = "module_cubit_offerdata";
                uuidCacheDatabase = "core_uuidcache";
            }
            state.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " + offerDatabase + " (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));");
            state.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " + uuidCacheDatabase + " (Id int NOT NULL AUTO_INCREMENT, UUID text, NAME text, TIMESTAMP bigint, PRIMARY KEY (id));");


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
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + database + "?autoReconnect=true&useSSL=false",
                    this.username, this.password);
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
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean set_create_offer(OfferData data) {
        // TODO Auto-generated method stub
        Connection con = this.createConnection();
        try {
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT value FROM " + offerDatabase + " WHERE regionID = '"
                    + data.getRegionID() + "' AND world = '" + data.getWorld().getName().toLowerCase() + "';");
            String uuidData = "NULL";
            if (data.getPlayerUUID() != null) {
                uuidData = data.getPlayerUUID().toString();
            }
            if (result.next()) {
                state.executeUpdate("UPDATE " + offerDatabase + " SET value = '" + data.getValue() + "', uuid = '" + uuidData
                        + "' WHERE regionID = '" + data.getRegionID() + "' AND world = '"
                        + data.getWorld().getName().toLowerCase() + "';");
            } else {
                state.executeUpdate("INSERT INTO " + offerDatabase + " (regionID, value, world, uuid) VALUES ('"
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
            ResultSet result = state.executeQuery("SELECT value FROM " + offerDatabase + " WHERE regionID = '" + regionID
                    + "' AND world = '" + world.getName() + "';");

            if (result.next()) {
                state.executeUpdate("DELETE FROM " + offerDatabase + " WHERE regionID = '" + regionID + "' AND world = '"
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
            ResultSet result = state.executeQuery("SELECT NAME FROM " + uuidCacheDatabase + " WHERE UUID = '" + uuid + "';");

            if (result.next()) {

                state.executeUpdate("UPDATE " + uuidCacheDatabase + " SET NAME = '" + player + "', TIMESTAMP = '" + time
                        + "' WHERE UUID = '" + uuid.toString() + "';");
            } else {
                state.executeUpdate("INSERT INTO " + uuidCacheDatabase + " (UUID, NAME, TIMESTAMP) VALUES ('" + uuid.toString() + "', '"
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
            ResultSet result = state.executeQuery("SELECT TIMESTAMP FROM " + uuidCacheDatabase + " WHERE UUID = '" + uuid + "';");
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
            ResultSet result = state.executeQuery("SELECT NAME FROM " + uuidCacheDatabase + " WHERE UUID = '" + uuid + "';");
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
            ResultSet result = state.executeQuery("SELECT value, uuid FROM " + offerDatabase + " WHERE regionID = '" + regionID
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
            ResultSet result = state.executeQuery("SELECT uuid FROM " + offerDatabase + " WHERE regionID = '" + regionID
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
