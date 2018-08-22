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

package de.linzn.cubit.internal.blockEdit.normal.nmsPackets;

import de.linzn.cubit.internal.blockEdit.INMSMask;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class NMS_default implements INMSMask {

    @SuppressWarnings("deprecation")
    @Override
    public void refreshChunk(Chunk chunk) {
        // TODO Auto-generated method stub
        chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());

    }

    @SuppressWarnings("deprecation")
    @Override
    public void sendTitle(Player paramPlayer, String paramString) {
        // TODO Auto-generated method stub
        paramPlayer.sendTitle(paramString, "");

    }

}
