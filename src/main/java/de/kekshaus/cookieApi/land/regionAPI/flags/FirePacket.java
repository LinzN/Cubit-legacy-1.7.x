package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class FirePacket {

	public static RegionData activateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
		return regionData;

	}

	public static RegionData deactivateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.ALLOW);
		return regionData;

	}
}
