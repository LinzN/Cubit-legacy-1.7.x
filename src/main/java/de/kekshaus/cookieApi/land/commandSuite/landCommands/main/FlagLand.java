package de.kekshaus.cookieApi.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.IPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class FlagLand implements ILandCmd {

	private Landplugin plugin;
	private IPacket packet;

	public FlagLand(Landplugin plugin, IPacket packet) {
		this.plugin = plugin;
		this.packet = packet;
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
		if (!player.hasPermission(plugin.getPermNodes().flagLand + packet.getPacketName())) {
			sender.sendMessage(plugin.getLanguageManager().errorNoPermission);
			return true;
		}

		final Location loc = player.getLocation();
		final Chunk chunk = loc.getChunk();
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/* Command based switch */
		if (args.length < 2) {
		} else if (args[1].toString().equalsIgnoreCase("on")) {

			packet.switchState(regionData, true, true);
			String stateString = plugin.getLandManager().getStringState(packet.getState(regionData));
			sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess.replace("{flag}", packet.getPacketName())
					.replace("{value}", stateString));

			return true;
		} else if (args[1].toString().equalsIgnoreCase("off")) {

			packet.switchState(regionData, false, true);
			String stateString = plugin.getLandManager().getStringState(packet.getState(regionData));
			sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess.replace("{flag}", packet.getPacketName())
					.replace("{value}", stateString));

			return true;
		}
		/* Switch flag-state to the other value */
		packet.switchState(regionData, true);
		String stateString = plugin.getLandManager().getStringState(packet.getState(regionData));
		sender.sendMessage(plugin.getLanguageManager().flagSwitchSuccess.replace("{flag}", packet.getPacketName())
				.replace("{value}", stateString));

		return true;
	}

}
