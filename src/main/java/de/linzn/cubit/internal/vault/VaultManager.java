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

package de.linzn.cubit.internal.vault;

import com.sk89q.worldguard.LocalPlayer;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.vault.eConomy.EconomyHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class VaultManager {

    private CubitBukkitPlugin plugin;
    private Economy econ = null;
    private EconomyHook ecoMrg = null;

    public VaultManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("[Setup] VaultManager");
        this.plugin = plugin;
        if (setupEconomy()) {
            this.ecoMrg = new EconomyHook(plugin, econ);
        }
    }

    private boolean setupEconomy() {
        if (!CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy) {
            this.plugin.getLogger().info("Economy is disabled in settings.yml!");
            return false;
        }
        return hookEconomy(true);
    }

    private boolean hookEconomy(boolean withInfo) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            if (withInfo) {
                this.plugin.getLogger().warning("Vault not found. Economy is disabled.");
            }
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            if (withInfo) {
                this.plugin.getLogger().warning("No valid economy plugin found. Economy is disabled.");
            }
            return false;
        } else {
            this.plugin.getLogger().info("Hooked into economy plugin!");
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public boolean hasEnougToBuy(UUID playerUUID, double value) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy && econ == null) {
            if (hookEconomy(false)) {
                this.ecoMrg = new EconomyHook(plugin, econ);
            }
        }

        if (this.econ != null) {
            return this.ecoMrg.hasEnoughToBuy(playerUUID, Math.abs(value));
        }
        return true;

    }

    public boolean transferMoney(UUID senderUUID, UUID recieverUUID, double value) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy && econ == null) {
            if (hookEconomy(false)) {
                this.ecoMrg = new EconomyHook(plugin, econ);
            }
        }

        if (this.econ != null) {
            try {
                ecoMrg.transferMoney(senderUUID, recieverUUID, Math.abs(value));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public double calculateLandCost(UUID uuid, World world, boolean buyTask) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy && econ == null) {
            if (hookEconomy(false)) {
                this.ecoMrg = new EconomyHook(plugin, econ);
            }
        }

        double landBasePrice = CubitBukkitPlugin.inst().getYamlManager().getSettings().landBasePrice;
        double landTaxAddition = CubitBukkitPlugin.inst().getYamlManager().getSettings().landTaxAddition;
        double landMaxPrice = CubitBukkitPlugin.inst().getYamlManager().getSettings().landMaxPrice;
        double landSellPercent = CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellPercent;
        LocalPlayer localplayer = CubitBukkitPlugin.inst().getWorldGuardPlugin()
                .wrapOfflinePlayer(Bukkit.getOfflinePlayer(uuid));
        double regionSize = CubitBukkitPlugin.inst().getWorldGuardPlugin().getRegionManager(world)
                .getRegionCountOfPlayer(localplayer);
        double price;

        if (buyTask) {
            price = landBasePrice + (landTaxAddition * regionSize);
            if (price > landMaxPrice) {
                price = landMaxPrice;
            }

        } else {
            double sellValue = landBasePrice + (landTaxAddition * regionSize);
            if (sellValue > landMaxPrice) {
                sellValue = landMaxPrice;
            }
            price = (sellValue * landSellPercent);
        }
        return price;
    }

    public String formattingToEconomy(double value) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy && econ == null) {
            if (hookEconomy(false)) {
                this.ecoMrg = new EconomyHook(plugin, econ);
            }
        }
        if (this.econ != null) {
            return ecoMrg.formatToEconomy(value);
        }
        return "0.00";
    }

}
