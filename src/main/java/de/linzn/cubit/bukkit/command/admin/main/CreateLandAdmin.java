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
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateLandAdmin implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;

    public CreateLandAdmin(CubitBukkitPlugin plugin, String permNode) {
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

        if (args.length < 2) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
                    "/" + cmd.getLabel() + " " + args[0].toLowerCase() + " [Player]"));
            return true;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        UUID uuid = offlinePlayer.getUniqueId();

        final Location loc = player.getLocation();
        final Chunk chunk = loc.getChunk();
        final String regionID = plugin.getRegionManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
                chunk.getZ());

        /* Check if this is a valid buyTask */
        if (plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().isAlreadyLand.replace("{regionID}", regionID));
            return true;
        }

        if (!plugin.getRegionManager().createRegion(loc, uuid, CubitType.WORLD)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
            return true;
        }

        /* Call cubit buy land event */
        CubitLand cubitLand = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());
        CubitLandBuyEvent cubitLandBuyEvent = new CubitLandBuyEvent(loc.getWorld(), cubitLand);
        this.plugin.getServer().getPluginManager().callEvent(cubitLandBuyEvent);

        if (!plugin.getBlockManager().getBlockHandler().placeLandBorder(chunk,
                CubitBukkitPlugin.inst().getYamlManager().getSettings().landBuyMaterialBorder)) {
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

        /* Task was successfully. Send BuyMessage */
        sender.sendMessage(plugin.getYamlManager().getLanguage().buySuccess.replace("{regionID}", regionID));
        return true;
    }

}
