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

package de.linzn.cubit.internal.blockEdit.normal.block;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ChunkBlockCleaner implements Runnable {

    private Plugin plugin;
    private Chunk chunk;
    private List<Material> blockList;

    public ChunkBlockCleaner(Plugin plugin, Chunk chunk, List<Material> blockList) {
        this.plugin = plugin;
        this.chunk = chunk;
        this.blockList = blockList;
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this);
    }

    @Override
    public void run() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    Block selectedBlock = chunk.getBlock(x, y, z);
                    if (this.blockList.contains(selectedBlock.getType())) {
                        removeBlock(selectedBlock);
                    }
                }
            }
        }
    }

    private void removeBlock(final Block block) {
        plugin.getServer().getScheduler().runTask(plugin, () -> block.setType(Material.AIR));
    }
}
