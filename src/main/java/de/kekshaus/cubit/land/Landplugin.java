package de.kekshaus.cubit.land;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cubit.land.api.blockAPI.BlockManager;
import de.kekshaus.cubit.land.api.database.DatabaseManager;
import de.kekshaus.cubit.land.api.particleAPI.ParticleManager;
import de.kekshaus.cubit.land.api.regionAPI.LandManager;
import de.kekshaus.cubit.land.api.vaultAPI.VaultManager;
import de.kekshaus.cubit.land.commandSuite.SetupCommands;
import de.kekshaus.cubit.land.plugin.LandConfig;
import de.kekshaus.cubit.land.plugin.Language;
import de.kekshaus.cubit.land.plugin.PermissionNodes;
import de.kekshaus.cubit.land.plugin.listener.AdditionalPhysicsListener;
import de.kekshaus.cubit.land.plugin.listener.LoginListener;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private boolean isGuildLoaded = false;
	private WorldGuardPlugin wgPl;
	private WorldEdit wePl;
	private LandManager landMrg;
	private BlockManager blockMrg;
	private ParticleManager particleMrg;
	private VaultManager vaultMrg;
	private DatabaseManager databaseMrg;
	private Language langMrg;
	private LandConfig landConf;
	private PermissionNodes permNodes;

	@Override
	public void onEnable() {
		inst = this;
		this.wgPl = WorldGuardPlugin.inst();
		this.wePl = WorldEdit.getInstance();
		if (this.getServer().getPluginManager().getPlugin("XeonSuiteGuild") != null) {
			isGuildLoaded = true;
		}
		setupManagers();
		this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
		this.getServer().getPluginManager().registerEvents(new AdditionalPhysicsListener(), this);
		if (!this.databaseMrg.link()) {
			this.setEnabled(false);
		}
		new SetupCommands(this);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(Landplugin.inst());
	}

	private void setupManagers() {
		databaseMrg = new DatabaseManager(this);
		landMrg = new LandManager(this);
		blockMrg = new BlockManager(this);
		particleMrg = new ParticleManager(this);
		vaultMrg = new VaultManager(this);
		langMrg = new Language();
		landConf = new LandConfig(this);
		permNodes = new PermissionNodes();

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

	public DatabaseManager getDatabaseManager() {
		return this.databaseMrg;
	}

	public VaultManager getVaultManager() {
		return this.vaultMrg;
	}

	public Language getLanguageManager() {
		return this.langMrg;
	}

	public LandConfig getLandConfig() {
		return this.landConf;
	}

	public PermissionNodes getPermNodes() {
		return this.permNodes;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return this.wgPl;
	}

	public WorldEdit getWorldEdit() {
		return this.wePl;
	}

	public boolean isGuildLoaded() {
		return this.isGuildLoaded;
	}

}
