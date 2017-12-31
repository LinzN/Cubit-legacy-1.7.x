/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.regionMgr.region;

import com.sk89q.worldguard.protection.managers.RegionManager;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.World;

public class SaveRegions {
    public void save(final RegionData regionData) {
        saveData(regionData, regionData.getWorld());
    }

    public void save(final World world) {
        saveData(null, world);
    }

    public void saveData(final RegionData regionData, final World world) {
        CubitBukkitPlugin.inst().getServer().getScheduler().runTaskAsynchronously(CubitBukkitPlugin.inst(),
                () -> {
                    try {
                        RegionManager manager = CubitBukkitPlugin.inst().getWorldGuardPlugin()
                                .getRegionManager(world);
                        if (regionData != null && regionData.praseWGRegion() != null) {
                            manager.addRegion(regionData.praseWGRegion());
                        }
                        manager.saveChanges();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
