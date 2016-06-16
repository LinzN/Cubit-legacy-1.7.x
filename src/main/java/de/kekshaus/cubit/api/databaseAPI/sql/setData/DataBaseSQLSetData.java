package de.kekshaus.cubit.api.databaseAPI.sql.setData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.api.databaseAPI.sql.handler.DataBaseSQLConnectionManager;

public class DataBaseSQLSetData {

	public void setOffer(OfferData data) {
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT value FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld().getName().toLowerCase() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement("UPDATE offerManager SET value = '" + data.getValue()
						+ "', uuid = '" + data.getPlayerUUID().toString() + "' WHERE regionID = '" + data.getRegionID()
						+ "' AND world = '" + data.getWorld().getName().toLowerCase() + "';");
				update.executeUpdate();
				update.close();
			} else {
				PreparedStatement insert = conn.prepareStatement(
						"INSERT INTO offerManager (regionID, value, world, uuid) VALUES ('" + data.getRegionID()
								+ "', '" + data.getValue() + "', '" + data.getWorld().getName().toLowerCase() + "', '"
								+ data.getPlayerUUID().toString() + "');");
				insert.executeUpdate();
				insert.close();
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void removeOffer(String regionID, String world) {
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement(
					"SELECT value FROM offerManager WHERE regionID = '" + regionID + "' AND world = '" + world + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement(
						"DELETE FROM offerManager WHERE regionID = '" + regionID + "' AND world = '" + world + "';");
				update.executeUpdate();
				update.close();
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean updateProfile(UUID uuid, String player, long time) {
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT NAME FROM uuidcache WHERE UUID = '" + uuid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {

				PreparedStatement sql2 = conn.prepareStatement("UPDATE uuidcache SET NAME = '" + player
						+ "', TIMESTAMP = '" + time + "' WHERE UUID = '" + uuid.toString() + "';");
				sql2.executeUpdate();
				sql2.close();
			} else {
				PreparedStatement sql2 = conn.prepareStatement("INSERT INTO uuidcache (UUID, NAME, TIMESTAMP) VALUES ('"
						+ uuid.toString() + "', '" + player + "', '" + time + "');");
				sql2.executeUpdate();
				sql2.close();
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
