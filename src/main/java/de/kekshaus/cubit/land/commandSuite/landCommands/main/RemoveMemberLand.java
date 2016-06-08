package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class RemoveMemberLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private boolean isAdmin;

	public RemoveMemberLand(Landplugin plugin, boolean isAdmin, String permNode) {
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

		if (args.length < 2) {
			sender.sendMessage(" Debug: Wrong arguments");
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
					regionData.praseWGRegion().getId()));
			return true;
		}
		@SuppressWarnings("deprecation")
		UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
		if (!plugin.getLandManager().removeMember(regionData, loc.getWorld(), uuid)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "REMOVE-MEMBER"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "REMOVE-MEMBER"));
			return true;
		}

		sender.sendMessage("Success!");

		return true;
	}

}
