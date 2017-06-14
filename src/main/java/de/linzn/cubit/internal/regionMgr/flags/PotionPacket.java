package de.linzn.cubit.internal.regionMgr.flags;

import org.bukkit.ChatColor;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.IProtectionFlag;
import de.linzn.cubit.internal.regionMgr.region.RegionData;

public class PotionPacket implements IProtectionFlag {

	@Override
	public RegionData enablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		return regionData;

	}

	@Override
	public RegionData disablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);
		return regionData;

	}

	@Override
	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.POTION_SPLASH) == StateFlag.State.DENY) {
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
			CubitBukkitPlugin.inst().getRegionManager().getRegionSaver().save(regionData.getWorld());
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
		return "POTION";
	}
}
