/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.internal.dynmapMgr;


import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dynmapMgr.makers.CubitDynmap;

public class DynmapManager {

    public CubitDynmap cubitDynmap;
    private CubitBukkitPlugin plugin;


    public DynmapManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("Loading DynmapManager");
        this.plugin = plugin;
        if (this.plugin.getYamlManager().getSettings().useDynmap) {
            this.cubitDynmap = new CubitDynmap(this.plugin);
        }

    }


    public void disable() {
        this.cubitDynmap.stop();
    }

}
