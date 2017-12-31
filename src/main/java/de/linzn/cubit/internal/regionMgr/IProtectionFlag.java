/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.regionMgr;

import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.ChatColor;

public interface IProtectionFlag {
    RegionData enablePacket(RegionData regionData);

    RegionData disablePacket(RegionData regionData);

    boolean getState(RegionData regionData);

    ChatColor getStateColor(RegionData regionData);

    RegionData switchState(RegionData regionData, boolean value, boolean save);

    RegionData switchState(RegionData regionData, boolean save);

    String getPacketName();
}
