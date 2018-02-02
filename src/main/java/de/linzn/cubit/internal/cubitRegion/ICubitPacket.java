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

import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.ChatColor;

public interface ICubitPacket {
    CubitLand enablePacket(CubitLand cubitLand);

    CubitLand disablePacket(CubitLand cubitLand);

    boolean getState(CubitLand cubitLand);

    ChatColor getStateColor(CubitLand cubitLand);

    CubitLand switchState(CubitLand cubitLand, boolean value, boolean save);

    CubitLand switchState(CubitLand cubitLand, boolean save);

    String getPacketName();
}
