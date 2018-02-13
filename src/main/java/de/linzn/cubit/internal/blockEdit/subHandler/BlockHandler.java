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

package de.linzn.cubit.internal.blockEdit.subHandler;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.blockEdit.normal.block.ChunkBlockCleaner;
import de.linzn.cubit.internal.blockEdit.normal.block.ChunkBorder;
import org.bukkit.Chunk;
import org.bukkit.Material;

import java.util.List;

public class BlockHandler {

    private CubitBukkitPlugin plugin;
    private boolean useFAWE;

    public BlockHandler(CubitBukkitPlugin plugin, boolean useFAWE) {
        this.plugin = plugin;
        this.useFAWE = useFAWE;
    }

    public boolean placeLandBorder(Chunk chunk, Material material) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.placeLandBorderDefault(chunk, material);
        }
        return false;
    }

    public boolean removeBlockOnShopBuy(Chunk chunk, List<Material> blockList) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.removeBlockOnShopBuyDefault(chunk, blockList);
        }
        return false;
    }

    private boolean placeLandBorderDefault(Chunk chunk, Material material) {
        try {
            new ChunkBorder(plugin, chunk, material);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean removeBlockOnShopBuyDefault(Chunk chunk, List<Material> blockList) {
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
