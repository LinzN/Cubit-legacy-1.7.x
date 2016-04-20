package de.kekshaus.cookieApi.land.commandSuite.landCommands.main.flags;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class PvPLand implements ILandCmd {

	private Landplugin plugin;

	public PvPLand(Landplugin plugin) {
		this.plugin = plugin;
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
		if (!player.hasPermission(plugin.getPermNodes().pvpLand)) {
			sender.sendMessage(plugin.getLanguageManager().errorNoPermission);
			return true;
		}

		final Location loc = player.getLocation();
		final Chunk chunk = loc.getChunk();
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/* Command based switch */
		if (args.length < 2) {
		} else if (args[1].toString().equalsIgnoreCase("on")) {
			if (plugin.getLandManager().switchPvPPacket(regionData, loc.getWorld(), true, false)) {
				sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess
						.replace("{flag}", plugin.getLandManager().getPvPName()).replace("{value}", "AKTIV"));
			}
			return true;
		} else if (args[1].toString().equalsIgnoreCase("off")) {
			if (plugin.getLandManager().switchPvPPacket(regionData, loc.getWorld(), false, false)) {
				sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess
						.replace("{flag}", plugin.getLandManager().getPvPName()).replace("{value}", "INAKTIV"));
			}
			return true;
		}
		/* Switch flag-state to the other value */
		if (plugin.getLandManager().switchPvPPacket(regionData, loc.getWorld(), false, true)) {
			String status = "INAKTIV";
			if (plugin.getLandManager().getPvPState(regionData)) {
				status = "AKTIV";
			}
			sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess
					.replace("{flag}", plugin.getLandManager().getPvPName()).replace("{value}", status));
		}
		return true;
	}

}
