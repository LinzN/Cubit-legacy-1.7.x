/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.bukkit.plugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import de.linzn.cubit.bukkit.command.SetupCommands;
import de.linzn.cubit.bukkit.plugin.listener.AdditionalPhysicsListener;
import de.linzn.cubit.bukkit.plugin.listener.LoginListener;
import de.linzn.cubit.internal.YamlConfigurationMgr.YamlConfigurationManager;
import de.linzn.cubit.internal.blockMgr.BlockManager;
import de.linzn.cubit.internal.dataAccessMgr.DataAccessManager;
import de.linzn.cubit.internal.dynmapMgr.DynmapManager;
import de.linzn.cubit.internal.entityMgr.EntityManager;
import de.linzn.cubit.internal.landmapMgr.ScoreboardMapManager;
import de.linzn.cubit.internal.particleMgr.ParticleManager;
import de.linzn.cubit.internal.regionMgr.CubitregionManager;
import de.linzn.cubit.internal.vaultMgr.VaultManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.update.spiget.SpigetUpdateCheck;

public class CubitBukkitPlugin extends JavaPlugin {

    private static CubitBukkitPlugin inst;
    private WorldGuardPlugin wgPl;
    private WorldEdit wePl;
    private CubitregionManager regionMrg;
    private BlockManager blockMrg;
    private ParticleManager particleMrg;
    private VaultManager vaultMrg;
    private DataAccessManager dataAccessMgr;
    private EntityManager entityMrg;
    private ScoreboardMapManager scoreboardMapMgr;
    private DynmapManager dynmapManager;
    private PermissionNodes permNodes;
    private YamlConfigurationManager yamlConfiguration;
    private Metrics metrics;
    private SpigetUpdateCheck spigetUpdateCheck;

    public static CubitBukkitPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        if (!getPluginDepends()) {
            this.setEnabled(false);
            return;
        }
        if (!setupManagers()) {
            this.setEnabled(false);
            return;
        }

        this.getServer().getPluginManager().registerEvents(new LoginListener(), this);

        new SetupCommands(this);

        if (this.getYamlManager().getSettings().physicWaterLavaFlowLand) {
            this.getServer().getPluginManager().registerEvents(new AdditionalPhysicsListener(), this);
        }
        runOutgoingStreams();

        getLogger().info("Cubit startup finish");

    }

    @Override
    public void onDisable() {
        scoreboardMapMgr.cleanupAllScoreboardMaps();
        HandlerList.unregisterAll(CubitBukkitPlugin.inst());
    }

    private boolean getPluginDepends() {

        if (this.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            this.getLogger().severe("Error: " + "WorldEdit not found!");
            return false;
        }

        if (this.getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            this.getLogger().severe("Error: " + "WorldGuard not found!");
            return false;
        }

        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            this.getLogger().severe("Error: " + "Vault not found!");
            return false;
        }

        this.wgPl = WorldGuardPlugin.inst();
        this.wePl = WorldEdit.getInstance();

        return true;
    }

    private void runOutgoingStreams() {
        getLogger().info("Run outgoing streams.");
        this.metrics = new Metrics(this);
        if (this.yamlConfiguration.getSettings().updateCheck) {
            this.spigetUpdateCheck = new SpigetUpdateCheck(this);
        }

    }

    private boolean setupManagers() {
        this.yamlConfiguration = new YamlConfigurationManager(this);
        this.regionMrg = new CubitregionManager(this);
        this.blockMrg = new BlockManager(this);
        this.particleMrg = new ParticleManager(this);
        this.vaultMrg = new VaultManager(this);
        this.permNodes = new PermissionNodes(this);
        this.dataAccessMgr = new DataAccessManager(this);
        this.entityMrg = new EntityManager(this);
        this.scoreboardMapMgr = new ScoreboardMapManager(this);
        this.dynmapManager = new DynmapManager(this);
        return true;

    }

    public CubitregionManager getRegionManager() {
        return this.regionMrg;
    }

    public BlockManager getBlockManager() {
        return this.blockMrg;
    }

    public ParticleManager getParticleManager() {
        return this.particleMrg;
    }

    public DataAccessManager getDataAccessManager() {
        return this.dataAccessMgr;
    }

    public VaultManager getVaultManager() {
        return this.vaultMrg;
    }

    public EntityManager getEntityManager() {
        return this.entityMrg;
    }

    public ScoreboardMapManager getScoreboardMapManager() {
        return scoreboardMapMgr;
    }

    public DynmapManager getDynmapManager() {
        return this.dynmapManager;
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

    public Metrics getMetrics() {
        return this.metrics;
    }

    public SpigetUpdateCheck getSpigetUpdateCheck() {
        return this.spigetUpdateCheck;
    }

    public YamlConfigurationManager getYamlManager() {
        return this.yamlConfiguration;
    }

}
