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

package de.linzn.cubit.bukkit.command.shop.main;

import de.linzn.cubit.api.events.CubitLandUpdateEvent;
import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import de.linzn.cubit.internal.dataBase.OfferData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyShop implements ICommand {

    private CubitBukkitPlugin plugin;

    private String permNode;

    public BuyShop(CubitBukkitPlugin plugin, String permNode) {
        this.plugin = plugin;
        this.permNode = permNode;
    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        if (!CubitBukkitPlugin.inst().getYamlManager().getCommandsConfig().shop_buy) {
            /* Command is disabled */
            sender.sendMessage(plugin.getYamlManager().getLanguage().disabledCommand);
            return true;
        }

        if (!(sender instanceof Player)) {
            /* This is not possible from the server console */
            sender.sendMessage(plugin.getYamlManager().getLanguage().noConsoleMode);
            return true;
        }

        /* Build and get all variables */
        Player player = (Player) sender;

        /* Permission Check */
        if (!player.hasPermission(this.permNode)) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoPermission);
            return true;
        }

        final Location loc = player.getLocation();
        final Chunk chunk = loc.getChunk();
        CubitLand cubitLand = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

        /*
         * Check if the player has permissions for this land or hat landadmin
         * permissions
         */

        if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandFound);
            return true;
        }

        if (cubitLand.getCubitType() != CubitType.SHOP) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}",
                    CubitType.SHOP.toString()));
            return true;
        }

        if (plugin.getRegionManager().hasLandPermission(cubitLand, player.getUniqueId())) {
            player.sendMessage(plugin.getYamlManager().getLanguage().takeOwnLand);
            return true;
        }
        if (!plugin.getDataAccessManager().databaseType.get_is_offer(cubitLand.getLandName(), loc.getWorld())) {
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().notOffered.replace("regionID", cubitLand.getLandName()));
            return true;
        }

        int shopLimit = CubitBukkitPlugin.inst().getYamlManager().getSettings().shopLimit;
        if (plugin.getRegionManager().hasReachLimit(player.getUniqueId(), loc.getWorld(), CubitType.SHOP, shopLimit)) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().reachLimit);
            return true;
        }
        OfferData offerData = plugin.getDataAccessManager().databaseType.get_offer(cubitLand.getLandName(),
                loc.getWorld());

        if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), offerData.getValue())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
                    "" + plugin.getVaultManager().formattingToEconomy(offerData.getValue())));
            return true;
        }

        if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, offerData.getValue())) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-ECONOMY"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-ECONOMY"));
            return true;
        }
        /* Change owner and clear Memberlist */
        if (!plugin.getRegionManager().restoreDefaultSettings(cubitLand, loc.getWorld(), player.getUniqueId())) {
            /* If this task failed! This should never happen */
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-UPDATEOWNER"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-UPDATEOWNER"));
            return true;
        }
        /* Remove offer from Database */
        if (!plugin.getDataAccessManager().databaseType.set_remove_offer(cubitLand.getLandName(), loc.getWorld())) {
            /* If this task failed! This should never happen */
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-REMOVEOFFER"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-REMOVEOFFER"));
            return true;
        }

        if (!plugin.getBlockManager().getBlockHandler().placeLandBorder(chunk,
                CubitBukkitPlugin.inst().getYamlManager().getSettings().landBuyMaterialBorder)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            return true;
        }


        if (!plugin.getBlockManager().getBlockHandler().removeBlockOnShopBuy(chunk, CubitBukkitPlugin.inst().getYamlManager().getSettings().shopMaterialCleanup)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            return true;
        }

        /* Cubit land update event*/
        CubitLandUpdateEvent cubitLandUpdateEvent = new CubitLandUpdateEvent(loc.getWorld(), cubitLand.getLandName(), cubitLand);
        this.plugin.getServer().getPluginManager().callEvent(cubitLandUpdateEvent);

        /* Task was successfully. Send BuyMessage */
        sender.sendMessage(
                plugin.getYamlManager().getLanguage().buySuccess.replace("{regionID}", cubitLand.getLandName()));

        return true;
    }

}
