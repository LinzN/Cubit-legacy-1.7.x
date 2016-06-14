package de.kekshaus.cubit.land.plugin.listener;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;

public class AdditionalPhysicsListener implements Listener {

	@EventHandler
	public void onLiquidFlowOtherLand(final BlockFromToEvent event) {
		Chunk fromChunk = event.getBlock().getLocation().getChunk();
		Chunk toChunk = event.getToBlock().getLocation().getChunk();

		/* Maybe later using direct compare insteat of RegionData */
		RegionData fromLand = Landplugin.inst().getLandManager().praseRegionData(fromChunk.getWorld(), fromChunk.getX(),
				fromChunk.getZ());

		RegionData toLand = Landplugin.inst().getLandManager().praseRegionData(toChunk.getWorld(), toChunk.getX(),
				toChunk.getZ());

		if (toLand.getLandType() == LandTypes.NOTYPE) {
			return;
		}

		if (!Landplugin.inst().getLandManager().hasLandPermission(toLand, fromLand.getOwnerUUID())) {
			event.setCancelled(true);
		}
	}
}
