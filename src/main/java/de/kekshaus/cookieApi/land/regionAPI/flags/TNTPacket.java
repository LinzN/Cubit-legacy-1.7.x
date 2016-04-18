package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class TNTPacket {

	public static RegionData activateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.TNT, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
		return regionData;

	}

	public static RegionData deactivateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.TNT, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
		return regionData;

	}
}
