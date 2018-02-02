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

package de.linzn.cubit.internal.cubitRegion;

import org.bukkit.Bukkit;

public enum CubitType {
    SERVER, SHOP, WORLD, NOTYPE;

    public static CubitType getLandType(String regionName) {
        String cutName = regionName.split("_")[0];
        for (CubitType type : CubitType.values()) {
            if (type.toString().equalsIgnoreCase(cutName)) {
                return CubitType.valueOf(cutName.toUpperCase());
            }
        }
        if (Bukkit.getWorld(cutName) != null) {
            return WORLD;
        } else {
            return NOTYPE;
        }

    }

}