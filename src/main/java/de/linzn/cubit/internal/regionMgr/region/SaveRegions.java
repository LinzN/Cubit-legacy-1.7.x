package de.linzn.cubit.internal.regionMgr.region;

import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class SaveRegions {
	public void save(final RegionData regionData) {
		saveData(regionData, regionData.getWorld());
	}

	public void save(final World world) {
		saveData(null, world);
	}

	public void saveData(final RegionData regionData, final World world) {
		CubitBukkitPlugin.inst().getServer().getScheduler().runTaskAsynchronously(CubitBukkitPlugin.inst(),
				new Runnable() {
					public void run() {
						try {
							RegionManager manager = CubitBukkitPlugin.inst().getWorldGuardPlugin()
									.getRegionManager(world);
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
