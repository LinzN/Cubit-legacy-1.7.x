package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class LockPacket {

	public static RegionData activateData(RegionData regionData) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		regionData.praseWGRegion().setFlag(groupFlag, RegionGroup.NON_MEMBERS);
		regionData.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.DENY);
		return regionData;

	}

	public static RegionData deactivateData(RegionData regionData) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		regionData.praseWGRegion().setFlag(groupFlag, RegionGroup.ALL);
		regionData.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		return regionData;

	}
}
