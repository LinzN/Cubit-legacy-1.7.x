/*
The MIT License (MIT)

Copyright (c) 2014 Joseph DeSimpliciis

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.linzn.cubit.internal.landMap;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

/**
 * File created by jcdesimp on 3/10/14. Updated by SpatiumPrinceps on 20/06/17
 */
public class MapManager implements Listener {
	private HashMap<String, LandMap> mapList;

	private CubitBukkitPlugin plugin;

	public MapManager(CubitBukkitPlugin plugin) {
		plugin.getLogger().info("Loading MapManager");
		this.plugin = plugin;
		this.mapList = new HashMap<String, LandMap>();
		this.plugin = plugin;
	}

	private void addMap(LandMap m) {
		mapList.put(m.getMapViewer().getName(), m);
	}

	public void toggleMap(Player p) {
		if (this.plugin.getYamlManager().getSettings().landUseScoreboardMap) {
			if (mapList.containsKey(p.getName())) {
				remMap(p.getName());
			} else {
				addMap(new LandMap(p, this.plugin));
			}
			// System.out.println(mapList.toString());
		}
	}

	public void remMap(String pName) {

		if (mapList.containsKey(pName)) {
			LandMap curr = mapList.get(pName);
			curr.removeMap();
			mapList.remove(pName);
		}
		// System.out.println("After Rem: "+mapList.toString());
	}

	public void removeAllMaps() {
		for (String k : mapList.keySet()) {
			mapList.get(k).removeMap();
		}
		mapList.clear();
	}

	public void updateAll() {
		for (String k : mapList.keySet()) {
			mapList.get(k).updateMap();
		}
	}

	@EventHandler
	public void playerLeave(PlayerQuitEvent event) {
		if (mapList.containsKey(event.getPlayer().getName())) {
			remMap(event.getPlayer().getName());
		}

	}

	@EventHandler
	public void playerWorldChange(PlayerChangedWorldEvent event) {
		if (mapList.containsKey(event.getPlayer().getName())) {
			remMap(event.getPlayer().getName());
		}
	}

	/**
	 * Update map when player teleports
	 *
	 * @param event
	 *            that was triggered
	 */
	@EventHandler
	public void playerTeleportKeepMap(PlayerTeleportEvent event) {
		final Player p = event.getPlayer();
		// mapList.get(p.getName()).updateMap();

		if (mapList.containsKey(event.getPlayer().getName())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					if (mapList.containsKey(p.getName())) {
						mapList.get(p.getName()).updateMap();
						// System.out.println("Updating map for " + p.getName()
						// + " after TP.");
					}
				}
			}, 15L);
		}
	}

	@EventHandler
	public void playerRespawn(PlayerRespawnEvent event) {
		final Player p = event.getPlayer();
		if (mapList.containsKey(event.getPlayer().getName())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					if (mapList.containsKey(p.getName())) {
						mapList.get(p.getName()).updateMap();
						// System.out.println("Updating map for " + p.getName()
						// + " after TP.");
					}
				}
			}, 15L);
		}
	}

}
