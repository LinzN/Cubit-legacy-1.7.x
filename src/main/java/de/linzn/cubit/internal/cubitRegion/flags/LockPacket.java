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

package de.linzn.cubit.internal.cubitRegion.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.ICubitPacket;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.ChatColor;

public class LockPacket implements ICubitPacket {

    @Override
    public CubitLand enablePacket(CubitLand cubitLand) {
        RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
        cubitLand.praseWGRegion().setFlag(groupFlag, RegionGroup.NON_MEMBERS);
        cubitLand.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.DENY);
        return cubitLand;

    }

    @Override
    public CubitLand disablePacket(CubitLand cubitLand) {
        RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
        cubitLand.praseWGRegion().setFlag(groupFlag, RegionGroup.ALL);
        cubitLand.praseWGRegion().setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
        return cubitLand;

    }

    @Override
    public boolean getState(CubitLand cubitLand) {
        return cubitLand.praseWGRegion().getFlag(DefaultFlag.USE) == StateFlag.State.DENY;
    }

    @Override
    public ChatColor getStateColor(CubitLand cubitLand) {
        if (getState(cubitLand)) {
            return ChatColor.GREEN;
        }
        return ChatColor.RED;
    }

    @Override
    public CubitLand switchState(CubitLand cubitLand, boolean value, boolean save) {
        CubitLand newCubitLand = cubitLand;
        if (value) {
            newCubitLand = enablePacket(cubitLand);
        } else {
            newCubitLand = disablePacket(cubitLand);
        }
        if (save) {
            CubitBukkitPlugin.inst().getRegionManager().getRegionSaver().save(cubitLand.getWorld());
        }
        return newCubitLand;
    }

    @Override
    public CubitLand switchState(CubitLand cubitLand, boolean save) {
        if (getState(cubitLand)) {
            return switchState(cubitLand, false, save);
        } else {
            return switchState(cubitLand, true, save);
        }
    }

    @Override
    public String getPacketName() {
        return "LOCK";
    }
}
