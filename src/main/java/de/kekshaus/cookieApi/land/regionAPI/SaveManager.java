package de.kekshaus.cookieApi.land.regionAPI;

import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;
import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.regionAPI.region.RegionData;

public class SaveManager {
	public SaveManager(RegionData region, World world) {
		save(region, world);
	}

	private void save(final RegionData regionData, final World world) {
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
