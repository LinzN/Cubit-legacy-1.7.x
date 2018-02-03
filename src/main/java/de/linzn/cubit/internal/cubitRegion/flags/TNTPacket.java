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
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.ICubitPacket;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.ChatColor;

public class TNTPacket implements ICubitPacket {

    @Override
    public CubitLand enablePacket(CubitLand cubitLand) {
        cubitLand.getWGRegion().setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
        cubitLand.getWGRegion().setFlag(DefaultFlag.TNT, StateFlag.State.DENY);
        cubitLand.getWGRegion().setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
        return cubitLand;

    }

    @Override
    public CubitLand disablePacket(CubitLand cubitLand) {
        cubitLand.getWGRegion().setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
        cubitLand.getWGRegion().setFlag(DefaultFlag.TNT, StateFlag.State.ALLOW);
        cubitLand.getWGRegion().setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
        return cubitLand;

    }

    @Override
    public boolean getState(CubitLand cubitLand) {
        return cubitLand.getWGRegion().getFlag(DefaultFlag.OTHER_EXPLOSION) == StateFlag.State.DENY;
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
        CubitLand newCubitLand;
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
        return "TNT";
    }
}
