package de.kekshaus.cubit.land.api.databaseAPI;

import java.util.UUID;

import org.bukkit.World;

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

	public void setValue(double value) {
		this.value = value;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public String getRegionID() {
		return this.regionID;
	}

	public UUID getPlayerUUID() {
		return this.playerUUID;
	}

	public Double getValue() {
		return this.value;
	}

	public World getWorld() {
		return this.world;
	}

}
