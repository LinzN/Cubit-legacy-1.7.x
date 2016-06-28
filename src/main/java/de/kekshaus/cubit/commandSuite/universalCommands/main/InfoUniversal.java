package de.kekshaus.cubit.commandSuite.universalCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;
import de.nlinz.xeonSuite.guild.objects.Guild;

public class InfoUniversal implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;

	public InfoUniversal(Landplugin plugin, String permNode, LandTypes type) {
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
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/* Send header */
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHeader);

		/* Check Type of this Region */
		switch (regionData.getLandType()) {
		case SERVER:
			serverInfo(player, regionData);
			break;
		case SHOP:
			shopInfo(player, regionData);
			break;
		case WORLD:
			landInfo(player, regionData);
			break;
		default:
			noInfo(player, regionData, loc, chunk);
			break;
		}

		/* After info command send Particle */
		if (!plugin.getParticleManager().sendInfo(player, loc)) {
			/* If this task failed! This should never happen */
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger()
					.warning(plugin.getYamlManager().getLanguage().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		return true;
	}

	private void landInfo(Player player, RegionData regionData) {
		/* Get RegionData Info */
		String formatedTime = plugin.getDatabaseManager().getLastLoginFormated(regionData.getOwnerUUID());
		String minBorder = regionData.getMinPoint();
		String maxBorder = regionData.getMaxPoint();
		String statusLock = plugin.getLandManager().lockPacket.getStateColor(regionData)
				+ plugin.getLandManager().lockPacket.getPacketName();
		String statusFire = plugin.getLandManager().firePacket.getStateColor(regionData)
				+ plugin.getLandManager().firePacket.getPacketName();
		String statusPvP = plugin.getLandManager().pvpPacket.getStateColor(regionData)
				+ plugin.getLandManager().pvpPacket.getPacketName();
		String statusTNT = plugin.getLandManager().tntPacket.getStateColor(regionData)
				+ plugin.getLandManager().tntPacket.getPacketName();
		String statusMonster = plugin.getLandManager().monsterPacket.getStateColor(regionData)
				+ plugin.getLandManager().monsterPacket.getPacketName();

		String statusPotion = plugin.getLandManager().potionPacket.getStateColor(regionData)
				+ plugin.getLandManager().potionPacket.getPacketName();

		player.sendMessage(
				plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));

		if (this.plugin.isGuildLoaded()) {
			Guild guild = regionData.getGuild();
			if (guild != null) {
				player.sendMessage(
						plugin.getYamlManager().getLanguage().landInfoE1A1.replace("{guild}", guild.getGuildName()));
			}
		}
		player.sendMessage(
				plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}", regionData.getOwnerName()));
		if (regionData.getMembersName().size() != 0) {
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}",
					regionData.getMembersName().toString()));
		}
		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder).replace("{max}",
				maxBorder));
		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
				.replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
				.replace("{tnt}", statusTNT).replace("{potion}", statusPotion));

		if (plugin.getDatabaseManager().isOffered(regionData.getRegionName(), regionData.getWorld())) {
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA2.replace("{value}", "" + plugin
					.getDatabaseManager().getOfferData(regionData.getRegionName(), regionData.getWorld()).getValue()));
		}
		return;

	}

	private void shopInfo(Player player, RegionData regionData) {
		/* Get RegionData Info */
		if (regionData.getOwnerUUID() != null) {
			String formatedTime = plugin.getDatabaseManager().getLastLoginFormated(regionData.getOwnerUUID());
			String minBorder = regionData.getMinPoint();
			String maxBorder = regionData.getMaxPoint();
			String statusLock = plugin.getLandManager().lockPacket.getStateColor(regionData)
					+ plugin.getLandManager().lockPacket.getPacketName();
			String statusFire = plugin.getLandManager().firePacket.getStateColor(regionData)
					+ plugin.getLandManager().firePacket.getPacketName();
			String statusPvP = plugin.getLandManager().pvpPacket.getStateColor(regionData)
					+ plugin.getLandManager().pvpPacket.getPacketName();
			String statusTNT = plugin.getLandManager().tntPacket.getStateColor(regionData)
					+ plugin.getLandManager().tntPacket.getPacketName();
			String statusMonster = plugin.getLandManager().monsterPacket.getStateColor(regionData)
					+ plugin.getLandManager().monsterPacket.getPacketName();

			String statusPotion = plugin.getLandManager().potionPacket.getStateColor(regionData)
					+ plugin.getLandManager().potionPacket.getPacketName();

			player.sendMessage(
					plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));

			if (this.plugin.isGuildLoaded()) {
				Guild guild = regionData.getGuild();
				if (guild != null) {
					player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE1A1.replace("{guild}",
							guild.getGuildName()));
				}
			}
			player.sendMessage(
					plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}", regionData.getOwnerName()));
			if (regionData.getMembersName().size() != 0) {
				player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}",
						regionData.getMembersName().toString()));
			}
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder)
					.replace("{max}", maxBorder));
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
					.replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
					.replace("{tnt}", statusTNT).replace("{potion}", statusPotion));
			if (plugin.getDatabaseManager().isOffered(regionData.getRegionName(), regionData.getWorld())) {
				player.sendMessage(
						plugin.getYamlManager().getLanguage().landInfoA2
								.replace("{value}",
										"" + plugin.getVaultManager()
												.formateToEconomy(plugin.getDatabaseManager()
														.getOfferData(regionData.getRegionName(), regionData.getWorld())
														.getValue())));
			}

		} else {

			String value = plugin.getVaultManager().formateToEconomy(plugin.getDatabaseManager()
					.getOfferData(regionData.getRegionName(), regionData.getWorld()).getValue());

			player.sendMessage(
					plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));
			player.sendMessage(plugin.getYamlManager().getLanguage().isFreeAndBuyable
					.replace("{regionID}", regionData.getRegionName()).replace("{price}", value));

		}
	}

	private void serverInfo(Player player, RegionData regionData) {
		String minBorder = regionData.getMinPoint();
		String maxBorder = regionData.getMaxPoint();

		String statusLock = plugin.getLandManager().lockPacket.getStateColor(regionData)
				+ plugin.getLandManager().lockPacket.getPacketName();
		String statusFire = plugin.getLandManager().firePacket.getStateColor(regionData)
				+ plugin.getLandManager().firePacket.getPacketName();
		String statusPvP = plugin.getLandManager().pvpPacket.getStateColor(regionData)
				+ plugin.getLandManager().pvpPacket.getPacketName();
		String statusTNT = plugin.getLandManager().tntPacket.getStateColor(regionData)
				+ plugin.getLandManager().tntPacket.getPacketName();
		String statusMonster = plugin.getLandManager().monsterPacket.getStateColor(regionData)
				+ plugin.getLandManager().monsterPacket.getPacketName();

		String statusPotion = plugin.getLandManager().potionPacket.getStateColor(regionData)
				+ plugin.getLandManager().potionPacket.getPacketName();

		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}", "Server"));
		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder).replace("{max}",
				maxBorder));
		player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
				.replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
				.replace("{tnt}", statusTNT).replace("{potion}", statusPotion));
		return;
	}

	private void noInfo(Player player, RegionData regionData, Location loc, Chunk chunk) {
		if (this.type != LandTypes.SHOP) {
			/* Buy-able region */
			double economyValue = Landplugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(),
					loc.getWorld(), true);
			final String regionID = plugin.getLandManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
					chunk.getZ());
			player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA1
					.replace("{cost}", "" + plugin.getVaultManager().formateToEconomy(economyValue))
					.replace("{regionID}", regionID));
			return;
		} else {
			player.sendMessage(
					plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type} ", type.toString()));
			return;
		}
	}

}
