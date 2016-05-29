package de.kekshaus.cubit.land.plugin;

import de.kekshaus.cubit.land.Landplugin;

public class LandConfig {

	private Landplugin plugin;
	public String sqlDataBase;
	public String sqlHostname;
	public int sqlPort;
	public String sqlUser;
	public String sqlPassword;

	public LandConfig(Landplugin plugin) {
		this.plugin = plugin;
	}

}
