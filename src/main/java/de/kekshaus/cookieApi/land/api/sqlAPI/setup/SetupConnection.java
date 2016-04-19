package de.kekshaus.cookieApi.land.api.sqlAPI.setup;

import java.sql.Connection;
import java.sql.Statement;

import de.kekshaus.cookieApi.bukkit.CookieApiBukkit;
import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.sqlAPI.handler.ConnectionFactory;
import de.kekshaus.cookieApi.land.api.sqlAPI.handler.ConnectionHandler;
import de.kekshaus.cookieApi.land.api.sqlAPI.handler.ConnectionManager;

public class SetupConnection {

	public static boolean setup(Landplugin plugin) {
		String db = CookieApiBukkit.getDataBase();
		String port = CookieApiBukkit.getPort();
		String host = CookieApiBukkit.getHost();
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = CookieApiBukkit.getUsername();
		String password = CookieApiBukkit.getPassword();
		ConnectionFactory factory = new ConnectionFactory(url, username, password);
		ConnectionManager manager = ConnectionManager.DEFAULT;
		ConnectionHandler handler = manager.getHandler("cookieLand", factory);

		try {
			Connection connection = handler.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS offerManager (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));";
			Statement action = connection.createStatement();
			action.executeUpdate(sql);
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
