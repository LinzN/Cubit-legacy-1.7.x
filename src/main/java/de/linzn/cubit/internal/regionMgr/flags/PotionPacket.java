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

package de.linzn.cubit.internal.regionMgr.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.IProtectionFlag;
import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.ChatColor;

public class PotionPacket implements IProtectionFlag {

    @Override
    public RegionData enablePacket(RegionData regionData) {
        regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
        return regionData;

    }

    @Override
    public RegionData disablePacket(RegionData regionData) {
        regionData.praseWGRegion().setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);
        return regionData;

    }

    @Override
    public boolean getState(RegionData regionData) {
        return regionData.praseWGRegion().getFlag(DefaultFlag.POTION_SPLASH) == StateFlag.State.DENY;
    }

    @Override
    public ChatColor getStateColor(RegionData regionData) {
        if (getState(regionData)) {
            return ChatColor.GREEN;
        }
        return ChatColor.RED;
    }

    @Override
    public RegionData switchState(RegionData regionData, boolean value, boolean save) {
        RegionData newRegionData = regionData;
        if (value) {
            newRegionData = enablePacket(regionData);
        } else {
            newRegionData = disablePacket(regionData);
        }
        if (save) {
            CubitBukkitPlugin.inst().getRegionManager().getRegionSaver().save(regionData.getWorld());
        }
        return newRegionData;
    }

    @Override
    public RegionData switchState(RegionData regionData, boolean save) {
        if (getState(regionData)) {
            return switchState(regionData, false, save);
        } else {
            return switchState(regionData, true, save);
        }
    }

    @Override
    public String getPacketName() {
        return "POTION";
    }
}
