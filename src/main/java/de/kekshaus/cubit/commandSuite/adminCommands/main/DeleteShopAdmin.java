package de.kekshaus.cubit.commandSuite.adminCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

public class DeleteShopAdmin implements ICommand {

	private Landplugin plugin;

	private String permNode;

	public DeleteShopAdmin(Landplugin plugin, String permNode) {
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
		final String regionName = Landplugin.inst().getRegionManager().buildLandName(LandTypes.SHOP.toString(),
				chunk.getX(), chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */
		RegionData regionData = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		if (regionData.getLandType() != LandTypes.SHOP) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}",
					LandTypes.SHOP.toString()));
			return true;
		}
		
		
		/* Remove offer from Database */
		if (!plugin.getDatabaseManager().removeOfferData(regionData.getRegionName(), loc.getWorld())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-REMOVEOFFER"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SHOP-REMOVEOFFER"));
			return true;
		}
		
		if (!plugin.getRegionManager().removeLand(regionData, loc.getWorld())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "DELETE-REGION"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "DELETE-REGION"));
			return true;
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().deleteShopLand.replace("{regionID}", regionName));

		return true;
	}

}
