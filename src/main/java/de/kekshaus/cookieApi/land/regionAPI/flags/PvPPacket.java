package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class PvPPacket {

	public static RegionData activateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		return regionData;

	}

	public static RegionData deactivateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.PVP, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);
		return regionData;

	}
}
