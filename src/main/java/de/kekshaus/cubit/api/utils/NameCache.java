package de.kekshaus.cubit.api.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;

public class NameCache {
	private Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
	private final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
	private Map<UUID, String> playerCache = new HashMap<UUID, String>();
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

	public String getCacheName(UUID uuid) {
		String prasedName = null;

		if (playerCache.containsKey(uuid)) {
			prasedName = playerCache.get(uuid);
		}

		if (prasedName == null) {
			prasedName = fetchBukkitName(uuid);
		}

		if (prasedName == null) {
			prasedName = fetchMojangName(uuid);
		}

		if (prasedName == null) {
			prasedName = "Unknown";
		}
		return prasedName;
	}
}
