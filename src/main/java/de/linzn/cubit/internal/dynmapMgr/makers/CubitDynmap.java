package de.linzn.cubit.internal.dynmapMgr.makers;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dynmapMgr.checkRegionListener.CubitRegionListener;
import de.linzn.cubit.internal.dynmapMgr.checkRegionListener.PluginLoadListener;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import java.util.HashMap;
import java.util.Map;


public class CubitDynmap {
    public CubitBukkitPlugin plugin;
    public Plugin dynmap;
    public Map<World, Map<String, Boolean>> regionPending; // boolean value true = new region, false deleted region
    private DynmapAPI api;
    private MarkerAPI markerapi;
    private MarkerSet markerSet;
    private long updperiod;
    private AreaStyle landStyle;
    private AreaStyle serverStyle;
    private AreaStyle shopStyle;

    public CubitDynmap(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.regionPending = new HashMap<>();
        this.dynmap = Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (this.dynmap == null) {
            System.out.println("Cannot find dynmap!");
            return;
        } else {
            this.api = (DynmapAPI) this.dynmap;
            WorldGuardPlugin wg = this.plugin.getWorldGuardPlugin();

            this.plugin.getServer().getPluginManager().registerEvents(new PluginLoadListener(this), this.plugin);

            if (this.dynmap.isEnabled() && wg.isEnabled())
                initialize();
        }
    }

    private String formatInfoBox(ProtectedRegion region, AreaMarker m, LandTypes cubitType) {
        String typeString;

        switch (cubitType) {
            case SERVER:
                typeString = "<div class=\"infowindow\"><span style=\"font-size:120%;\">Type: SERVER<br> %cubitLand%</span></div>";
                break;
            case SHOP:
                typeString = "<div class=\"infowindow\"><span style=\"font-size:120%;\">Type: SHOP<br> %cubitLand%</span></div>";
                break;
            case WORLD:
                typeString = "<div class=\"infowindow\"><span style=\"font-size:120%;\">Type: LAND<br> %cubitLand%</span></div>";
                break;
            default:
                typeString = "<div class=\"infowindow\"><span style=\"font-size:120%;\">Type: LAND<br> %cubitLand%</span></div>";
                break;
        }

        String v = "<div class=\"cubitInfo\">" + typeString + "</div>";
        v = v.replace("%cubitLand%", m.getLabel());
        return v;
    }


    private void addStyle(AreaMarker m, ProtectedRegion region, LandTypes cubitType, boolean hasOwner) {
        AreaStyle as;
        switch (cubitType) {
            case SERVER:
                as = serverStyle;
                break;
            case SHOP:
                as = shopStyle;
                if (!hasOwner) {
                    as = new AreaStyle(LandTypes.SHOP);
                    as.fillColor = "#0db914";
                }
                break;
            case WORLD:
                as = landStyle;
                break;
            default:
                as = landStyle;
                break;
        }

        int fillColor = Integer.parseInt(as.fillColor.substring(1), 16);
        m.setLineStyle(0, 0, 0);
        m.setFillStyle(as.fillTransparency, fillColor);
    }

    void removeRegionMarker(World world, String regionID) {
        String markerId = world.getName() + "_" + regionID;
        this.markerSet.findAreaMarker(markerId).deleteMarker();
    }


    void updateRegionMarker(World world, ProtectedRegion region) {
        String name = region.getId();
        double[] x;
        double[] z;

        String regionId = region.getId();
        LandTypes cubitType = LandTypes.WORLD;
        boolean hasOwner = true;
        if (region.getOwners().getUniqueIds().isEmpty()) {
            hasOwner = false;
        }

        if (regionId.startsWith(LandTypes.SHOP.toString().toLowerCase())) {
            cubitType = LandTypes.SHOP;
        } else if (regionId.startsWith(LandTypes.SERVER.toString().toLowerCase())) {
            cubitType = LandTypes.SERVER;
        }

        RegionType tn = region.getType();
        BlockVector l0 = region.getMinimumPoint();
        BlockVector l1 = region.getMaximumPoint();

        if (tn == RegionType.CUBOID) {
            x = new double[4];
            z = new double[4];
            x[0] = l0.getX();
            z[0] = l0.getZ();
            x[1] = l0.getX();
            z[1] = l1.getZ() + 1.0;
            x[2] = l1.getX() + 1.0;
            z[2] = l1.getZ() + 1.0;
            x[3] = l1.getX() + 1.0;
            z[3] = l0.getZ();
        } else {
            return;
        }
        String markerId = world.getName() + "_" + regionId;
        AreaMarker m = this.markerSet.findAreaMarker(markerId);
        if (m == null) {
            m = this.markerSet.createAreaMarker(markerId, name, false, world.getName(), x, z, false);
            if (m == null)
                return;
        } else {
            m.setCornerLocations(x, z);
            m.setLabel(name);
        }
        addStyle(m, region, cubitType, hasOwner);
        m.setDescription(formatInfoBox(region, m, cubitType));
    }

    public void initialize() {
        this.markerapi = this.api.getMarkerAPI();
        if (this.markerapi == null) {
            this.plugin.getLogger().info("makerapi error");
            return;
        }

        this.markerSet = this.markerapi.getMarkerSet("cubit.markerset");
        if (this.markerSet == null)
            this.markerSet = this.markerapi.createMarkerSet("cubit.markerset", "Cubit", null, false);
        else
            this.markerSet.setMarkerSetLabel("Cubit");
        if (this.markerSet == null) {
            this.plugin.getLogger().info("makerset error");
            return;
        }

        this.markerSet.setLayerPriority(10);
        this.markerSet.setHideByDefault(false);


        this.landStyle = new AreaStyle(LandTypes.WORLD);
        this.shopStyle = new AreaStyle(LandTypes.SHOP);
        this.serverStyle = new AreaStyle(LandTypes.SERVER);

        this.updperiod = (long) (5 * 20);
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new CheckRegionUpdates(this), 40, this.updperiod);
        this.plugin.getServer().getPluginManager().registerEvents(new CubitRegionListener(this.plugin), this.plugin);
        this.plugin.getLogger().info("Cubit dynmap module enabled");
    }

    public void stop() {
        if (this.markerSet != null) {
            this.markerSet.deleteMarkerSet();
            this.markerSet = null;
        }
    }

}
