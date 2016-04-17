package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class FirePacket {

	public static ProtectedRegion activateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
		return region;

	}

	public static ProtectedRegion deactivateData(ProtectedRegion region) {
		region.setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.LIGHTNING, StateFlag.State.ALLOW);
		return region;

	}
}
