package de.kekshaus.cookieApi.land.regionAPI.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;

public class RegionEntitiesMulti {

	public void permformData(LocalPlayer lPlayer, List<ProtectedRegion> regionListe, World world) {
		if (regionListe.size() <= 30) {

			for (ProtectedRegion region : regionListe) {
				region.getMembers().addPlayer(lPlayer);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<ProtectedRegion> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (ProtectedRegion region : list) {
					region.getMembers().addPlayer(lPlayer);
				}
			}

		}

	}

	public List<ProtectedRegion> getLandsOfPlayer(LocalPlayer lPlayer, World world) {
		RegionManager rm = Landplugin.inst().getWorldGuardPlugin().getRegionManager(world);
		List<ProtectedRegion> toReturn = new ArrayList<ProtectedRegion>();

		for (Map.Entry<String, ProtectedRegion> entry : rm.getRegions().entrySet()) {
			if (entry.getValue().getOwners().contains(lPlayer.getUniqueId())) {
				toReturn.add(entry.getValue());
			}
		}

		return toReturn;
	}
}
