package de.kekshaus.cookieApi.land;

import org.bukkit.plugin.java.JavaPlugin;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;

	public void onEnable() {
		inst = this;
	}

	public void onDisable() {
	}

	public static Landplugin inst() {
		return inst;
	}

}
