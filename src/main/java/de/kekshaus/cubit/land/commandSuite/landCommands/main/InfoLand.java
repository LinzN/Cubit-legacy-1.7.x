package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.land.api.sqlAPI.handler.OfferData;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;
import de.nlinz.xeonSuite.guild.objects.Guild;

public class InfoLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;

	public InfoLand(Landplugin plugin, String permNode) {
		this.plugin = plugin;
		this.permNode = permNode;
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
		RegionData regionData = plugin.getLandManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

		/* Send header */
		sender.sendMessage(plugin.getLanguageManager().landHeader);

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
			sender.sendMessage(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			plugin.getLogger().warning(plugin.getLanguageManager().errorInTask.replace("{error}", "CREATE-PARTICLE"));
			return true;
		}

		return true;
	}

	private void landInfo(Player player, RegionData regionData) {
		/* Get RegionData Info */
		String formatedTime = plugin.getSqlManager().getLastLoginFormated(regionData.getOwnerUUID());
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

		player.sendMessage(
				plugin.getLanguageManager().landInfoE1.replace("{regionID}", regionData.praseWGRegion().getId()));

		if (this.plugin.isGuildLoaded()) {
			Guild guild = regionData.getGuild();
			if (guild != null) {
				player.sendMessage(plugin.getLanguageManager().landInfoE1A1.replace("{guild}", guild.getGuildName()));
			}
		}
		player.sendMessage(plugin.getLanguageManager().landInfoE2.replace("{owner}", regionData.getOwnerName()));
		if (regionData.getMembersName().size() != 0) {
			player.sendMessage(plugin.getLanguageManager().landInfoE3.replace("{members}",
					regionData.getMembersName().toString()));
		}
		player.sendMessage(
				plugin.getLanguageManager().landInfoE4.replace("{min}", minBorder).replace("{max}", maxBorder));
		player.sendMessage(plugin.getLanguageManager().landInfoE5.replace("{time}", formatedTime));
		player.sendMessage(
				plugin.getLanguageManager().landInfoE6.replace("{lock}", statusLock).replace("{monster}", statusMonster)
						.replace("{fire}", statusFire).replace("{pvp}", statusPvP).replace("{tnt}", statusTNT));

		OfferData tempData = new OfferData(regionData.praseWGRegion().getId(), regionData.getWorld().getName());
		if (plugin.getSqlManager().hasOfferData(tempData)) {
			System.out.println("Debug Info Has offer");
			player.sendMessage(plugin.getLanguageManager().showOffer.replace("{value}",
					"" + plugin.getSqlManager().getOfferData(tempData).getValue()));
		}
		return;

	}

	private void shopInfo(Player player, RegionData regionData) {
		return;
	}

	private void serverInfo(Player player, RegionData regionData) {
		return;
	}

	private void noInfo(Player player, RegionData regionData, Location loc, Chunk chunk) {
		/* Buy-able region */
		double economyValue = Landplugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(),
				loc.getWorld(), true);
		final String regionID = plugin.getLandManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
				chunk.getZ());
		player.sendMessage(plugin.getLanguageManager().landInfoA1.replace("{cost}", "" + economyValue)
				.replace("{regionID}", regionID));
		return;
	}

}
