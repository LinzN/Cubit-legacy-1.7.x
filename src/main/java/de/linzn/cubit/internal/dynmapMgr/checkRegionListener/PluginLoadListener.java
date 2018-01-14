package de.linzn.cubit.internal.dynmapMgr.checkRegionListener;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dynmapMgr.makers.CubitDynmap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class PluginLoadListener implements Listener {
    CubitDynmap cubitDynmap;


    public PluginLoadListener(CubitDynmap cubitDynmap) {
        this.cubitDynmap = cubitDynmap;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin p = event.getPlugin();
        String name = p.getDescription().getName();
        if (name.equals("dynmap") || name.equals("WorldGuard")) {
            if (this.cubitDynmap.dynmap.isEnabled() && CubitBukkitPlugin.inst().getWorldGuardPlugin().isEnabled())
                this.cubitDynmap.initialize();
        }
    }
}
