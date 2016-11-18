package de.kekshaus.cubit.api.databaseAPI.sql.getData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.api.databaseAPI.sql.handler.DataBaseSQLConnectionManager;

public class DataBaseSQLGetData {

	public DataBaseSQLGetData() {

	}

	public long getTimeStamp(UUID uuid) {
		long lastlogin = 0;
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitplugin");
			PreparedStatement sql = conn
					.prepareStatement("SELECT TIMESTAMP FROM uuidcache WHERE UUID = '" + uuid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				lastlogin = result.getLong(1);
			}
			result.close();
			sql.close();
			manager.release("cubitplugin", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastlogin;

	}

	public long getTimeStamp(String p) {
		@SuppressWarnings("deprecation")
		UUID uuid = Bukkit.getOfflinePlayer(p).getUniqueId();
		return getTimeStamp(uuid);

	}

	public OfferData getOfferData(String regionID, World world) {
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		OfferData data = null;
		try {
			Connection conn = manager.getConnection("cubitplugin");
			PreparedStatement sql = conn.prepareStatement("SELECT value, uuid FROM offerManager WHERE regionID = '"
					+ regionID + "' AND world = '" + world.getName().toLowerCase() + "';");
			ResultSet result = sql.executeQuery();
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
			sql.close();
			manager.release("cubitplugin", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean isOffered(String regionID, String world) {
		boolean isoffered = false;
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitplugin");
			PreparedStatement sql = conn.prepareStatement(
					"SELECT uuid FROM offerManager WHERE regionID = '" + regionID + "' AND world = '" + world + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				isoffered = true;
			}
			result.close();
			sql.close();
			manager.release("cubitplugin", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isoffered;
	}

}
