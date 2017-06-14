package de.linzn.cubit.internal.databaseMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.linzn.cubit.internal.databaseMgr.engine.SQLIEngine;

public class DatabaseInputOutput {

	private SQLIEngine engine;

	public DatabaseInputOutput(SQLIEngine engine) {
		this.engine = engine;
		createTables();
	}

	private void createTables() {
		String table1 = "CREATE TABLE IF NOT EXISTS offerManager (Id INTEGER PRIMARY KEY   AUTOINCREMENT, regionID text, world text, uuid text, value double);";
		String table2 = "CREATE TABLE IF NOT EXISTS uuidcache (Id INTEGER PRIMARY KEY   AUTOINCREMENT, UUID text, NAME text, TIMESTAMP bigint);";
		if (this.engine.useSQL()) {
			table1 = "CREATE TABLE IF NOT EXISTS offerManager (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));";
			table2 = "CREATE TABLE IF NOT EXISTS uuidcache (Id int NOT NULL AUTO_INCREMENT, UUID text, NAME text, TIMESTAMP bigint, PRIMARY KEY (id));";
		}
		String[] tableArray = { table1, table2 };
		this.engine.setupSQLI(tableArray);
	}

	// Input

	public boolean set_create_offer(OfferData data) {
		try {
			ResultSet result = this.engine.runQueryResult("SELECT value FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld().getName().toLowerCase() + "';");
			String uuidData = "NULL";
			if (data.getPlayerUUID() != null) {
				uuidData = data.getPlayerUUID().toString();
			}
			if (result.next()) {
				this.engine.runUpdateTask("UPDATE offerManager SET value = '" + data.getValue() + "', uuid = '"
						+ uuidData + "' WHERE regionID = '" + data.getRegionID() + "' AND world = '"
						+ data.getWorld().getName().toLowerCase() + "';");
			} else {
				this.engine.runUpdateTask("INSERT INTO offerManager (regionID, value, world, uuid) VALUES ('"
						+ data.getRegionID() + "', '" + data.getValue() + "', '"
						+ data.getWorld().getName().toLowerCase() + "', '" + uuidData + "');");
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean set_remove_offer(String regionID, World world) {
		try {
			ResultSet result = this.engine.runQueryResult("SELECT value FROM offerManager WHERE regionID = '" + regionID
					+ "' AND world = '" + world.getName() + "';");

			if (result.next()) {
				this.engine.runUpdateTask("DELETE FROM offerManager WHERE regionID = '" + regionID + "' AND world = '"
						+ world.getName() + "';");
			}
			result.close();

		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean set_update_profile(UUID uuid, String player, long time) {

		try {
			ResultSet result = this.engine.runQueryResult("SELECT NAME FROM uuidcache WHERE UUID = '" + uuid + "';");

			if (result.next()) {

				this.engine.runUpdateTask("UPDATE uuidcache SET NAME = '" + player + "', TIMESTAMP = '" + time
						+ "' WHERE UUID = '" + uuid.toString() + "';");
			} else {
				this.engine.runUpdateTask("INSERT INTO uuidcache (UUID, NAME, TIMESTAMP) VALUES ('" + uuid.toString()
						+ "', '" + player + "', '" + time + "');");

			}
			result.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Output

	public long get_last_login_profile(UUID uuid) {
		long lastlogin = 0;
		try {
			ResultSet result = this.engine
					.runQueryResult("SELECT TIMESTAMP FROM uuidcache WHERE UUID = '" + uuid + "';");
			if (result.next()) {
				lastlogin = result.getLong(1);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastlogin;

	}

	public String get_profile_name(UUID uuid) {
		String name = null;
		try {
			ResultSet result = this.engine.runQueryResult("SELECT NAME FROM uuidcache WHERE UUID = '" + uuid + "';");
			if (result.next()) {
				name = result.getString(1);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;

	}

	public long get_last_login_profile(String p) {
		@SuppressWarnings("deprecation")
		UUID uuid = Bukkit.getOfflinePlayer(p).getUniqueId();
		return get_last_login_profile(uuid);

	}

	public OfferData get_offer(String regionID, World world) {
		OfferData data = null;
		try {
			ResultSet result = this.engine.runQueryResult("SELECT value, uuid FROM offerManager WHERE regionID = '"
					+ regionID + "' AND world = '" + world.getName().toLowerCase() + "';");

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
		return data;
	}

	public boolean get_is_offer(String regionID, World world) {
		boolean isoffered = false;
		try {
			ResultSet result = this.engine.runQueryResult("SELECT uuid FROM offerManager WHERE regionID = '" + regionID
					+ "' AND world = '" + world.getName() + "';");

			if (result.next()) {
				isoffered = true;
			}
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isoffered;
	}

}
