package de.kekshaus.cookieApi.land.api.regionAPI.flags;

import org.bukkit.ChatColor;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.IPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;

public class LockPacket implements IPacket {

	@Override
	public RegionData enablePacket(RegionData regionData) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		regionData.praseWGRegion().setFlag(groupFlag, RegionGroup.NON_MEMBERS);
		regionData.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.DENY);
		return regionData;

	}

	@Override
	public RegionData disablePacket(RegionData regionData) {
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		regionData.praseWGRegion().setFlag(groupFlag, RegionGroup.ALL);
		regionData.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		return regionData;

	}

	@Override
	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.USE) == StateFlag.State.DENY) {
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
		return "LOCK";
	}
}
