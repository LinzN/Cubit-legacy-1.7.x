package de.kekshaus.cubit.land;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cubit.land.api.YamlConfigurationAPI.YamlConfigurationManager;
import de.kekshaus.cubit.land.api.blockAPI.BlockAPIManager;
import de.kekshaus.cubit.land.api.databaseAPI.DatabaseAPIManager;
import de.kekshaus.cubit.land.api.particleAPI.ParticleAPIManager;
import de.kekshaus.cubit.land.api.regionAPI.RegionAPIManager;
import de.kekshaus.cubit.land.api.vaultAPI.VaultAPIManager;
import de.kekshaus.cubit.land.commandSuite.SetupCommands;
import de.kekshaus.cubit.land.plugin.Language;
import de.kekshaus.cubit.land.plugin.PermissionNodes;
import de.kekshaus.cubit.land.plugin.listener.AdditionalPhysicsListener;
import de.kekshaus.cubit.land.plugin.listener.LoginListener;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private boolean isGuildLoaded = false;
	private WorldGuardPlugin wgPl;
	private WorldEdit wePl;
	private RegionAPIManager landMrg;
	private BlockAPIManager blockMrg;
	private ParticleAPIManager particleMrg;
	private VaultAPIManager vaultMrg;
	private DatabaseAPIManager databaseMrg;
	private Language langMrg;
	private PermissionNodes permNodes;
	private YamlConfigurationManager yamlConfiguration;

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
		yamlConfiguration = new YamlConfigurationManager(this);
		landMrg = new RegionAPIManager(this);
		blockMrg = new BlockAPIManager(this);
		particleMrg = new ParticleAPIManager(this);
		vaultMrg = new VaultAPIManager(this);
		langMrg = new Language();
		permNodes = new PermissionNodes();
		databaseMrg = new DatabaseAPIManager(this);

	}

	public static Landplugin inst() {
		return inst;
	}

	public RegionAPIManager getLandManager() {
		return this.landMrg;
	}

	public BlockAPIManager getBlockManager() {
		return this.blockMrg;
	}

	public ParticleAPIManager getParticleManager() {
		return this.particleMrg;
	}

	public DatabaseAPIManager getDatabaseManager() {
		return this.databaseMrg;
	}

	public VaultAPIManager getVaultManager() {
		return this.vaultMrg;
	}

	public Language getLanguageManager() {
		return this.langMrg;
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

	public YamlConfigurationManager getYamlManager() {
		return this.yamlConfiguration;
	}

	public boolean isGuildLoaded() {
		return this.isGuildLoaded;
	}

}
