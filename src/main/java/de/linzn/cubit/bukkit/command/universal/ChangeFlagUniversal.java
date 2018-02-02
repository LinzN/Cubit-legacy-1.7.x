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

package de.linzn.cubit.bukkit.command.universal;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.ICubitPacket;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeFlagUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private ICubitPacket packet;
    private boolean isAdmin;
    private String permNode;
    private CubitType type;

    public ChangeFlagUniversal(CubitBukkitPlugin plugin, ICubitPacket packet, String permNode, CubitType type,
                               boolean isAdmin) {
        this.plugin = plugin;
        this.packet = packet;
        this.isAdmin = isAdmin;
        this.permNode = permNode;
        this.type = type;

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
        CubitLand cubitLand = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

        /*
         * Check if the player has permissions for this land or hat landadmin
         * permissions
         */
        if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandFound);
            return true;
        }

        if (cubitLand.getCubitType() != type && type != CubitType.NOTYPE) {
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}", type.toString()));
            return true;
        }

        if (!plugin.getRegionManager().hasLandPermission(cubitLand, player.getUniqueId()) && !this.isAdmin) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
                    cubitLand.getLandName()));
            return true;
        }

        /* Command based switch */
        if (args.length < 2) {
        } else if (args[1].equalsIgnoreCase("on")) {

            packet.switchState(cubitLand, true, true);
            String stateString = plugin.getRegionManager().getStringState(packet.getState(cubitLand));
            sender.sendMessage(plugin.getYamlManager().getLanguage().flagSwitchSuccess
                    .replace("{flag}", packet.getPacketName()).replace("{value}", stateString));

            return true;
        } else if (args[1].equalsIgnoreCase("off")) {

            packet.switchState(cubitLand, false, true);
            String stateString = plugin.getRegionManager().getStringState(packet.getState(cubitLand));
            sender.sendMessage(plugin.getYamlManager().getLanguage().flagSwitchSuccess
                    .replace("{flag}", packet.getPacketName()).replace("{value}", stateString));

            return true;
        }
        /* Switch flag-state to the opposite value */
        packet.switchState(cubitLand, true);
        String stateString = plugin.getRegionManager().getStringState(packet.getState(cubitLand));

        if (!plugin.getParticleManager().changeFlag(player, loc)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
            return true;
        }

        sender.sendMessage(plugin.getYamlManager().getLanguage().flagSwitchSuccess
                .replace("{flag}", packet.getPacketName()).replace("{value}", stateString));

        return true;
    }

}
