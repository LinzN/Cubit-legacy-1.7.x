package de.kekshaus.cookieApi.land;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private WorldGuardPlugin wgPl;

	public void onEnable() {
		inst = this;
		wgPl = WorldGuardPlugin.inst();
	}

	public void onDisable() {
	}

	public static Landplugin inst() {
		return inst;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return this.wgPl;
	}

}
