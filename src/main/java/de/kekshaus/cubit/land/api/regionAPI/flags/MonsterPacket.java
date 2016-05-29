package de.kekshaus.cubit.land.api.regionAPI.flags;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.IPacket;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;

public class MonsterPacket implements IPacket {

	@Override
	@SuppressWarnings("serial")
	public RegionData enablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
		regionData.praseWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
			{
				add(EntityType.CREEPER);
				add(EntityType.ZOMBIE);
				add(EntityType.SKELETON);
				add(EntityType.SILVERFISH);
				add(EntityType.ENDER_DRAGON);
				add(EntityType.WITHER);
				add(EntityType.WITHER_SKULL);
				add(EntityType.GIANT);
				add(EntityType.PIG_ZOMBIE);
				add(EntityType.CAVE_SPIDER);
				add(EntityType.SPIDER);
				add(EntityType.WITCH);
				add(EntityType.ENDERMITE);
				add(EntityType.GUARDIAN);

			}
		});
		return regionData;

	}

	@Override
	@SuppressWarnings("serial")
	public RegionData disablePacket(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
		regionData.praseWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
			{

			}
		});
		return regionData;

	}

	@Override
	public boolean getState(RegionData regionData) {
		if (regionData.praseWGRegion().getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.DENY) {
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
		return "MONSTER";
	}
}
