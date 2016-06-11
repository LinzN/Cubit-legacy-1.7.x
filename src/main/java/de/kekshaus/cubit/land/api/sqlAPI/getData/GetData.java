package de.kekshaus.cubit.land.api.sqlAPI.getData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.kekshaus.cubit.land.api.sqlAPI.handler.ConnectionManager;
import de.kekshaus.cubit.land.api.sqlAPI.handler.OfferData;

public class GetData {

	public GetData() {

	}

	public long getTimeStamp(UUID uuid) {
		long lastlogin = 0;
		ConnectionManager manager = ConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn
					.prepareStatement("SELECT TIMESTAMP FROM uuidcache WHERE UUID = '" + uuid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				lastlogin = result.getLong(1);
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);
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

	public OfferData getOfferData(OfferData data) {
		ConnectionManager manager = ConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT value, uuid FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				double value = result.getDouble(1);
				UUID playerUUID = UUID.fromString(result.getString(2));
				data.setPlayerUUID(playerUUID);
				data.setValue(value);
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean hasOfferData(OfferData data) {
		boolean isoffered = false;
		ConnectionManager manager = ConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT uuid FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				isoffered = true;
			}
			result.close();
			sql.close();
			manager.release("cookieLand", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isoffered;
	}

}
