/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.regionMgr.flags;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.IProtectionFlag;
import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.HashSet;

public class MonsterPacket implements IProtectionFlag {

    @Override
    @SuppressWarnings("serial")
    public RegionData enablePacket(RegionData regionData) {
        regionData.praseWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
        regionData.praseWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
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

            }
        });
        return regionData;

    }

    @Override
    @SuppressWarnings("serial")
    public RegionData disablePacket(RegionData regionData) {
        regionData.praseWGRegion().setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
        regionData.praseWGRegion().getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
            {

            }
        });
        return regionData;

    }

    @Override
    public boolean getState(RegionData regionData) {
        return regionData.praseWGRegion().getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.DENY;
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
        return "MONSTER";
    }
}
