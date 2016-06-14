package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class KickLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;

	public KickLand(Landplugin plugin, String permNode) {
		this.plugin = plugin;
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
		final RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(),
				chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */
		if (!plugin.getLandManager().hasLandPermission(regionData, player.getUniqueId())) {
			sender.sendMessage(plugin.getLanguageManager().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}
		HashSet<UUID> playerMap = new HashSet<UUID>();

		for (Entity entity : chunk.getEntities()) {
			if (entity instanceof Player) {
				if (!entity.hasPermission(plugin.getPermNodes().kickAdminBypass)) {
					if (!plugin.getLandManager().hasLandPermission(regionData, player.getUniqueId())) {
						if (!regionData.getMembersUUID().contains(entity.getUniqueId())) {
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
						kickedPlayer.sendMessage(plugin.getLanguageManager().kickedInfo.replace("{regionID}",
								regionData.getRegionName()));
					}

				});

			}
		}
		sender.sendMessage(plugin.getLanguageManager().kickInfo.replace("{regionID}", regionData.getRegionName()));

		return true;
	}

}
