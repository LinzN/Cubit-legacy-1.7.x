/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.bukkit.command.universal;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import de.linzn.cubit.internal.regionMgr.region.RegionData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;
    private LandTypes type;

    public InfoUniversal(CubitBukkitPlugin plugin, String permNode, LandTypes type) {
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
        RegionData regionData = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

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
        long lastLogin = plugin.getDataAccessManager().databaseType
                .get_last_login_profile(regionData.getOwnersUUID()[0]);
        String formatedTime = plugin.getDataAccessManager().databaseType.get_formate_date(lastLogin);
        String minBorder = regionData.getMinPoint();
        String maxBorder = regionData.getMaxPoint();
        String statusLock = plugin.getRegionManager().lockPacket.getStateColor(regionData)
                + plugin.getRegionManager().lockPacket.getPacketName();
        String statusFire = plugin.getRegionManager().firePacket.getStateColor(regionData)
                + plugin.getRegionManager().firePacket.getPacketName();
        String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(regionData)
                + plugin.getRegionManager().pvpPacket.getPacketName();
        String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(regionData)
                + plugin.getRegionManager().tntPacket.getPacketName();
        String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(regionData)
                + plugin.getRegionManager().monsterPacket.getPacketName();

        String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(regionData)
                + plugin.getRegionManager().potionPacket.getPacketName();

        player.sendMessage(
                plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));

        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}",
                plugin.getRegionManager().getPlayerNames(regionData.getOwnersUUID()).toString()));
        if (regionData.getMembersUUID().length != 0) {
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}",
                    plugin.getRegionManager().getPlayerNames(regionData.getMembersUUID()).toString()));
        }
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder).replace("{max}",
                maxBorder));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
                .replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
                .replace("{tnt}", statusTNT).replace("{potion}", statusPotion));

        if (plugin.getDataAccessManager().databaseType.get_is_offer(regionData.getRegionName(),
                regionData.getWorld())) {
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA2.replace("{value}",
                    "" + plugin.getDataAccessManager().databaseType
                            .get_offer(regionData.getRegionName(), regionData.getWorld()).getValue()));
        }

        boolean isMember = false;

        if (regionData.getMembersUUID().equals(player.getUniqueId())) {
            isMember = true;
        }

        if (plugin.getRegionManager().isToLongOffline(regionData.getOwnersUUID()[0], isMember)) {
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA3);
        }
        return;

    }

    private void shopInfo(Player player, RegionData regionData) {
        /* Get RegionData Info */
        if (regionData.getOwnersUUID().length != 0) {
            long lastLogin = plugin.getDataAccessManager().databaseType
                    .get_last_login_profile(regionData.getOwnersUUID()[0]);
            String formatedTime = plugin.getDataAccessManager().databaseType.get_formate_date(lastLogin);
            String minBorder = regionData.getMinPoint();
            String maxBorder = regionData.getMaxPoint();
            String statusLock = plugin.getRegionManager().lockPacket.getStateColor(regionData)
                    + plugin.getRegionManager().lockPacket.getPacketName();
            String statusFire = plugin.getRegionManager().firePacket.getStateColor(regionData)
                    + plugin.getRegionManager().firePacket.getPacketName();
            String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(regionData)
                    + plugin.getRegionManager().pvpPacket.getPacketName();
            String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(regionData)
                    + plugin.getRegionManager().tntPacket.getPacketName();
            String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(regionData)
                    + plugin.getRegionManager().monsterPacket.getPacketName();

            String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(regionData)
                    + plugin.getRegionManager().potionPacket.getPacketName();

            player.sendMessage(
                    plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));

            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}",
                    plugin.getRegionManager().getPlayerNames(regionData.getOwnersUUID()).toString()));
            if (regionData.getMembersUUID().length != 0) {
                player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}",
                        plugin.getRegionManager().getPlayerNames(regionData.getMembersUUID()).toString()));
            }
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder)
                    .replace("{max}", maxBorder));
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
                    .replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
                    .replace("{tnt}", statusTNT).replace("{potion}", statusPotion));
            if (plugin.getDataAccessManager().databaseType.get_is_offer(regionData.getRegionName(),
                    regionData.getWorld())) {
                player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA2.replace("{value}",
                        "" + plugin.getVaultManager().formateToEconomy(plugin.getDataAccessManager().databaseType
                                .get_offer(regionData.getRegionName(), regionData.getWorld()).getValue())));
            }

        } else {
            String value = plugin.getVaultManager().formateToEconomy(plugin.getDataAccessManager().databaseType
                    .get_offer(regionData.getRegionName(), regionData.getWorld()).getValue());

            player.sendMessage(
                    plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", regionData.getRegionName()));
            player.sendMessage(plugin.getYamlManager().getLanguage().isFreeAndBuyable
                    .replace("{regionID}", regionData.getRegionName()).replace("{price}", value));

        }
    }

    private void serverInfo(Player player, RegionData regionData) {
        String minBorder = regionData.getMinPoint();
        String maxBorder = regionData.getMaxPoint();

        String statusLock = plugin.getRegionManager().lockPacket.getStateColor(regionData)
                + plugin.getRegionManager().lockPacket.getPacketName();
        String statusFire = plugin.getRegionManager().firePacket.getStateColor(regionData)
                + plugin.getRegionManager().firePacket.getPacketName();
        String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(regionData)
                + plugin.getRegionManager().pvpPacket.getPacketName();
        String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(regionData)
                + plugin.getRegionManager().tntPacket.getPacketName();
        String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(regionData)
                + plugin.getRegionManager().monsterPacket.getPacketName();

        String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(regionData)
                + plugin.getRegionManager().potionPacket.getPacketName();

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
            double economyValue = CubitBukkitPlugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(),
                    loc.getWorld(), true);
            final String regionID = plugin.getRegionManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
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
