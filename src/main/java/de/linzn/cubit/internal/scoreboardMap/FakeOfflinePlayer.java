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

package de.linzn.cubit.internal.scoreboardMap;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class FakeOfflinePlayer implements OfflinePlayer {
    String name;

    public FakeOfflinePlayer(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return null;
    }

    public boolean hasPlayedBefore() {
        return false;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return null;
    }

    public long getFirstPlayed() {
        return 0;
    }

    public boolean isBanned() {
        return false;
    }

    @Deprecated
    public void setBanned(boolean b) {
        return;
    }

    public Map<String, Object> serialize() {
        return null;
    }

    public boolean isWhitelisted() {
        return true;
    }

    public void setWhitelisted(boolean b) {
        return;
    }

    public Location getBedSpawnLocation() {
        return null;
    }

    public boolean isOnline() {
        return false;
    }

    public long getLastPlayed() {
        return 0;
    }

    public boolean isOp() {
        return false;
    }

    public void setOp(boolean b) {
        return;
    }
}
