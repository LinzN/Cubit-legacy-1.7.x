package de.linzn.cubit.bukkit.command.universal;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import de.linzn.cubit.internal.regionMgr.region.RegionData;

public class ChangeBiomeUniversal implements ICommand {

	private CubitBukkitPlugin plugin;
	private String permNode;
	private LandTypes type;
	private boolean isAdmin;

	public ChangeBiomeUniversal(CubitBukkitPlugin plugin, String permNode, LandTypes type, boolean isAdmin) {
		this.plugin = plugin;
		this.isAdmin = isAdmin;

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

		if (args.length < 2) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
					"/" + cmd.getLabel() + " " + args[0].toLowerCase() + " [Biome]"));
			return true;
		}

		final Location loc = player.getLocation();

		if (!checkBiome(args[1].toUpperCase())) {
			sender.sendMessage(
					plugin.getYamlManager().getLanguage().notABiome.replace("{biome}", args[1].toUpperCase()));
			return true;
		}

		Biome biome = Biome.valueOf(args[1].toUpperCase());
		final Chunk chunk = loc.getChunk();
		RegionData regionData = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

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

		if (!plugin.getRegionManager().hasLandPermission(regionData, player.getUniqueId()) && this.isAdmin == false) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoLandPermission.replace("{regionID}",
					regionData.getRegionName()));
			return true;
		}

		if (!this.isAdmin) {
			double economyValue = plugin.getYamlManager().getSettings().landChangeBiomePrice;
			if (!plugin.getVaultManager().hasEnougToBuy(player.getUniqueId(), economyValue)) {
				sender.sendMessage(plugin.getYamlManager().getLanguage().notEnoughMoney.replace("{cost}",
						"" + plugin.getVaultManager().formateToEconomy(economyValue)));
				return true;
			}

			if (!plugin.getVaultManager().transferMoney(player.getUniqueId(), null, economyValue)) {
				/* If this task failed! This should never happen */
				sender.sendMessage(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
				plugin.getLogger().warning(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-ECONOMY"));
				return true;
			}
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().startBiomeChange.replace("{regionID}",
				regionData.getRegionName()));
		if (!plugin.getBlockManager().getBiomeHandler().changeBiomeChunk(chunk, biome)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SET-BIOME"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "SET-BIOME"));
			return true;
		}

		if (!plugin.getParticleManager().changeBiome(player, loc)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().changedBiome
				.replace("{regionID}", regionData.getRegionName()).replace("{biome}", biome.name().toUpperCase()));

		return true;
	}

	private boolean checkBiome(String biomename) {
		for (Biome biome : Biome.values()) {
			if (biome.name().equals(biomename)) {
				return true;
			}
		}
		return false;
	}

}
