package de.kekshaus.cookieApi.land.sqlAPI;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.sqlAPI.setup.SetupConnection;

public class SqlManager {

	private Landplugin plugin;

	public SqlManager(Landplugin plugin) {
		this.plugin = plugin;
	}

	public boolean link() {
		return SetupConnection.setup(plugin);
	}

}
