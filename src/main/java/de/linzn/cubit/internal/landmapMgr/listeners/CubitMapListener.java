package de.linzn.cubit.internal.landmapMgr.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.linzn.cubit.internal.landmapMgr.LandmapManager;

public class CubitMapListener implements Listener {

	private final LandmapManager landmapMgr;

	public CubitMapListener(LandmapManager landmapMgr) {
		this.landmapMgr = landmapMgr;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		landmapMgr.unregisterScoreboardMap(event.getPlayer().getUniqueId());
	}


}
