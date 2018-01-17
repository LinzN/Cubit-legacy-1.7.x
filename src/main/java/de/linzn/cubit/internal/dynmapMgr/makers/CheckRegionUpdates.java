package de.linzn.cubit.internal.dynmapMgr.makers;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CheckRegionUpdates implements Runnable {

    public CubitDynmap cubitDynmap;
    private Map<World, Set<String>> showedRegions;
    private boolean isSetup;

    public CheckRegionUpdates(CubitDynmap cubitDynmap) {
        this.cubitDynmap = cubitDynmap;
        this.isSetup = false;
    }


    @Override
    public void run() {
            if (!this.isSetup) {
                this.OnSetup();
                return;
            }
        Bukkit.getScheduler().runTaskAsynchronously(this.cubitDynmap.plugin, () -> {
            for (World world : this.cubitDynmap.regionPending.keySet()) {
                RegionManager regionManager = this.cubitDynmap.plugin.getWorldGuardPlugin().getRegionManager(world);
                Map<String, Boolean> worldRegions = this.cubitDynmap.regionPending.get(world);
                for (String regionID : worldRegions.keySet()) {
                    boolean isActive = worldRegions.get(regionID);
                    if (isActive) {
                        ProtectedRegion region = regionManager.getRegion(regionID);
                        if (region != null) {
                            this.cubitDynmap.updateRegionMarker(world, region);
                        }
                    } else {
                        this.cubitDynmap.removeRegionMarker(world, regionID);
                    }
                    this.cubitDynmap.regionPending.get(world).remove(regionID);

                }
            }

        });
    }


    private void OnSetup() {
        if (this.showedRegions == null) {
            this.showedRegions = new HashMap<>();
        }

        for (World world : Bukkit.getWorlds()) {
            RegionManager regionManager = this.cubitDynmap.plugin.getWorldGuardPlugin().getRegionManager(world);
            Set<String> worldRegions = new HashSet<>();
            this.showedRegions.put(world, worldRegions);

            for (ProtectedRegion protectedRegion : regionManager.getRegions().values()) {
                //Bukkit.getScheduler().runTask(CubitBukkitPlugin.inst(), () -> {
                    cubitDynmap.updateRegionMarker(world, protectedRegion);
                    worldRegions.add(protectedRegion.getId());
                //});
            }
        }
        this.isSetup = true;
    }
}
