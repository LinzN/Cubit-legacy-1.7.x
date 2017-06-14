package de.linzn.cubit.internal.regionMgr;

import org.bukkit.ChatColor;

import de.linzn.cubit.internal.regionMgr.region.RegionData;

public interface IProtectionFlag {
	public abstract RegionData enablePacket(RegionData regionData);

	public abstract RegionData disablePacket(RegionData regionData);

	public abstract boolean getState(RegionData regionData);

	public abstract ChatColor getStateColor(RegionData regionData);

	public abstract RegionData switchState(RegionData regionData, boolean value, boolean save);

	public abstract RegionData switchState(RegionData regionData, boolean save);

	public abstract String getPacketName();
}
