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
import org.bukkit.entity.EntityType;

import java.util.HashSet;

public class MonsterPacket implements ICubitPacket {

    @Override

    public CubitLand enablePacket(CubitLand cubitLand) {
        cubitLand.getWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
        cubitLand.getWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
            {
                add(EntityType.CREEPER);
                add(EntityType.ZOMBIE);
                add(EntityType.SKELETON);
                add(EntityType.SILVERFISH);
                add(EntityType.ENDER_DRAGON);
                add(EntityType.WITHER);
                add(EntityType.WITHER_SKULL);
                add(EntityType.GIANT);
                add(EntityType.PIG_ZOMBIE);
                add(EntityType.CAVE_SPIDER);
                add(EntityType.SPIDER);
                add(EntityType.WITCH);
                add(EntityType.ENDERMITE);
                add(EntityType.GUARDIAN);
                add(EntityType.ZOMBIE_VILLAGER);
                add(EntityType.HUSK);
                add(EntityType.POLAR_BEAR);
                add(EntityType.EVOKER);
                add(EntityType.SHULKER);
                add(EntityType.MAGMA_CUBE);
                add(EntityType.STRAY);
                add(EntityType.VEX);
                add(EntityType.VINDICATOR);
            }
        });
        return cubitLand;

    }

    @Override
    @SuppressWarnings("serial")
    public CubitLand disablePacket(CubitLand cubitLand) {
        cubitLand.getWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
        cubitLand.getWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
            {

            }
        });
        return cubitLand;

    }

    @Override
    public boolean getState(CubitLand cubitLand) {
        return cubitLand.getWGRegion().getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.DENY;
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
        return "MONSTER";
    }
}
