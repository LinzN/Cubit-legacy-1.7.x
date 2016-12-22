package de.kekshaus.cubit.commandSuite.universalCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

public class RestoreUniversal implements ICommand {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;

	public RestoreUniversal(Landplugin plugin, String permNode, LandTypes type) {
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
		
		if (args.length < 2) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
					"/" + cmd.getLabel() + " " + args[0].toLowerCase() + " [SnapshotName]"));
			return true;
		}
		
		
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
		
		
		String snapshotName = args[1].toLowerCase();
		
		if (!plugin.getBlockManager().getSnapshotHandler().hasSnapshot(player.getUniqueId(), snapshotName)){
			sender.sendMessage(plugin.getYamlManager().getLanguage().noSnapshot);
			return true;
		}
		
		double economyValue = plugin.getYamlManager().getSettings().landRestoreSnapshot;
		
		if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), economyValue)) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
					"" + plugin.getVaultManager().formateToEconomy(economyValue)));
			return true;
		}

		if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, economyValue)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-ECONOMY"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-ECONOMY"));
			return true;
		}

		
		if (!this.plugin.getBlockManager().getSnapshotHandler().restoreSnapshot(player.getUniqueId(), chunk, snapshotName , true)){
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-SNAPSHOT"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-SNAPSHOT"));
			return true;
		}
		
		sender.sendMessage(plugin.getYamlManager().getLanguage().restoredSnapshot.replace("{snapshotName}", snapshotName));
		
		return true;
	
	}

}
