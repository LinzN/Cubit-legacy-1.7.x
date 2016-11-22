package de.kekshaus.cubit.api.regionAPI.region;

import java.util.Set;
import java.util.UUID;

import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
