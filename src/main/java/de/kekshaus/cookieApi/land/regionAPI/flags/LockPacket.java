package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class LockPacket {

	public static ProtectedRegion activateData(ProtectedRegion region) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		region.setFlag(groupFlag, RegionGroup.NON_MEMBERS);
		region.setFlag(DefaultFlag.USE, StateFlag.State.DENY);
		return region;

	}

	public static ProtectedRegion deactivateData(ProtectedRegion region) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		region.setFlag(groupFlag, RegionGroup.ALL);
		region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		return region;

	}
}
