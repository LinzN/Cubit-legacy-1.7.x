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

package de.linzn.cubit.bukkit.command.universal.blockedit;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditMovedownUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;
    private LandTypes type;

    public EditMovedownUniversal(CubitBukkitPlugin plugin, String permNode, LandTypes type) {
        this.plugin = plugin;
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

        if (!this.plugin.getYamlManager().getSettings().landUseSnapshots) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().disabledSnapshots);
            return true;
        }

        if (!this.plugin.getBlockManager().getSnapshotHandler().hasValidAdapter()) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().noValidWEAdapter);
            return true;
        }

		/* Permission Check */
        if (!player.hasPermission(this.permNode)) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoPermission);
            return true;
        }

        final Location loc = player.getLocation();
        final Chunk chunk = loc.getChunk();
        final RegionData regionData = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(),
                chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */
        if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandFound);
            return true;
        }

        if (regionData.getLandType() != type && type != LandTypes.NOTYPE) {
            sender.sendMessage(
                    plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}", type.toString()));
            return true;
        }

        if (!plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId())) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
                    regionData.getRegionName()));
            return true;
        }

        double economyValue = plugin.getYamlManager().getSettings().landSaveSnapshotPrice;

        if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), economyValue)) {
            sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
                    "" + plugin.getVaultManager().formateToEconomy(economyValue)));
            return true;
        }

        if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, economyValue)) {
            /* If this task failed! This should never happen */
            sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SAVE-ECONOMY"));
            plugin.getLogger()
                    .warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SAVE-ECONOMY"));
            return true;
        }

        // Code
        return true;
    }

}
