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
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SellShop implements ICommand {

    private CubitBukkitPlugin plugin;

    private String permNode;

    public SellShop(CubitBukkitPlugin plugin, String permNode) {
        this.plugin = plugin;
        this.permNode = permNode;

    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        if (!CubitBukkitPlugin.inst().getYamlManager().getCommandsConfig().shop_sell) {
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
        final String regionName = CubitBukkitPlugin.inst().getRegionManager().buildLandName(CubitType.SHOP.toString(),
                chunk.getX(), chunk.getZ());

        /*
         * Check if the player has permissions for this land or hat landadmin
         * permissions
         */

        /* Check if this is a valid sellTask */
        if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandFound);
            return true;
        }

        CubitLand cubitLand = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

        UUID economyOwner = cubitLand.getOwnersUUID()[0];

        if (cubitLand.getLandType() != CubitType.SHOP) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}",
                    CubitType.SHOP.toString()));
            return true;
        }

        if (!plugin.getRegionManager().hasLandPermission(cubitLand, player.getUniqueId())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
                    cubitLand.getRegionName()));
            return true;
        }

        if (!plugin.getRegionManager().restoreDefaultSettings(
                plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ()), loc.getWorld(),
                null)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
            return true;
        }

        if (!plugin.getBlockManager().getBlockHandler().placeLandBorder(chunk,
                CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellMaterialBorder)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
            return true;
        }

        if (!plugin.getParticleManager().sendSell(player, loc)) {
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

        double economyValue = value * CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellPercent;

        if (!plugin.getVaultManager().transferMoney(null, economyOwner, economyValue)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
            return true;
        }
        if (!plugin.getDataAccessManager().databaseType.set_create_offer(offerData)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
            return true;
        }

        /* Cubit land update event*/
        CubitLandUpdateEvent cubitLandUpdateEvent = new CubitLandUpdateEvent(loc.getWorld(), cubitLand.getRegionName(), cubitLand);
        this.plugin.getServer().getPluginManager().callEvent(cubitLandUpdateEvent);

        sender.sendMessage(plugin.getYamlManager().getLanguage().sellSuccess.replace("{regionID}", regionName));

        return true;
    }

}
