package de.kekshaus.cubit.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class TakeOfferLand implements ILandCmd {

	private Landplugin plugin;

	private String permNode;

	public TakeOfferLand(Landplugin plugin, String permNode) {
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

		if (plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId())) {
			player.sendMessage(plugin.getYamlManager().getLanguage().takeOwnLand);
			return true;
		}
		if (!plugin.getDatabaseManager().isOffered(regionData.getRegionName(), loc.getWorld())) {
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().notOffered.replace("regionID", regionData.getRegionName()));
			return true;
		}

		OfferData offerData = plugin.getDatabaseManager().getOfferData(regionData.getRegionName(), loc.getWorld());
		if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), offerData.getValue())) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
					"" + plugin.getVaultManager().formateToEconomy(offerData.getValue())));
			return true;
		}

		if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), regionData.getOwnersUUID()[0],
				offerData.getValue())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-ECONOMY"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-ECONOMY"));
			return true;
		}
		/* Change owner and clear Memberlist */
		if (!plugin.getRegionManager().restoreDefaultSettings(regionData, loc.getWorld(), player.getUniqueId())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-UPDATEOWNER"));
			plugin.getLogger().warning(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-UPDATEOWNER"));
			return true;
		}
		/* Remove offer from Database */
		if (!plugin.getDatabaseManager().removeOfferData(regionData.getRegionName(), loc.getWorld())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-REMOVEOFFER"));
			plugin.getLogger().warning(
					plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "TAKEOFFER-REMOVEOFFER"));
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
		sender.sendMessage(
				plugin.getYamlManager().getLanguage().buySuccess.replace("{regionID}", regionData.getRegionName()));

		return true;
	}

}
