package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class SellLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private boolean isAdmin;

	public SellLand(Landplugin plugin, boolean isAdmin, String permNode) {
		this.plugin = plugin;
		this.permNode = permNode;
		this.isAdmin = isAdmin;

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

		/* Check if this is a valid sellTask */
		if (!plugin.getLandManager().isLand(loc.getWorld(), chunk.getX(), chunk.getZ())) {
			sender.sendMessage(plugin.getLanguageManager().errorNoLandFound);
			return true;
		}

		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());
		final String regionID = regionData.getRegionName();

		if (!plugin.getLandManager().hasLandPermission(regionData, player.getUniqueId()) && this.isAdmin == false) {
			sender.sendMessage(plugin.getLanguageManager().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}

		double economyValue = Landplugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(),
				loc.getWorld(), false);

		if (!plugin.getVaultManager().transferMoney(null, regionData.getOwnerUUID(), economyValue)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-ECONOMY"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-ECONOMY"));
			return true;
		}

		if (!plugin.getLandManager().removeLand(regionData, loc.getWorld())) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-REGION"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-REGION"));
			return true;
		}

		if (!plugin.getBlockManager().placeLandBorder(chunk,
				Landplugin.inst().getLandConfig().landSellMaterialBorder)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-BLOCK"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-BLOCK"));
			return true;
		}

		if (!plugin.getParticleManager().sendSell(player, loc)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		/* Task was successfully. Send BuyMessage */
		sender.sendMessage(plugin.getLanguageManager().sellSuccess.replace("{regionID}", regionID));
		return true;
	}

}
