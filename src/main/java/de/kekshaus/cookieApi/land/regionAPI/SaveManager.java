package de.kekshaus.cookieApi.land.regionAPI;

import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;

public class SaveManager {
	public SaveManager(ProtectedRegion region, World world) {
		save(region, world);
	}

	private void save(final ProtectedRegion region, final World world) {
		Landplugin.inst().getServer().getScheduler().runTaskAsynchronously(Landplugin.inst(), new Runnable() {
			public void run() {
				try {
					RegionManager manager = Landplugin.inst().getWorldGuardPlugin().getRegionManager(world);
					if (region != null) {
						manager.addRegion(region);
					}
					manager.saveChanges();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
