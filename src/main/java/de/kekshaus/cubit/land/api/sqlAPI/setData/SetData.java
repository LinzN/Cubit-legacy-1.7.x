package de.kekshaus.cubit.land.api.sqlAPI.setData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.kekshaus.cubit.land.api.sqlAPI.handler.ConnectionManager;
import de.kekshaus.cubit.land.api.sqlAPI.handler.OfferData;

public class SetData {

	public SetData() {

	}

	public void setOffer(OfferData data) {
		ConnectionManager manager = ConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT value FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement("UPDATE offerManager SET value = " + data.getValue()
						+ ", uuid = " + data.getPlayerUUID().toString() + " WHERE regionID = '" + data.getRegionID()
						+ "' AND world = '" + data.getWorld() + "';");
				update.executeUpdate();
				update.close();
			} else {
				PreparedStatement insert = conn
						.prepareStatement("INSERT INTO offerManager (regionID, value, world, uuid) VALUES ('"
								+ data.getRegionID() + "', '" + data.getValue() + "', '" + data.getWorld() + "', '"
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

	public void removeOffer(OfferData data) {
		ConnectionManager manager = ConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cookieLand");
			PreparedStatement sql = conn.prepareStatement("SELECT data FROM offerManager WHERE regionID = '"
					+ data.getRegionID() + "' AND world = '" + data.getWorld() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement("DELETE FROM offerManager WHERE regionID = '"
						+ data.getRegionID() + "' AND world = '" + data.getWorld() + "';");
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

}
