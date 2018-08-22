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

package de.linzn.cubit.internal.scoreboardMap;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.scoreboardMap.listeners.CubitMapListener;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardMapManager {
    private HashMap<UUID, ScoreboardMap> scoreboardMaps;
    private CubitBukkitPlugin plugin;

    public ScoreboardMapManager(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardMaps = new HashMap<>();
        plugin.getLogger().info("[Setup] ScoreboardMapManager");
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landUseScoreboardMap) {
            this.plugin.getServer().getPluginManager().registerEvents(new CubitMapListener(this), this.plugin);
        }
    }

    public void toggleScoreboardMap(UUID playerUUID, boolean value) {
        if (value) {
            this.registerNewScoreboardMap(playerUUID);
        } else {
            this.unregisterExistScoreboardMap(playerUUID);
        }
        return;
    }

    public void toggleScoreboardMap(UUID playerUUID) {
        if (this.scoreboardMaps.containsKey(playerUUID)) {
            this.toggleScoreboardMap(playerUUID, false);
        } else {
            this.toggleScoreboardMap(playerUUID, true);
        }
        return;
    }

    public void registerNewScoreboardMap(UUID playerUUID) {
        ScoreboardMap scoreMap = new ScoreboardMap(this.plugin.getServer().getPlayer(playerUUID), this.plugin);
        this.scoreboardMaps.put(playerUUID, scoreMap);
        return;
    }

    public void unregisterExistScoreboardMap(UUID playerUUID) {
        if (this.scoreboardMaps.containsKey(playerUUID)) {
            this.scoreboardMaps.get(playerUUID).removeMap();
            this.scoreboardMaps.remove(playerUUID);
        }
        return;
    }

    public void cleanupAllScoreboardMaps() {
        for (UUID playerUUID : this.scoreboardMaps.keySet()) {
            this.unregisterExistScoreboardMap(playerUUID);
        }
        this.scoreboardMaps.clear();
        return;
    }
}
