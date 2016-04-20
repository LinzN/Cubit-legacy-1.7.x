package de.kekshaus.cookieApi.land.api.regionAPI.flags;

import org.bukkit.ChatColor;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.kekshaus.cookieApi.land.api.regionAPI.IPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;

public class FirePacket implements IPacket {

	public RegionData enablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
		return regionData;

	}

	public RegionData disablePacket(RegionData regionData) {
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

	public ChatColor getStateColor(RegionData regionData) {
		if (getState(regionData)) {
			return ChatColor.GREEN;
		}
		return ChatColor.RED;
	}

	public RegionData switchState(RegionData regionData, boolean value) {
		if (value) {
			return enablePacket(regionData);
		} else {
			return disablePacket(regionData);
		}
	}

	public RegionData switchState(RegionData regionData) {
		if (getState(regionData)) {
			return switchState(regionData, false);
		} else {
			return switchState(regionData, true);
		}
	}

	@Override
	public String getPacketName() {
		return "FIRE";
	}
}
