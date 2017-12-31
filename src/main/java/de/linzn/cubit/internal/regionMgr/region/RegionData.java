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

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import org.bukkit.World;

import java.util.Set;
import java.util.UUID;

public class RegionData {
    private ProtectedRegion region;
    private LandTypes type;
    private World world;

    public RegionData(World world) {
        this.type = LandTypes.NOTYPE;
        this.world = world;

    }

    public void setWGRegion(ProtectedRegion region) {
        this.type = LandTypes.getLandType(region.getId());
        this.region = region;
    }

    public ProtectedRegion praseWGRegion() {
        return this.region;
    }

    public String getMinPoint() {
        return this.region.getMinimumPoint().getBlockX() + ", " + this.region.getMinimumPoint().getBlockZ();
    }

    public String getMaxPoint() {
        return this.region.getMaximumPoint().getBlockX() + ", " + this.region.getMaximumPoint().getBlockZ();
    }

    public UUID[] getOwnersUUID() {
        Set<UUID> uuidSet = praseWGRegion().getOwners().getUniqueIds();
        return uuidSet.toArray(new UUID[uuidSet.size()]);
    }

    public UUID[] getMembersUUID() {
        Set<UUID> uuidSet = praseWGRegion().getMembers().getUniqueIds();
        return uuidSet.toArray(new UUID[uuidSet.size()]);
    }

    public World getWorld() {
        return this.world;
    }

    public LandTypes getLandType() {
        return this.type;
    }

    public String getRegionName() {
        return this.region.getId();
    }

}
