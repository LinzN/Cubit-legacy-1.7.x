package de.kekshaus.cubit.plugin.listener;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import de.kekshaus.cubit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import de.linzn.cubit.internal.regionMgr.region.RegionData;

public class AdditionalPhysicsListener implements Listener {

	@EventHandler
	public void onLiquidFlowOtherLand(final BlockFromToEvent event) {
		Chunk fromChunk = event.getBlock().getLocation().getChunk();
		Chunk toChunk = event.getToBlock().getLocation().getChunk();

		/* Maybe later using direct compare insteat of RegionData */
		RegionData fromLand = CubitBukkitPlugin.inst().getRegionManager().praseRegionData(fromChunk.getWorld(),
				fromChunk.getX(), fromChunk.getZ());

		RegionData toLand = CubitBukkitPlugin.inst().getRegionManager().praseRegionData(toChunk.getWorld(), toChunk.getX(),
				toChunk.getZ());

		if (toLand.getLandType() == LandTypes.NOTYPE) {
			return;
		}

		if (fromLand.getLandType() == LandTypes.SERVER && toLand.getLandType() == LandTypes.SERVER) {
			return;
		}

		if (!CubitBukkitPlugin.inst().getRegionManager().hasLandPermission(toLand, fromLand.getOwnersUUID()[0])) {
			event.setCancelled(true);
		}
	}
}
