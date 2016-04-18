package de.kekshaus.cookieApi.land.sqlAPI.handler;

import java.util.UUID;

public class OfferData {
	private double value;
	private UUID playerUUID;
	private String regionID;
	private String world;

	public OfferData(String regionID, String world, UUID playerUUID, double value) {
		this.value = value;
		this.playerUUID = playerUUID;
		this.regionID = regionID;
		this.world = world;
	}

	public OfferData(String regionID, String world) {
		this.regionID = regionID;
		this.world = world;
	}

	public OfferData(String regionID, String world, UUID playerUUID) {
		this.playerUUID = playerUUID;
		this.regionID = regionID;
		this.world = world;
	}

	public OfferData(String regionID, String world, double value) {
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

	public void setWorld(String world) {
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

	public String getWorld() {
		return this.world;
	}

}
