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
		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;
		final Location loc = player.getLocation();
		final Chunk chunk = loc.getChunk();
		if (plugin.getLandManager().createLand(loc, player, LandTypes.WORLD)) {
			if (plugin.getBlockManager().placeLandBorder(chunk, Material.TORCH)) {
				if (plugin.getParticleManager().sendCustomPaticle(player, loc, Effect.WITCH_MAGIC,
						Effect.FIREWORKS_SPARK)) {
					player.sendMessage("Land wurde erstellt!");
				}
			}
		}
		return true;
	}

}
