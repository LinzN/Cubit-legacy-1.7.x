package de.linzn.cubit.bukkit.command.admin.main;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dataAccessMgr.OfferData;
import de.linzn.cubit.internal.regionMgr.LandTypes;

public class CreateShopAdmin implements ICommand {

	private CubitBukkitPlugin plugin;

	private String permNode;

	public CreateShopAdmin(CubitBukkitPlugin plugin, String permNode) {
		this.plugin = plugin;
		this.permNode = permNode;

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
		final String regionName = CubitBukkitPlugin.inst().getRegionManager().buildLandName(LandTypes.SHOP.toString(),
				chunk.getX(), chunk.getZ());

		/*
		 * Check if the player has permissions for this land or hat landadmin
		 * permissions
		 */

		if (!plugin.getRegionManager().isValidRegion(loc.getWorld(), chunk.getX(), chunk.getZ())) {
			if (!plugin.getRegionManager().createRegion(loc, player.getUniqueId(), LandTypes.SHOP)) {
				/* If this task failed! This should never happen */
				sender.sendMessage(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
				plugin.getLogger()
						.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-REGION"));
				return true;
			}
		} else {
			if (!plugin.getRegionManager().restoreDefaultSettings(
					plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ()),
					loc.getWorld(), null)) {
				/* If this task failed! This should never happen */
				sender.sendMessage(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
				plugin.getLogger().warning(
						plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "RESTORE-REGION"));
				return true;
			}
		}

		if (!plugin.getBlockManager().getBlockHandler().placeLandBorder(chunk,
				CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellMaterialBorder)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-BLOCK"));
			return true;
		}

		if (!plugin.getParticleManager().sendBuy(player, loc)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		double value = CubitBukkitPlugin.inst().getYamlManager().getSettings().shopBasePrice;
		if (args.length >= 2) {
			if (!NumberUtils.isNumber(args[1])) {
				sender.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
				return true;
			}
			value = Double.parseDouble(args[1]);
		}
		OfferData offerData = new OfferData(regionName, loc.getWorld());
		offerData.setValue(value);
		offerData.setPlayerUUID(player.getUniqueId());
		if (!plugin.getDataAccessManager().databaseType.set_create_offer(offerData)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "OFFER-ADD"));
			return true;
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().createShopLand.replace("{regionID}", regionName));

		return true;
	}

}
