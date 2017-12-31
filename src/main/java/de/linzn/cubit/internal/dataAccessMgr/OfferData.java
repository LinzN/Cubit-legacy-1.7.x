/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.dataAccessMgr;

import org.bukkit.World;

import java.util.UUID;

public class OfferData {
    private double value;
    private UUID playerUUID;
    private String regionID;
    private World world;

    public OfferData(String regionID, World world, UUID playerUUID, double value) {
        this.value = value;
        this.playerUUID = playerUUID;
        this.regionID = regionID;
        this.world = world;
    }

    public OfferData(String regionID, World world) {
        this.regionID = regionID;
        this.world = world;
    }

    public OfferData(String regionID, World world, UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.regionID = regionID;
        this.world = world;
    }

    public OfferData(String regionID, World world, double value) {
        this.value = value;
        this.regionID = regionID;
        this.world = world;
    }

    public String getRegionID() {
        return this.regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

}
