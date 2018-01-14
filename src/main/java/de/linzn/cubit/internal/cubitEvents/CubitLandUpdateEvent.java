package de.linzn.cubit.internal.cubitEvents;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CubitLandUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private World world;
    private String regionId;

    public CubitLandUpdateEvent(World world, String regionId) {
        this.world = world;
        this.regionId = regionId;
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