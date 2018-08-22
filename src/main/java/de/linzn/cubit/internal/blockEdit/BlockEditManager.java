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

package de.linzn.cubit.internal.blockEdit;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.blockEdit.normal.nmsPackets.NMSLoader;
import de.linzn.cubit.internal.blockEdit.subHandler.BiomeHandler;
import de.linzn.cubit.internal.blockEdit.subHandler.BlockHandler;
import de.linzn.cubit.internal.blockEdit.subHandler.SnapshotHandler;
import org.bukkit.Bukkit;

public class BlockEditManager {

    private CubitBukkitPlugin plugin;
    private NMSLoader nmsloader;
    private SnapshotHandler snapshotHandler;
    private BiomeHandler biomeHandler;
    private BlockHandler blockHandler;

    public BlockEditManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("[Setup] BlockEditManager");
        boolean useFAWE = Bukkit.getPluginManager().getPlugin("") != null;
        this.plugin = plugin;
        this.nmsloader = new NMSLoader(this.plugin);
        this.snapshotHandler = new SnapshotHandler(this.plugin, useFAWE);
        this.biomeHandler = new BiomeHandler(this.plugin, useFAWE);
        this.blockHandler = new BlockHandler(this.plugin, useFAWE);
    }

    public SnapshotHandler getSnapshotHandler() {
        return this.snapshotHandler;
    }

    public BiomeHandler getBiomeHandler() {
        return this.biomeHandler;
    }

    public BlockHandler getBlockHandler() {
        return this.blockHandler;
    }

    public INMSMask getNMSHandler() {
        return this.nmsloader.nmsHandler();
    }

}
