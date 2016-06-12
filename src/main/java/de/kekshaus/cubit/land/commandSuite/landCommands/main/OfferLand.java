package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.api.sqlAPI.handler.OfferData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class OfferLand implements ILandCmd {

	private Landplugin plugin;

	private boolean isAdmin;
	private String permNode;

	public OfferLand(Landplugin plugin, boolean isAdmin, String permNode) {
		this.plugin = plugin;
		this.isAdmin = isAdmin;
		this.permNode = permNode;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			/* This is not possible from the server console */
			sender.sendMessage(plugin.getLanguageManager().noConsoleMode);
			return true;
		}

		/* Build and get all variables */
		Player player = (Player) sender;

		/* Permission Check */
		if (!player.hasPermission(this.permNode)) {
			sender.sendMessage(plugin.getLanguageManager().errorNoPermission);
			return true;
		}

		final Location loc = player.getLocation();
		final Chunk chunk = loc.getChunk();
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */
		if (!plugin.getLandManager().hasLandPermission(regionData, player.getUniqueId()) && this.isAdmin == false) {
			sender.sendMessage(plugin.getLanguageManager().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}

		if (args.length < 2) {
		} else if (args.length >= 2 && Double.parseDouble(args[1]) > 0D) {
			OfferData offerData = new OfferData(regionData.getRegionName(), loc.getWorld().toString());
			offerData.setPlayerUUID(regionData.getOwnerUUID());
			offerData.setValue(Double.parseDouble(args[1]));
			if (!plugin.getSqlManager().setOfferData(offerData)) {
				/* If this task failed! This should never happen */
				sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "OFFER-ADD"));
				plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "OFFER-ADD"));
				return true;
			}
			sender.sendMessage(plugin.getLanguageManager().offerAddSuccess
					.replace("{regionID}", regionData.getRegionName()).replace("{value}", args[1]));
		} else {

			if (plugin.getSqlManager().isOffered(regionData.getRegionName(), loc.getWorld())) {
				if (!plugin.getSqlManager().removeOfferData(regionData.getRegionName(), loc.getWorld())) {
					/* If this task failed! This should never happen */
					sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "OFFER-REMOVE"));
					plugin.getLogger()
							.warning(plugin.getLanguageManager().errorInTask.replace("{error}", "OFFER-REMOVE"));
					return true;
				}
				sender.sendMessage(plugin.getLanguageManager().offerRemoveSuccess.replace("{regionID}",
						regionData.getRegionName()));
			}
		}

		return true;
	}

}
