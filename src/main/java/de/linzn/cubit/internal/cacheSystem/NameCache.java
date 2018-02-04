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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NameCache {
    private final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private Map<UUID, String> playerCache = new HashMap<>();
    private String name;

    private String fetchMojangName(UUID uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            NameCache[] nameHistory = gson.fromJson(
                    new BufferedReader(new InputStreamReader(connection.getInputStream())), NameCache[].class);
            NameCache currentNameData = nameHistory[nameHistory.length - 1];
            playerCache.put(uuid, currentNameData.name);
            return currentNameData.name;
        } catch (Exception e) {
            return null;
        }

    }

    private String fetchBukkitName(UUID uuid) {
        try {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            String playerName = offlinePlayer.getName();
            if (playerName != null) {
                playerCache.put(uuid, playerName);
            }
            return playerName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private String fetchDatabaseName(UUID uuid) {
        String playerName = CubitBukkitPlugin.inst().getDataAccessManager().databaseType.get_profile_name(uuid);
        if (playerName != null) {
            playerCache.put(uuid, playerName);
        }
        return playerName;
    }

    public String getCacheName(UUID uuid) {
        String playerName = null;

        if (playerCache.containsKey(uuid)) {
            playerName = playerCache.get(uuid);
        }
        if (playerName == null) {
            playerName = fetchDatabaseName(uuid);
        }

        if (playerName == null) {
            playerName = fetchBukkitName(uuid);
        }

        if (playerName == null) {
            playerName = fetchMojangName(uuid);
        }

        if (playerName == null) {
            playerName = "Unknown";
        }
        return playerName;
    }
}
