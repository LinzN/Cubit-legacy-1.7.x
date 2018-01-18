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

package de.linzn.cubit.internal.blockMgr;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public interface INMSMask {

    void refreshChunk(Chunk chunk);

    void sendTitle(Player paramPlayer, String paramString);

}
