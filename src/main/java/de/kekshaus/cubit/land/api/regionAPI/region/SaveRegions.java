package de.kekshaus.cubit.land.api.regionAPI.region;

import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.kekshaus.cubit.land.Landplugin;

public class SaveRegions {
	public void save(final RegionData regionData) {
		saveData(regionData, regionData.getWorld());
	}

	public void save(final World world) {
		saveData(null, world);
	}

	public void saveData(final RegionData regionData, final World world) {
		Landplugin.inst().getServer().getScheduler().runTaskAsynchronously(Landplugin.inst(), new Runnable() {
			public void run() {
				try {
					RegionManager manager = Landplugin.inst().getWorldGuardPlugin().getRegionManager(world);
					if (regionData != null && regionData.praseWGRegion() != null) {
						manager.addRegion(regionData.praseWGRegion());
					}
					manager.saveChanges();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
