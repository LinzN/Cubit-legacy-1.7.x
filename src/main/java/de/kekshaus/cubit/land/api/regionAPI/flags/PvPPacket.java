package de.kekshaus.cubit.land.api.regionAPI.flags;

import org.bukkit.ChatColor;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.IPacket;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;

public class PvPPacket implements IPacket {

	@Override
	public RegionData enablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		return regionData;

	}

	@Override
	public RegionData disablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.PVP, StateFlag.State.ALLOW);
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);
		return regionData;

	}

	@Override
	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.PVP) == StateFlag.State.DENY) {
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
			Landplugin.inst().getLandManager().getRegionSaver().save(regionData.getWorld());
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
		return "PVP";
	}
}
