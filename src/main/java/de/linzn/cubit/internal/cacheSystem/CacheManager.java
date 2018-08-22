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

package de.linzn.cubit.internal.cacheSystem;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

import java.util.UUID;

public class CacheManager {
    private CubitBukkitPlugin plugin;
    private NameCache nameCache;

    public CacheManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("[Setup] CacheManager");
        this.nameCache = new NameCache();
    }

    public String[] getPlayernames(UUID[] playerUUIDs) {
        String[] playerNames = new String[playerUUIDs.length];
        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = getPlayername(playerUUIDs[i]);
        }
        return playerNames;
    }

    public String getPlayername(UUID playerUUID) {
        return this.nameCache.getCacheName(playerUUID);
    }
}
