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

package de.linzn.cubit.bukkit.command.land.main;

import de.linzn.cubit.api.events.CubitLandUpdateEvent;
import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyupLand implements ICommand {

    private CubitBukkitPlugin plugin;

    private String permNode;

    public BuyupLand(CubitBukkitPlugin plugin, String permNode) {
        this.plugin = plugin;
        this.permNode = permNode;

    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        if (!CubitBukkitPlugin.inst().getYamlManager().getCommandsConfig().land_buyup) {
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

        if (cubitLand.getCubitType() != CubitType.WORLD) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}",
                    CubitType.WORLD.toString()));
            return true;
        }

        if (plugin.getRegionManager().hasLandPermission(cubitLand, player.getUniqueId())) {
            player.sendMessage(plugin.getYamlManager().getLanguage().takeOwnLand);
            return true;
        }
        boolean isMember = false;
        if (cubitLand.getMembersUUID().equals(player.getUniqueId())) {
            isMember = true;
        }

        if (!plugin.getRegionManager().isToLongOffline(cubitLand.getOwnersUUID()[0], isMember)) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().notToLongOffline);
            return true;
        }

        if (!plugin.getYamlManager().getSettings().freeCubitLandWorld.contains(loc.getWorld().getName())) {
            double economyValue = plugin.getVaultManager().calculateLandCost(player.getUniqueId(), loc.getWorld(), true);
            if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), economyValue)) {
                sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
                        "" + plugin.getVaultManager().formattingToEconomy(economyValue)));
                return true;
            }

            if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, economyValue)) {
                /* If this task failed! This should never happen */
                sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
                plugin.getLogger()
                        .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
                return true;
            }
        }

        /* Change owner and clear Memberlist */
        if (!plugin.getRegionManager().restoreDefaultSettings(cubitLand, loc.getWorld(), player.getUniqueId())) {
            /* If this task failed! This should never happen */
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "BUYUP-UPDATEOWNER"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "BUYUP-UPDATEOWNER"));
            return true;
        }
        /* Remove offer from Database */
        if (!plugin.getDataAccessManager().databaseType.set_remove_offer(cubitLand.getLandName(), loc.getWorld())) {
            /* If this task failed! This should never happen */
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "BUYUP-REMOVEOFFER"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "BUYUP-REMOVEOFFER"));
            return true;
        }

        /* Cubit land update event*/
        CubitLandUpdateEvent cubitLandUpdateEvent = new CubitLandUpdateEvent(loc.getWorld(), cubitLand.getLandName(), cubitLand);
        this.plugin.getServer().getPluginManager().callEvent(cubitLandUpdateEvent);

        if (!plugin.getParticleManager().sendBuy(player, loc)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            return true;
        }
        /* Task was successfully. Send BuyMessage */
        sender.sendMessage(
                plugin.getYamlManager().getLanguage().buySuccess.replace("{regionID}", cubitLand.getLandName()));

        return true;
    }

}
