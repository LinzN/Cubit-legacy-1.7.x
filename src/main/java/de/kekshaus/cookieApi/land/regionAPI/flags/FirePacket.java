package de.kekshaus.cookieApi.land.regionAPI.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class FirePacket {
	public FirePacket() {

	}

	private RegionData activateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
		return regionData;

	}

	private RegionData deactivateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.ALLOW);
		return regionData;

	}

	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.FIRE_SPREAD) == StateFlag.State.DENY) {
			return true;
		}
		return false;
	}

	public RegionData switchState(RegionData regionData, boolean value) {
		if (value) {
			return activateData(regionData);
		} else {
			return deactivateData(regionData);
		}
	}

	public RegionData switchState(RegionData regionData) {
		if (getState(regionData)) {
			return switchState(regionData, false);
		} else {
			return switchState(regionData, true);
		}
	}
}
