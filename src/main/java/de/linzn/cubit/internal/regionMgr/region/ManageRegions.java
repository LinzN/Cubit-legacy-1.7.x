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

package de.linzn.cubit.internal.regionMgr.region;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.UUID;

public class ManageRegions {

    public RegionData newRegion(final int chunkX, final int chunkZ, final World world, final UUID playerUUID,
                                final String regionName) {

        final Vector min;
        final Vector max;
        final Vector2D min2D;

        min2D = new Vector2D(chunkX * 16, chunkZ * 16);
        min = new Vector(min2D.getBlockX(), 0, min2D.getBlockZ());
        max = min.add(15, world.getMaxHeight(), 15);
        ProtectedRegion region = new ProtectedCuboidRegion(regionName, min.toBlockVector(), max.toBlockVector());

        if (playerUUID != null) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
            LocalPlayer localplayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
            DefaultDomain domain = new DefaultDomain();
            region.getMembers().clear();
            region.getOwners().clear();
            domain.addPlayer(localplayer);
            region.setOwners(domain);

        }
        RegionData regionData = new RegionData(world);
        regionData.setWGRegion(region);
        return regionData;
    }

    public RegionData removeRegion(RegionData regionData, World world) {
        RegionManager manager = CubitBukkitPlugin.inst().getWorldGuardPlugin().getRegionManager(world);
        manager.removeRegion(regionData.getRegionName());
        return regionData;

    }

}
