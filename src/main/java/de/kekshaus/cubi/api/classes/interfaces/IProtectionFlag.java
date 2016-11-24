package de.kekshaus.cubi.api.classes.interfaces;

import org.bukkit.ChatColor;

import de.kekshaus.cubit.api.regionAPI.region.RegionData;

public interface IProtectionFlag {
	public abstract RegionData enablePacket(RegionData regionData);

	public abstract RegionData disablePacket(RegionData regionData);

	public abstract boolean getState(RegionData regionData);

	public abstract ChatColor getStateColor(RegionData regionData);

	public abstract RegionData switchState(RegionData regionData, boolean value, boolean save);

	public abstract RegionData switchState(RegionData regionData, boolean save);

	public abstract String getPacketName();
}
