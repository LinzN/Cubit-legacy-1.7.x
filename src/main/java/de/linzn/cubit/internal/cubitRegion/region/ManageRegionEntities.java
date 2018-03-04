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

package de.linzn.cubit.internal.cubitRegion.region;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageRegionEntities {

    public List<CubitLand> setOwner(List<CubitLand> regionListe, World world, OfflinePlayer player) {
        LocalPlayer lPlayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
        DefaultDomain domain = new DefaultDomain();
        domain.addPlayer(lPlayer);
        if (regionListe.size() <= 30) {

            for (CubitLand region : regionListe) {
                region.getWGRegion().setOwners(domain);
            }
        } else {

            int loops = regionListe.size() / 30 + 1;
            for (int i = 0; i < loops; i++) {
                List<CubitLand> list = regionListe.subList(i * 30,
                        30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

                for (CubitLand region : list) {
                    region.getWGRegion().setOwners(domain);
                }
            }

        }
        return regionListe;

    }

    public List<CubitLand> addMember(List<CubitLand> regionListe, World world, OfflinePlayer player) {
        LocalPlayer lPlayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
        if (regionListe.size() <= 30) {

            for (CubitLand region : regionListe) {
                region.getWGRegion().getMembers().addPlayer(lPlayer);
            }
        } else {

            int loops = regionListe.size() / 30 + 1;
            for (int i = 0; i < loops; i++) {
                List<CubitLand> list = regionListe.subList(i * 30,
                        30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

                for (CubitLand region : list) {
                    region.getWGRegion().getMembers().addPlayer(lPlayer);
                }
            }

        }
        return regionListe;

    }

    public List<CubitLand> removeMember(List<CubitLand> regionListe, World world, OfflinePlayer player) {
        LocalPlayer lPlayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
        if (regionListe.size() <= 30) {

            for (CubitLand region : regionListe) {
                region.getWGRegion().getMembers().removePlayer(lPlayer);
            }
        } else {

            int loops = regionListe.size() / 30 + 1;
            for (int i = 0; i < loops; i++) {
                List<CubitLand> list = regionListe.subList(i * 30,
                        30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

                for (CubitLand region : list) {
                    region.getWGRegion().getMembers().removePlayer(lPlayer);
                }
            }

        }
        return regionListe;

    }

    public List<CubitLand> clearMember(List<CubitLand> regionListe, World world) {
        if (regionListe.size() <= 30) {

            for (CubitLand region : regionListe) {
                region.getWGRegion().getMembers().clear();
            }
        } else {

            int loops = regionListe.size() / 30 + 1;
            for (int i = 0; i < loops; i++) {
                List<CubitLand> list = regionListe.subList(i * 30,
                        30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

                for (CubitLand region : list) {
                    region.getWGRegion().getMembers().clear();
                }
            }

        }
        return regionListe;

    }

    public List<CubitLand> clearOwners(List<CubitLand> regionListe, World world) {
        if (regionListe.size() <= 30) {

            for (CubitLand region : regionListe) {
                region.getWGRegion().getOwners().clear();
            }
        } else {

            int loops = regionListe.size() / 30 + 1;
            for (int i = 0; i < loops; i++) {
                List<CubitLand> list = regionListe.subList(i * 30,
                        30 * i + 29 >= regionListe.size() ? regionListe.size() : 30 * i + 29);

                for (CubitLand region : list) {
                    region.getWGRegion().getOwners().clear();
                }
            }

        }
        return regionListe;

    }

    public List<ProtectedRegion> getRegionList(OfflinePlayer player, World world, CubitType type) {
        LocalPlayer lPlayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
        RegionManager rm = CubitBukkitPlugin.inst().getWorldGuardPlugin().getRegionManager(world);
        List<ProtectedRegion> toReturn = new ArrayList<>();

        for (Map.Entry<String, ProtectedRegion> entry : rm.getRegions().entrySet()) {
            if (entry.getValue().getOwners().contains(lPlayer)) {
                CubitLand cubitLand = new CubitLand(world);
                cubitLand.setWGRegion(entry.getValue(), false);
                if (cubitLand.getCubitType() == type || type == CubitType.NOTYPE) {
                    toReturn.add(entry.getValue());
                }
            }
        }

        return toReturn;
    }
}
