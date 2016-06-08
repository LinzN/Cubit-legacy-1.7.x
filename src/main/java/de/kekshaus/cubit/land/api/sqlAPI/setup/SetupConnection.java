package de.kekshaus.cubit.land.api.sqlAPI.setup;

import java.sql.Connection;
import java.sql.Statement;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.sqlAPI.handler.ConnectionFactory;
import de.kekshaus.cubit.land.api.sqlAPI.handler.ConnectionHandler;
import de.kekshaus.cubit.land.api.sqlAPI.handler.ConnectionManager;

public class SetupConnection {

	public static boolean setup(Landplugin plugin) {
		String db = Landplugin.inst().getLandConfig().sqlDataBase;
		int port = Landplugin.inst().getLandConfig().sqlPort;
		String host = Landplugin.inst().getLandConfig().sqlHostname;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = Landplugin.inst().getLandConfig().sqlUser;
		String password = Landplugin.inst().getLandConfig().sqlPassword;
		ConnectionFactory factory = new ConnectionFactory(url, username, password);
		ConnectionManager manager = ConnectionManager.DEFAULT;
		ConnectionHandler handler = manager.getHandler("cookieLand", factory);

		try {
			Connection connection = handler.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS offerManager (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));";
			String sql3 = "CREATE TABLE IF NOT EXISTS uuidcache (Id int NOT NULL AUTO_INCREMENT, UUID text, NAME text, TIMESTAMP bigint, PRIMARY KEY (id));";
			Statement action = connection.createStatement();
			action.executeUpdate(sql);
			if (Landplugin.inst().getLandConfig().sqlUseXeonSuiteSameDatabase) {
				action.executeUpdate(sql3);
			}
			action.close();
			handler.release(connection);
			plugin.getLogger().info("Datenbank geladen!");
			return true;

		} catch (Exception e) {
			plugin.getLogger().info("Fehler beim Laden der Datenbank!");
			e.printStackTrace();
			return false;
		}

	}

}
