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

public class ResetUniversal implements ICommand {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;

	public ResetUniversal(Landplugin plugin, String permNode, LandTypes type) {
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
		
		
		if (!this.plugin.getYamlManager().getSettings().landUseSnapshots){
			sender.sendMessage(plugin.getYamlManager().getLanguage().disabledSnapshots);
			return true;
		}
		
		if (!this.plugin.getBlockManager().getSnapshotHandler().hasValidAdapter()){
			sender.sendMessage(plugin.getYamlManager().getLanguage().noValidWEAdapter);
			return true;
		}

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
		
		if (args.length < 1) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
					"/" + cmd.getLabel() + " " + args[0].toLowerCase()));
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
		

		double economyValue = plugin.getYamlManager().getSettings().landResetSnapshotPrice;
		
		if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), economyValue)) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
					"" + plugin.getVaultManager().formateToEconomy(economyValue)));
			return true;
		}

		if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, economyValue)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESET-ECONOMY"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESET-ECONOMY"));
			return true;
		}

		
		if (!this.plugin.getBlockManager().getSnapshotHandler().resetChunk(chunk)){
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESET-SNAPSHOT"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESET-SNAPSHOT"));
			return true;
		}
		
		sender.sendMessage(plugin.getYamlManager().getLanguage().resetSnapshot.replace("{regionID}", regionData.getRegionName()));
		
		return true;
	
	}

}
