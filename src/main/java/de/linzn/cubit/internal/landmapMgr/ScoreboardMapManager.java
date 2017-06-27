package de.linzn.cubit.internal.landmapMgr;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.landmapMgr.listeners.CubitMapListener;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardMapManager {
    private HashMap<UUID, ScoreboardMap> scoreboardMaps;
    private CubitBukkitPlugin plugin;

    public ScoreboardMapManager(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardMaps = new HashMap<>();
        plugin.getLogger().info("Loading ScoreboardMapManager");
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landUseScoreboardMap) {
            this.plugin.getServer().getPluginManager().registerEvents(new CubitMapListener(this), this.plugin);
        }
    }

    public void toggleScoreboardMap(UUID playerUUID, boolean value) {
        if (value) {
            this.unregisterExistScoreboardMap(playerUUID);
        } else {
            this.registerNewScoreboardMap(playerUUID);
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
