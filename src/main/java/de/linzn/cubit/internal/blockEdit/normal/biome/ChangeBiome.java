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

package de.linzn.cubit.internal.blockEdit.normal.biome;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.plugin.Plugin;

public class ChangeBiome {

    private Plugin plugin;
    private Chunk chunk;
    private Biome biome;

    public ChangeBiome(Plugin plugin, Chunk chunk, Biome biome) {
        this.plugin = plugin;
        this.chunk = chunk;
        this.biome = biome;
    }

    public void change() {
        final int chunkX = this.chunk.getX() * 16;
        final int cZ = this.chunk.getZ() * 16;
        final World world = this.chunk.getWorld();

        for (int x = 0; x < 16; x++) {

            final int cordX = x + chunkX;

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                for (int z = 0; z < 16; z++) {
                    world.setBiome(cordX, cZ + z, biome);
                }
            });

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        CubitBukkitPlugin.inst().getBlockManager().getNMSHandler().refreshChunk(chunk);
    }

}
