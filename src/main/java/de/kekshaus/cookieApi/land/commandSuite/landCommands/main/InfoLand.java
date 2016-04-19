package de.kekshaus.cookieApi.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.guild.objects.Guild;
import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class InfoLand implements ILandCmd {

	private Landplugin plugin;

	public InfoLand(Landplugin plugin) {
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
		final Location loc = player.getLocation();
		final Chunk chunk = loc.getChunk();
		final String regionID = plugin.getLandManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
				chunk.getZ());
		double testValue = 300D;

		sender.sendMessage(plugin.getLanguageManager().landHeader);
		/* Check if this is a valid infoTask */
		if (!plugin.getLandManager().isLand(loc.getWorld(), chunk.getX(), chunk.getZ())) {
			sender.sendMessage(plugin.getLanguageManager().landInfoA1.replace("{cost}", "" + testValue)
					.replace("{regionID}", regionID));
			return true;
		}

		if (!plugin.getParticleManager().sendCustomPaticle(player, loc, Effect.HAPPY_VILLAGER,
				Effect.FIREWORKS_SPARK)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		/* Get RegionData Info */
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());
		Guild guild = regionData.getGuild();

		sender.sendMessage(
				plugin.getLanguageManager().landInfoE1.replace("{regionID}", regionData.praseWGRegion().getId()));
		if (guild != null) {
			sender.sendMessage(plugin.getLanguageManager().landInfoE1A1.replace("{regionID}", guild.getGuildName()));
		}
		sender.sendMessage(plugin.getLanguageManager().landInfoE2.replace("{owner}", regionData.getOwnerName()));
		sender.sendMessage(plugin.getLanguageManager().landInfoE3);
		sender.sendMessage(plugin.getLanguageManager().landInfoE4.replace("{time}", "Noch nix da"));

		return true;
	}

}
