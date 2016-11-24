package de.kekshaus.cubit.commandSuite.universalCommands.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubi.api.classes.enums.LandTypes;
import de.kekshaus.cubi.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

public class AddMemberUniversal implements ICommand {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;
	private boolean isAdmin;

	public AddMemberUniversal(Landplugin plugin, String permNode, LandTypes type, boolean isAdmin) {
		this.plugin = plugin;
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

		if (args.length < 2) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
					"/" + cmd.getLabel() + " " + args[0].toLowerCase() + " [Player]"));
			return true;
		}

		final Location loc = player.getLocation();

		if (args[1].equalsIgnoreCase("-a") || args[1].equalsIgnoreCase("-all")) {
			if (args.length >= 3 && !this.isAdmin) {
				@SuppressWarnings("deprecation")
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
				UUID uuid = offlinePlayer.getUniqueId();
				if (!plugin.getRegionManager().addMemberAll(player.getUniqueId(), loc.getWorld(), uuid, type)) {
					/* If this task failed! This should never happen */
					sender.sendMessage(
							plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
					plugin.getLogger().warning(
							plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
					return true;
				}

				sender.sendMessage(plugin.getYamlManager().getLanguage().addMemberSuccess.replace("{regionID}", "ALL")
						.replace("{member}", offlinePlayer.getName()));

				return true;
			} else {
				// someting
				return true;
			}
		}

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

		if (regionData.getLandType() != type && type != LandTypes.NOTYPE) {
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type}", type.toString()));
			return true;
		}

		if (!plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId()) && this.isAdmin == false) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}

		@SuppressWarnings("deprecation")
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
		UUID uuid = offlinePlayer.getUniqueId();
		if (!plugin.getRegionManager().addMember(regionData, loc.getWorld(), uuid)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "ADD-MEMBER"));
			return true;
		}
		if (!args[1].equalsIgnoreCase("-a") && !args[1].equalsIgnoreCase("-all")) {
			if (!plugin.getParticleManager().addMember(player, loc)) {
				/* If this task failed! This should never happen */
				sender.sendMessage(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
				plugin.getLogger().warning(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
				return true;
			}
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().addMemberSuccess
				.replace("{regionID}", regionData.getRegionName()).replace("{member}", offlinePlayer.getName()));

		return true;
	}

}
