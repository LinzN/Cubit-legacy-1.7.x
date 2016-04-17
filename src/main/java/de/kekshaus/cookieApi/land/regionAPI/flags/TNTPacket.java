package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class TNTPacket {

	public static ProtectedRegion activateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.TNT, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
		return region;

	}

	public static ProtectedRegion deactivateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.TNT, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
		return region;

	}
}
