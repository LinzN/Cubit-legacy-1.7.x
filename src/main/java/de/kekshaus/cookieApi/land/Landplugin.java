package de.kekshaus.cookieApi.land;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cookieApi.land.regionAPI.LandManager;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private WorldGuardPlugin wgPl;
	private LandManager landMrg;

	public void onEnable() {
		inst = this;
		wgPl = WorldGuardPlugin.inst();
		landMrg = new LandManager(this);
	}

	public void onDisable() {
	}

	public static Landplugin inst() {
		return inst;
	}

	public LandManager getLandManager() {
		return landMrg;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return this.wgPl;
	}

}
