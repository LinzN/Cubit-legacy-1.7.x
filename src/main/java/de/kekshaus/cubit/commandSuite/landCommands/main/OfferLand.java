package de.kekshaus.cubit.commandSuite.landCommands.main;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

public class OfferLand implements ICommand {

	private Landplugin plugin;

	private boolean isAdmin;
	private String permNode;

	public OfferLand(Landplugin plugin, String permNode, boolean isAdmin) {
		this.plugin = plugin;
		this.isAdmin = isAdmin;
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
		RegionData regionData = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */

		if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandFound);
			return true;
		}

		if (regionData.getLandType() != LandTypes.WORLD) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}",
					LandTypes.WORLD.toString()));
			return true;
		}

		if (!plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId()) && this.isAdmin == false) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}

		if (args.length >= 2) {
			if (!NumberUtils.isNumber(args[1])) {
				sender.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
				return true;
			}
			double value = Double.parseDouble(args[1]);
			if (value <= 0) {
				if (plugin.getDatabaseManager().isOffered(regionData.getRegionName(), loc.getWorld())) {
					if (!plugin.getDatabaseManager().removeOfferData(regionData.getRegionName(), loc.getWorld())) {
						/* If this task failed! This should never happen */
						sender.sendMessage(
								plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-REMOVE"));
						plugin.getLogger().warning(
								plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-REMOVE"));
						return true;
					}
					sender.sendMessage(plugin.getYamlManager().getLanguage().offerRemoveSuccess.replace("{regionID}",
							regionData.getRegionName()));
				}
			} else {
				OfferData offerData = new OfferData(regionData.getRegionName(), loc.getWorld());
				offerData.setPlayerUUID(regionData.getOwnersUUID()[0]);
				offerData.setValue(value);
				if (!plugin.getDatabaseManager().setOfferData(offerData)) {
					/* If this task failed! This should never happen */
					sender.sendMessage(
							plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
					plugin.getLogger()
							.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
					return true;
				}
				sender.sendMessage(plugin.getYamlManager().getLanguage().offerAddSuccess
						.replace("{regionID}", regionData.getRegionName())
						.replace("{value}", plugin.getVaultManager().formateToEconomy(value)));
			}
		}

		return true;
	}

}
