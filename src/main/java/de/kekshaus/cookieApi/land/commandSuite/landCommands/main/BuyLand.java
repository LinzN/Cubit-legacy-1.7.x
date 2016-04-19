package de.kekshaus.cookieApi.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.region.LandTypes;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class BuyLand implements ILandCmd {

	private Landplugin plugin;

	public BuyLand(Landplugin plugin) {
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

		/* Check if this is a valid buyTask */
		if (plugin.getLandManager().isLand(loc.getWorld(), chunk.getX(), chunk.getZ())) {
			sender.sendMessage(plugin.getLanguageManager().buyIsAlreadyLand.replace("{regionID}", regionID));
			return true;
		}

		if (!plugin.getLandManager().createLand(loc, player, LandTypes.WORLD)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-REGION"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-REGION"));
			return true;
		}

		if (!plugin.getBlockManager().placeLandBorder(chunk, Material.TORCH)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-BLOCK"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-BLOCK"));
			return true;
		}

		if (!plugin.getParticleManager().sendCustomPaticle(player, loc, Effect.WITCH_MAGIC, Effect.FIREWORKS_SPARK)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		/* Task was successfully. Send BuyMessage */
		sender.sendMessage(plugin.getLanguageManager().buySuccess.replace("{regionID}", regionID));
		return true;
	}

}
