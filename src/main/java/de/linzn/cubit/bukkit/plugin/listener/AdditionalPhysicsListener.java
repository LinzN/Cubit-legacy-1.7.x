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

package de.linzn.cubit.bukkit.plugin.listener;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class AdditionalPhysicsListener implements Listener {

    @EventHandler
    public void onLiquidFlowOtherLand(final BlockFromToEvent event) {
        Chunk fromChunk = event.getBlock().getLocation().getChunk();
        Chunk toChunk = event.getToBlock().getLocation().getChunk();
        CubitLand fromLand = CubitBukkitPlugin.inst().getRegionManager().praseRegionData(fromChunk.getWorld(),
                fromChunk.getX(), fromChunk.getZ());

        CubitLand toLand = CubitBukkitPlugin.inst().getRegionManager().praseRegionData(toChunk.getWorld(),
                toChunk.getX(), toChunk.getZ());

        if (toLand.getLandType() == CubitType.NOTYPE) {
            return;
        }

        if (fromLand.getLandType() == CubitType.SERVER && toLand.getLandType() == CubitType.SERVER) {
            return;
        }

        if (!CubitBukkitPlugin.inst().getRegionManager().hasLandPermission(toLand, fromLand.getOwnersUUID()[0])) {
            event.setCancelled(true);
        }
    }
}
