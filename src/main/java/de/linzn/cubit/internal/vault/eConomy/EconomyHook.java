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

package de.linzn.cubit.internal.vault.eConomy;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class EconomyHook {

    private Economy econ;
    private CubitBukkitPlugin plugin;

    public EconomyHook(CubitBukkitPlugin plugin, Economy econ) {
        this.econ = econ;
        this.plugin = plugin;

    }

    public boolean hasEnoughToBuy(UUID playerUUID, double value) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
        return (econ.getBalance(player) - value) >= 0;
    }

    public void transferMoney(UUID senderUUID, UUID receiverUUID, double value) {
        if (senderUUID != null) {
            OfflinePlayer sender = Bukkit.getOfflinePlayer(senderUUID);
            if (sender.getName() != null) {
                econ.withdrawPlayer(sender, value);
            } else {
                econ.withdrawPlayer(this.plugin.getCacheManager().getPlayername(senderUUID), value);
            }
        }
        if (receiverUUID != null) {
            OfflinePlayer receiver = Bukkit.getOfflinePlayer(receiverUUID);
            if (receiver.getName() != null) {
                econ.depositPlayer(receiver, value);
            } else {
                econ.depositPlayer(this.plugin.getCacheManager().getPlayername(receiverUUID), value);
            }
        }
    }

    public String formatToEconomy(double value) {
        return econ.format(value);
    }

}
