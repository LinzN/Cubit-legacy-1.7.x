package de.kekshaus.cookieApi.land;

import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cookieApi.land.blockAPI.BlockManager;
import de.kekshaus.cookieApi.land.particleAPI.ParticleManager;
import de.kekshaus.cookieApi.land.regionAPI.LandManager;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private WorldGuardPlugin wgPl;
	private LandManager landMrg;
	private BlockManager blockMrg;
	private ParticleManager particleMrg;

	public void onEnable() {
		inst = this;
		wgPl = WorldGuardPlugin.inst();
		landMrg = new LandManager(this);
		blockMrg = new BlockManager(this);
		particleMrg = new ParticleManager(this);
	}

	public void onDisable() {
	}

	public static Landplugin inst() {
		return inst;
	}

	public LandManager getLandManager() {
		return landMrg;
	}

	public BlockManager getBlockManager() {
		return blockMrg;
	}

	public ParticleManager getParticleManager() {
		return particleMrg;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return this.wgPl;
	}

}
