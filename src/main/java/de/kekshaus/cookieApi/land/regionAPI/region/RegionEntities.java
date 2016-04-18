package de.kekshaus.cookieApi.land.regionAPI.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;

public class RegionEntities {

	public static List<ProtectedRegion> setOwner(List<ProtectedRegion> regionListe, World world, LocalPlayer lPlayer) {
		DefaultDomain domain = new DefaultDomain();
		domain.addPlayer(lPlayer);
		if (regionListe.size() <= 30) {

			for (ProtectedRegion region : regionListe) {
				region.setOwners(domain);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<ProtectedRegion> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (ProtectedRegion region : list) {
					region.setOwners(domain);
				}
			}

		}
		return regionListe;

	}

	public static List<ProtectedRegion> addMember(List<ProtectedRegion> regionListe, World world, LocalPlayer lPlayer) {
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
		return regionListe;

	}

	public static List<ProtectedRegion> removeMember(List<ProtectedRegion> regionListe, World world,
			LocalPlayer lPlayer) {
		if (regionListe.size() <= 30) {

			for (ProtectedRegion region : regionListe) {
				region.getMembers().removePlayer(lPlayer);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<ProtectedRegion> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (ProtectedRegion region : list) {
					region.getMembers().removePlayer(lPlayer);
				}
			}

		}
		return regionListe;

	}

	public static List<ProtectedRegion> clearMember(List<ProtectedRegion> regionListe, World world) {
		if (regionListe.size() <= 30) {

			for (ProtectedRegion region : regionListe) {
				region.getMembers().clear();
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<ProtectedRegion> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (ProtectedRegion region : list) {
					region.getMembers().clear();
				}
			}

		}
		return regionListe;

	}

	public static List<ProtectedRegion> getLandsOfPlayer(LocalPlayer lPlayer, World world) {
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
