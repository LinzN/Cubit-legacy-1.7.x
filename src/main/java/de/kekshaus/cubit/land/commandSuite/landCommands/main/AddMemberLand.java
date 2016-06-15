package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class AddMemberLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private boolean isAdmin;

	public AddMemberLand(Landplugin plugin, boolean isAdmin, String permNode) {
		this.plugin = plugin;
		this.isAdmin = isAdmin;
		this.permNode = permNode;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {
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
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}", "/land add [Mitspieler]"));
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
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}
		@SuppressWarnings("deprecation")
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
		UUID uuid = offlinePlayer.getUniqueId();
		if (!plugin.getLandManager().addMember(regionData, loc.getWorld(), uuid)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
			return true;
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().addMemberSuccess
				.replace("{regionID}", regionData.getRegionName()).replace("{member}", offlinePlayer.getName()));

		return true;
	}

}
