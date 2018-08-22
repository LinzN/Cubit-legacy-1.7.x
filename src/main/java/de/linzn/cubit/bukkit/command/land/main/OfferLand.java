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

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import de.linzn.cubit.internal.dataBase.OfferData;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OfferLand implements ICommand {

    private CubitBukkitPlugin plugin;

    private boolean isAdmin;
    private String permNode;

    public OfferLand(CubitBukkitPlugin plugin, String permNode, boolean isAdmin) {
        this.plugin = plugin;
        this.isAdmin = isAdmin;
        this.permNode = permNode;

    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        if (!CubitBukkitPlugin.inst().getYamlManager().getCommandsConfig().land_offer) {
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

        if (!plugin.getRegionManager().hasLandPermission(cubitLand, player.getUniqueId()) && !this.isAdmin) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
                    cubitLand.getLandName()));
            return true;
        }

        if (args.length >= 2) {
            if (!NumberUtils.isNumber(args[1])) {
                sender.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
                return true;
            }
            double value = Double.parseDouble(args[1]);
            if (value <= 0) {
                if (plugin.getDataAccessManager().databaseType.get_is_offer(cubitLand.getLandName(),
                        loc.getWorld())) {
                    if (!plugin.getDataAccessManager().databaseType.set_remove_offer(cubitLand.getLandName(),
                            loc.getWorld())) {
                        /* If this task failed! This should never happen */
                        sender.sendMessage(
                                plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-REMOVE"));
                        plugin.getLogger().warning(
                                plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-REMOVE"));
                        return true;
                    }
                    sender.sendMessage(plugin.getYamlManager().getLanguage().offerRemoveSuccess.replace("{regionID}",
                            cubitLand.getLandName()));
                }
            } else {
                OfferData offerData = new OfferData(cubitLand.getLandName(), loc.getWorld());
                offerData.setPlayerUUID(cubitLand.getOwnersUUID()[0]);
                offerData.setValue(value);
                if (!plugin.getDataAccessManager().databaseType.set_create_offer(offerData)) {
                    /* If this task failed! This should never happen */
                    sender.sendMessage(
                            plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
                    plugin.getLogger()
                            .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
                    return true;
                }
                sender.sendMessage(plugin.getYamlManager().getLanguage().offerAddSuccess
                        .replace("{regionID}", cubitLand.getLandName())
                        .replace("{value}", plugin.getVaultManager().formattingToEconomy(value)));
            }
        }

        return true;
    }

}
