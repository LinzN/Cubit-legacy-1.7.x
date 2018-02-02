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

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import org.bukkit.World;

import java.util.Set;
import java.util.UUID;

public class CubitLand {
    private ProtectedRegion region;
    private CubitType type;
    private World world;
    private String[] ownerNames;
    private String[] memberNames;

    public CubitLand(World world) {
        this.type = CubitType.NOTYPE;
        this.world = world;
    }

    public ProtectedRegion getWGRegion() {
        return this.region;
    }

    public void setWGRegion(ProtectedRegion region) {
        this.type = CubitType.getLandType(region.getId());
        this.region = region;
        if (this.getOwnersUUID().length > 0) {
            this.ownerNames = CubitBukkitPlugin.inst().getCacheManager().getPlayernames(this.getOwnersUUID());
        }
        if (this.getMembersUUID().length > 0) {
            this.memberNames = CubitBukkitPlugin.inst().getCacheManager().getPlayernames(this.getMembersUUID());
        }
    }

    public String getMinPoint() {
        return this.region.getMinimumPoint().getBlockX() + ", " + this.region.getMinimumPoint().getBlockZ();
    }

    public String getMaxPoint() {
        return this.region.getMaximumPoint().getBlockX() + ", " + this.region.getMaximumPoint().getBlockZ();
    }

    public UUID[] getOwnersUUID() {
        Set<UUID> uuidSet = region.getOwners().getUniqueIds();
        return uuidSet.toArray(new UUID[uuidSet.size()]);
    }

    public UUID[] getMembersUUID() {
        Set<UUID> uuidSet = region.getMembers().getUniqueIds();
        return uuidSet.toArray(new UUID[uuidSet.size()]);
    }

    public String[] getOwnerNames() {
        return this.ownerNames;
    }

    public String[] getMemberNames() {
        return this.memberNames;
    }

    public World getWorld() {
        return this.world;
    }

    public CubitType getCubitType() {
        return this.type;
    }

    public String getLandName() {
        return this.region.getId();
    }

}
