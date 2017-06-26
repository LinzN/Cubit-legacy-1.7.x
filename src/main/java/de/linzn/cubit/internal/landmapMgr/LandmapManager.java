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

package de.linzn.cubit.internal.landmapMgr;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.landmapMgr.listeners.CubitMapListener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.HashMap;
import java.util.UUID;

/**
 * File created by jcdesimp on 3/10/14. Updated by SpatiumPrinceps on 20/06/17
 */
public class LandmapManager implements Listener {
	private HashMap<UUID, ScoreboardMap> mapList;

	private CubitBukkitPlugin plugin;

	public LandmapManager(CubitBukkitPlugin plugin) {
		plugin.getLogger().info("Loading LandmapManager");
		this.plugin = plugin;
		this.mapList = new HashMap<UUID, ScoreboardMap>();
		if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landUseScoreboardMap) {
			this.plugin.getServer().getPluginManager().registerEvents(new CubitMapListener(this), this.plugin);
		}
	}

	private void registerScoreboardMap(ScoreboardMap m) {
		mapList.put(m.getMapViewer().getUniqueId(), m);
		return;
	}

	public void toggleMap(Player p) {
		if (this.plugin.getYamlManager().getSettings().landUseScoreboardMap) {
			if (mapList.containsKey(p.getUniqueId())) {
				unregisterScoreboardMap(p.getUniqueId());
			} else {
				registerScoreboardMap(new ScoreboardMap(p, this.plugin));
			}
		}
	}

	public void unregisterScoreboardMap(UUID pUUID) {
		if (mapList.containsKey(pUUID)) {
			mapList.get(pUUID).removeMap();
			mapList.remove(pUUID);
		}
		return;
	}

	public void unregisterScoreboardMaps() {
		for (UUID k : mapList.keySet()) {
			mapList.get(k).removeMap();
		}
		mapList.clear();
		return;
	}

}
