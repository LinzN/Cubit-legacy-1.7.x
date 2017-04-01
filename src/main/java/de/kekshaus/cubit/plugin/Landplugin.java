package de.kekshaus.cubit.plugin;

import org.bstats.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.kekshaus.cubit.api.YamlConfigurationAPI.YamlConfigurationManager;
import de.kekshaus.cubit.api.blockAPI.BlockAPIManager;
import de.kekshaus.cubit.api.database.DatabaseManager;
import de.kekshaus.cubit.api.particleAPI.ParticleAPIManager;
import de.kekshaus.cubit.api.regionAPI.RegionAPIManager;
import de.kekshaus.cubit.api.vaultAPI.VaultAPIManager;
import de.kekshaus.cubit.commandSuite.SetupCommands;
import de.kekshaus.cubit.plugin.entityLimiter.EntityLimiter;
import de.kekshaus.cubit.plugin.listener.AdditionalPhysicsListener;
import de.kekshaus.cubit.plugin.listener.LoginListener;

public class Landplugin extends JavaPlugin {

	private static Landplugin inst;
	private WorldGuardPlugin wgPl;
	private WorldEdit wePl;
	private RegionAPIManager regionMrg;
	private BlockAPIManager blockMrg;
	private ParticleAPIManager particleMrg;
	private VaultAPIManager vaultMrg;
	private DatabaseManager databaseMrg;
	private PermissionNodes permNodes;
	private YamlConfigurationManager yamlConfiguration;

	@Override
	public void onEnable() {
		inst = this;
		if (!getPluginDepends()){
			this.setEnabled(false);
			return;
		}
		if (!setupManagers()){
			this.setEnabled(false);
			return;
		}
		this.getServer().getPluginManager().registerEvents(new LoginListener(), this);

		new SetupCommands(this);
		if (this.getYamlManager().getSettings().entityLimiterUse) {
			new EntityLimiter(this);
		}
		if (this.getYamlManager().getSettings().physicWaterLavaFlowLand) {
			this.getServer().getPluginManager().registerEvents(new AdditionalPhysicsListener(), this);
		}
		runMetrics();
		getLogger().info("Cubit startup finish");

	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(Landplugin.inst());
	}
	
	private boolean getPluginDepends(){

		if (this.getServer().getPluginManager().getPlugin("WorldEdit") == null){
			this.getLogger().severe("Error: " + "WorldEdit not found!");
			return false;
		}
		
		if (this.getServer().getPluginManager().getPlugin("WorldGuard") == null){
			this.getLogger().severe("Error: " + "WorldGuard not found!");
			return false;
		}
		
		if (this.getServer().getPluginManager().getPlugin("Vault") == null){
			this.getLogger().severe("Error: " + "Vault not found!");
			return false;
		}
		
		this.wgPl = WorldGuardPlugin.inst();
		this.wePl = WorldEdit.getInstance();
		
		return true;
	}

	private boolean setupManagers() {
		this.yamlConfiguration = new YamlConfigurationManager(this);
		this.regionMrg = new RegionAPIManager(this);
		this.blockMrg = new BlockAPIManager(this);
		this.particleMrg = new ParticleAPIManager(this);
		this.vaultMrg = new VaultAPIManager(this);
		this.permNodes = new PermissionNodes(this);
		this.databaseMrg = new DatabaseManager(this);
		return true;

	}

	private void runMetrics() {
		new Metrics(this);
	}

	public static Landplugin inst() {
		return inst;
	}

	public RegionAPIManager getRegionManager() {
		return this.regionMrg;
	}

	public BlockAPIManager getBlockManager() {
		return this.blockMrg;
	}

	public ParticleAPIManager getParticleManager() {
		return this.particleMrg;
	}

	public DatabaseManager getDatabaseManager() {
		return this.databaseMrg;
	}

	public VaultAPIManager getVaultManager() {
		return this.vaultMrg;
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

}
