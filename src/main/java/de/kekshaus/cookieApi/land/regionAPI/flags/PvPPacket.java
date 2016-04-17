package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PvPPacket {

	public static ProtectedRegion activateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		return region;

	}

	public static ProtectedRegion deactivateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.PVP, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);
		return region;

	}
}
