package de.linzn.cubit.internal.landmapMgr.listeners;

import de.linzn.cubit.internal.landmapMgr.ScoreboardMapManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CubitMapListener implements Listener {

	private final ScoreboardMapManager scoreboardmapMgr;

	public CubitMapListener(ScoreboardMapManager scoreboardmapMgr) {
		this.scoreboardmapMgr = scoreboardmapMgr;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		scoreboardmapMgr.unregisterExistScoreboardMap(event.getPlayer().getUniqueId());
	}

}
