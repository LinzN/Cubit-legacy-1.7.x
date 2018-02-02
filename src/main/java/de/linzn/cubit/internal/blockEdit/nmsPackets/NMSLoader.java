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

package de.linzn.cubit.internal.blockEdit.nmsPackets;

import de.linzn.cubit.internal.blockEdit.INMSMask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class NMSLoader {

    private HashSet<String> nmsList = new HashSet<>();
    private INMSMask nmsMask;
    private Plugin plugin;

    public NMSLoader(Plugin plugin) {
        this.plugin = plugin;
        addVersions();
        loadNMSClass();
    }

    private static String getVersion() {
        String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
        if (array.length == 4)
            return array[3];
        return "";
    }

    public void loadNMSClass() {

        String versionNumber = "v0_R1";
        if (this.nmsList.contains(getVersion())) {
            versionNumber = getVersion();
            plugin.getLogger().info("Using " + getVersion() + " for NMS Class");
        } else {
            plugin.getLogger().info(
                    "No version found for " + getVersion() + "! Fallback to nonNMS. Chunk-Refresh will not work!");
        }

        Object obj = null;
        try {
            obj = Class.forName("de.linzn.cubit.internal.blockEdit.nmsPackets.NMS_" + versionNumber).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.nmsMask = (INMSMask) obj;

    }

    public INMSMask nmsHandler() {
        return this.nmsMask;
    }

    private void addVersions() {
        this.nmsList.add("v1_12_R1");
    }

}
