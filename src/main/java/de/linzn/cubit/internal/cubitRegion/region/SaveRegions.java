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

package de.linzn.cubit.internal.cubitRegion.region;

import com.sk89q.worldguard.protection.managers.RegionManager;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.World;

public class SaveRegions {
    public boolean save(final CubitLand cubitLand) {
        return saveData(cubitLand, cubitLand.getWorld());
    }

    public boolean save(final World world) {
        return saveData(null, world);
    }

    public boolean saveData(final CubitLand cubitLand, final World world) {
        try {
            RegionManager manager = CubitBukkitPlugin.inst().getWorldGuardPlugin()
                    .getRegionManager(world);
            if (cubitLand != null && cubitLand.getWGRegion() != null) {
                manager.addRegion(cubitLand.getWGRegion());
            }
            manager.saveChanges();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
