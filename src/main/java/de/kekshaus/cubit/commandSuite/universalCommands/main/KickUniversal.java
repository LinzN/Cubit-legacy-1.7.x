package de.kekshaus.cubit.commandSuite.universalCommands.main;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class KickUniversal implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;

	public KickUniversal(Landplugin plugin, String permNode, LandTypes type) {
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
		HashSet<UUID> playerMap = new HashSet<>();

		for (Entity entity : chunk.getEntities()) {
			if (entity instanceof Player) {
				if (!entity.hasPermission(plugin.getPermNodes().kickAdminBypass)) {
					if (!plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId())) {
						if (!regionData.getMembersUUID().equals(entity.getUniqueId())) {
							playerMap.add(entity.getUniqueId());
						}
					}
				}
			}
		}

		for (UUID uuid : playerMap) {
			final Player kickedPlayer = Bukkit.getPlayer(uuid);
			if (kickedPlayer != null) {
				plugin.getServer().getScheduler().runTask(plugin, new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						kickedPlayer.teleport(loc.getWorld().getSpawnLocation());
						kickedPlayer.sendMessage(plugin.getYamlManager().getLanguage().kickedInfo.replace("{regionID}",
								regionData.getRegionName()));
					}

				});

			}
		}
		sender.sendMessage(
				plugin.getYamlManager().getLanguage().kickInfo.replace("{regionID}", regionData.getRegionName()));

		return true;
	}

}
