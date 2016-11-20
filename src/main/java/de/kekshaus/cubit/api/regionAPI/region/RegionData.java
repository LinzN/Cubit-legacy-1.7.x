package de.kekshaus.cubit.api.regionAPI.region;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionData {
	// private String regionID;
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

	public UUID getOwnerUUID() {
		UUID playerUUID = null;
		for (UUID uuid : praseWGRegion().getOwners().getUniqueIds()) {
			playerUUID = uuid;
		}
		return playerUUID;
	}

	public String getOwnerName() {
		return Bukkit.getOfflinePlayer(getOwnerUUID()).getName();
	}

	public Set<UUID> getMembersUUID() {
		return praseWGRegion().getMembers().getUniqueIds();
	}

	public Set<String> getMembersName() {
		Set<String> list = new HashSet<String>();
		for (UUID uuid : getMembersUUID()) {
			list.add(Bukkit.getOfflinePlayer(uuid).getName());
		}
		return list;
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
