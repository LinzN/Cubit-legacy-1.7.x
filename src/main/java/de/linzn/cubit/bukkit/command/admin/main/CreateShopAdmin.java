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

package de.linzn.cubit.bukkit.command.admin.main;

import de.linzn.cubit.api.events.CubitLandBuyEvent;
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

public class CreateShopAdmin implements ICommand {

    private CubitBukkitPlugin plugin;

    private String permNode;

    public CreateShopAdmin(CubitBukkitPlugin plugin, String permNode) {
        this.plugin = plugin;
        this.permNode = permNode;

    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
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
        final String regionName = CubitBukkitPlugin.inst().getRegionManager().buildLandName(CubitType.SHOP.toString(),
                chunk.getX(), chunk.getZ());

        /*
         * Check if the player has permissions for this land or hat landadmin
         * permissions
         */

        if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            if (!plugin.getRegionManager().createRegion(loc, player.getUniqueId(), CubitType.SHOP)) {
                /* If this task failed! This should never happen */
                sender.sendMessage(
                        plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
                plugin.getLogger()
                        .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
                return true;
            }
        } else {
            if (!plugin.getRegionManager().restoreDefaultSettings(
                    plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ()),
                    loc.getWorld(), null)) {
                /* If this task failed! This should never happen */
                sender.sendMessage(
                        plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
                plugin.getLogger().warning(
                        plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
                return true;
            }
        }

        if (!plugin.getBlockManager().getBlockHandler().placeLandBorder(chunk,
                CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellMaterialBorder)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            return true;
        }

        if (!plugin.getParticleManager().sendBuy(player, loc)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            return true;
        }

        double value = CubitBukkitPlugin.inst().getYamlManager().getSettings().shopBasePrice;
        if (args.length >= 2) {
            if (!NumberUtils.isNumber(args[1])) {
                sender.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
                return true;
            }
            value = Double.parseDouble(args[1]);
        }
        OfferData offerData = new OfferData(regionName, loc.getWorld());
        offerData.setValue(value);
        offerData.setPlayerUUID(player.getUniqueId());
        if (!plugin.getDataAccessManager().databaseType.set_create_offer(offerData)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
            return true;
        }

        /* Call cubit buy land event */
        CubitLand cubitLand = new CubitLand(loc.getWorld());
        cubitLand.setWGRegion(this.plugin.getWorldGuardPlugin().getRegionManager(loc.getWorld()).getRegion(regionName));
        CubitLandBuyEvent cubitLandBuyEvent = new CubitLandBuyEvent(loc.getWorld(), regionName, cubitLand);
        this.plugin.getServer().getPluginManager().callEvent(cubitLandBuyEvent);

        sender.sendMessage(plugin.getYamlManager().getLanguage().createShopLand.replace("{regionID}", regionName));

        return true;
    }

}
