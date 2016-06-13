package de.kekshaus.cubit.land.api.regionAPI.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cubit.land.Landplugin;

public class ManageRegionEntities {

	public List<RegionData> setOwner(List<RegionData> regionListe, World world, UUID playerUUID) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		LocalPlayer lPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
		DefaultDomain domain = new DefaultDomain();
		domain.addPlayer(lPlayer);
		if (regionListe.size() <= 30) {

			for (RegionData region : regionListe) {
				region.praseWGRegion().setOwners(domain);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<RegionData> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (RegionData region : list) {
					region.praseWGRegion().setOwners(domain);
				}
			}

		}
		return regionListe;

	}

	public List<RegionData> addMember(List<RegionData> regionListe, World world, UUID playerUUID) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		LocalPlayer lPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
		if (regionListe.size() <= 30) {

			for (RegionData region : regionListe) {
				region.praseWGRegion().getMembers().addPlayer(lPlayer);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<RegionData> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (RegionData region : list) {
					region.praseWGRegion().getMembers().addPlayer(lPlayer);
				}
			}

		}
		return regionListe;

	}

	public List<RegionData> removeMember(List<RegionData> regionListe, World world, UUID playerUUID) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		LocalPlayer lPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
		if (regionListe.size() <= 30) {

			for (RegionData region : regionListe) {
				region.praseWGRegion().getMembers().removePlayer(lPlayer);
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<RegionData> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (RegionData region : list) {
					region.praseWGRegion().getMembers().removePlayer(lPlayer);
				}
			}

		}
		return regionListe;

	}

	public List<RegionData> clearMember(List<RegionData> regionListe, World world) {
		if (regionListe.size() <= 30) {

			for (RegionData region : regionListe) {
				region.praseWGRegion().getMembers().clear();
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<RegionData> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (RegionData region : list) {
					region.praseWGRegion().getMembers().clear();
				}
			}

		}
		return regionListe;

	}

	public List<RegionData> clearOwners(List<RegionData> regionListe, World world) {
		if (regionListe.size() <= 30) {

			for (RegionData region : regionListe) {
				region.praseWGRegion().getOwners().clear();
			}
		} else {

			int loops = regionListe.size() / 30 + 1;
			for (int i = 0; i < loops; i++) {
				List<RegionData> list = regionListe.subList(i * 30,
						30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

				for (RegionData region : list) {
					region.praseWGRegion().getOwners().clear();
				}
			}

		}
		return regionListe;

	}

	public List<ProtectedRegion> getAllLands(UUID playerUUID, World world) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		LocalPlayer lPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
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
