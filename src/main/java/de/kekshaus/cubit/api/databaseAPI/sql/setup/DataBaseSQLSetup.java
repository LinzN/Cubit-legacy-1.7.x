package de.kekshaus.cubit.api.databaseAPI.sql.setup;

import java.sql.Connection;
import java.sql.Statement;

import de.kekshaus.cubit.api.databaseAPI.sql.handler.DataBaseSQLConnectionFactory;
import de.kekshaus.cubit.api.databaseAPI.sql.handler.DataBaseSQLConnectionHandler;
import de.kekshaus.cubit.api.databaseAPI.sql.handler.DataBaseSQLConnectionManager;
import de.kekshaus.cubit.plugin.Landplugin;

public class DataBaseSQLSetup {

	public static boolean setup(Landplugin plugin) {
		String db = Landplugin.inst().getYamlManager().getSettings().sqlDataBase;
		int port = Landplugin.inst().getYamlManager().getSettings().sqlPort;
		String host = Landplugin.inst().getYamlManager().getSettings().sqlHostname;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = Landplugin.inst().getYamlManager().getSettings().sqlUser;
		String password = Landplugin.inst().getYamlManager().getSettings().sqlPassword;
		DataBaseSQLConnectionFactory factory = new DataBaseSQLConnectionFactory(url, username, password);
		DataBaseSQLConnectionManager manager = DataBaseSQLConnectionManager.DEFAULT;
		DataBaseSQLConnectionHandler handler = manager.getHandler("cookieLand", factory);

		try {
			Connection connection = handler.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS offerManager (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));";
			String sql3 = "CREATE TABLE IF NOT EXISTS uuidcache (Id int NOT NULL AUTO_INCREMENT, UUID text, NAME text, TIMESTAMP bigint, PRIMARY KEY (id));";
			Statement action = connection.createStatement();
			action.executeUpdate(sql);
			// if
			// (!Landplugin.inst().getYamlManager().getSettings().sqlUseXeonSuiteSameDatabase)
			// {
			action.executeUpdate(sql3);
			// }
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
