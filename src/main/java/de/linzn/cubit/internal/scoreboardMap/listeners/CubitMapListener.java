/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.internal.scoreboardMap.listeners;

import de.linzn.cubit.internal.scoreboardMap.ScoreboardMapManager;
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
