package de.kekshaus.cookieApi.land.regionAPI.flags;

import java.util.HashSet;

import org.bukkit.entity.EntityType;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class MobPacket {

	@SuppressWarnings("serial")
	public static RegionData activateData(RegionData regionData) {
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

	@SuppressWarnings("serial")
	public static RegionData deactivateData(RegionData regionData) {
		regionData.praseWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
		regionData.praseWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
			{

			}
		});
		return regionData;

	}
}
