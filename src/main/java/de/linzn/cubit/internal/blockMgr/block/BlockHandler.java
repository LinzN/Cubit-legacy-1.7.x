/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.blockMgr.block;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Chunk;
import org.bukkit.Material;

import java.util.List;

public class BlockHandler {

    private CubitBukkitPlugin plugin;

    public BlockHandler(CubitBukkitPlugin plugin) {
        this.plugin = plugin;

    }

    public boolean placeLandBorder(Chunk chunk, Material material) {
        try {
            new ChunkBorder(plugin, chunk, material);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeBlockOnShopBuy(Chunk chunk, List<Material> blockList) {
        try {
            if (this.plugin.getYamlManager().getSettings().useShopMaterialCleanup) {
                new ChunkBlockCleaner(plugin, chunk, blockList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
