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

package de.linzn.cubit.internal.cubitEvents;

import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CubitLandUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private World world;
    private String regionId;
    private RegionData regionData;

    public CubitLandUpdateEvent(World world, String regionId, RegionData regionData) {
        this.world = world;
        this.regionId = regionId;
        this.regionData = regionData;
    }

    public RegionData getRegionData() {
        return regionData;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getRegionID() {
        return this.regionId;
    }

    public World getWorld() {
        return this.world;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}