package de.kekshaus.cookieApi.land;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cookieApi.land.api.blockAPI.BlockManager;
import de.kekshaus.cookieApi.land.api.particleAPI.ParticleManager;
import de.kekshaus.cookieApi.land.api.regionAPI.LandManager;
import de.kekshaus.cookieApi.land.api.sqlAPI.SqlManager;
import de.kekshaus.cookieApi.land.api.vaultAPI.VaultManager;
import de.kekshaus.cookieApi.land.commandSuite.SetupCommands;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private WorldGuardPlugin wgPl;
	private WorldEdit wePl;
	private LandManager landMrg;
	private BlockManager blockMrg;
	private ParticleManager particleMrg;
	private VaultManager vaultMrg;
	private SqlManager sqlMrg;
	private LanguageManager langMrg;

	public void onEnable() {
		inst = this;
		this.wgPl = WorldGuardPlugin.inst();
		this.wePl = WorldEdit.getInstance();
		setupManagers();
		if (!this.sqlMrg.link()) {
			this.setEnabled(false);
		}
		new SetupCommands(this);
	}

	public void onDisable() {
		HandlerList.unregisterAll(Landplugin.inst());
	}

	private void setupManagers() {
		sqlMrg = new SqlManager(this);
		landMrg = new LandManager(this);
		blockMrg = new BlockManager(this);
		particleMrg = new ParticleManager(this);
		vaultMrg = new VaultManager(this);
		langMrg = new LanguageManager();

	}

	public static Landplugin inst() {
		return inst;
	}

	public LandManager getLandManager() {
		return this.landMrg;
	}

	public BlockManager getBlockManager() {
		return this.blockMrg;
	}

	public ParticleManager getParticleManager() {
		return this.particleMrg;
	}

	public SqlManager getSqlManager() {
		return this.sqlMrg;
	}

	public VaultManager getVaultManager() {
		return this.vaultMrg;
	}

	public LanguageManager getLanguageManager() {
		return this.langMrg;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return this.wgPl;
	}

	public WorldEdit getWorldEdit() {
		return this.wePl;
	}

}
