package de.kekshaus.cubit.api.regionAPI.flags;

import org.bukkit.ChatColor;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.kekshaus.cubit.api.classes.interfaces.IProtectionFlag;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

public class FirePacket implements IProtectionFlag {

	@Override
	public RegionData enablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
		return regionData;

	}

	@Override
	public RegionData disablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.LIGHTNING, StateFlag.State.ALLOW);
		return regionData;

	}

	@Override
	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.FIRE_SPREAD) == StateFlag.State.DENY) {
			return true;
		}
		return false;
	}

	@Override
	public ChatColor getStateColor(RegionData regionData) {
		if (getState(regionData)) {
			return ChatColor.GREEN;
		}
		return ChatColor.RED;
	}

	@Override
	public RegionData switchState(RegionData regionData, boolean value, boolean save) {
		RegionData newRegionData = regionData;
		if (value) {
			newRegionData = enablePacket(regionData);
		} else {
			newRegionData = disablePacket(regionData);
		}
		if (save) {
			Landplugin.inst().getRegionManager().getRegionSaver().save(regionData.getWorld());
		}
		return newRegionData;
	}

	@Override
	public RegionData switchState(RegionData regionData, boolean save) {
		if (getState(regionData)) {
			return switchState(regionData, false, save);
		} else {
			return switchState(regionData, true, save);
		}
	}

	@Override
	public String getPacketName() {
		return "FIRE";
	}
}
