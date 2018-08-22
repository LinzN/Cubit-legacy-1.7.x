/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.bukkit.command.universal;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import de.linzn.cubit.internal.cubitRegion.region.CubitLand;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class InfoUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;
    private CubitType type;

    public InfoUniversal(CubitBukkitPlugin plugin, String permNode, CubitType type) {
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
        CubitLand cubitLand = plugin.getRegionManager().praseRegionData(loc.getWorld(), chunk.getX(), chunk.getZ());

        /* Send header */
        sender.sendMessage(plugin.getYamlManager().getLanguage().landHeader);

        /* Check Type of this Region */
        switch (cubitLand.getCubitType()) {
            case SERVER:
                serverInfo(player, cubitLand);
                break;
            case SHOP:
                shopInfo(player, cubitLand);
                break;
            case WORLD:
                landInfo(player, cubitLand);
                break;
            default:
                noInfo(player, cubitLand, loc, chunk);
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

    private void landInfo(Player player, CubitLand cubitLand) {
        /* Get CubitLand Info */
        long lastLogin = plugin.getDataAccessManager().databaseType
                .get_last_login_profile(cubitLand.getOwnersUUID()[0]);
        String formatedTime = plugin.getDataAccessManager().databaseType.get_formate_date(lastLogin);
        String minBorder = cubitLand.getMinPoint();
        String maxBorder = cubitLand.getMaxPoint();
        String statusLock = plugin.getRegionManager().lockPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().lockPacket.getPacketName();
        String statusFire = plugin.getRegionManager().firePacket.getStateColor(cubitLand)
                + plugin.getRegionManager().firePacket.getPacketName();
        String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().pvpPacket.getPacketName();
        String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().tntPacket.getPacketName();
        String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().monsterPacket.getPacketName();

        String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().potionPacket.getPacketName();

        player.sendMessage(
                plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", cubitLand.getLandName()));

        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}", Arrays.toString(cubitLand.getOwnerNames())));
        if (cubitLand.getMembersUUID().length != 0) {
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}", Arrays.toString(cubitLand.getMemberNames())));
        }
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder).replace("{max}",
                maxBorder));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
                .replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
                .replace("{tnt}", statusTNT).replace("{potion}", statusPotion));

        if (plugin.getDataAccessManager().databaseType.get_is_offer(cubitLand.getLandName(),
                cubitLand.getWorld())) {

            double offerValues = plugin.getDataAccessManager().databaseType.get_offer(cubitLand.getLandName(), cubitLand.getWorld()).getValue();
            double basePrice = CubitBukkitPlugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(), player.getLocation().getWorld(), true);
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA2.replace("{value}",
                    "" + offerValues + " + Base: " + basePrice));
        }

        boolean isMember = false;

        if (cubitLand.getMembersUUID().equals(player.getUniqueId())) {
            isMember = true;
        }

        if (plugin.getRegionManager().isToLongOffline(cubitLand.getOwnersUUID()[0], isMember)) {
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA3);
        }
        return;

    }

    private void shopInfo(Player player, CubitLand cubitLand) {
        /* Get CubitLand Info */
        if (cubitLand.getOwnersUUID().length != 0) {
            long lastLogin = plugin.getDataAccessManager().databaseType
                    .get_last_login_profile(cubitLand.getOwnersUUID()[0]);
            String formatedTime = plugin.getDataAccessManager().databaseType.get_formate_date(lastLogin);
            String minBorder = cubitLand.getMinPoint();
            String maxBorder = cubitLand.getMaxPoint();
            String statusLock = plugin.getRegionManager().lockPacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().lockPacket.getPacketName();
            String statusFire = plugin.getRegionManager().firePacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().firePacket.getPacketName();
            String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().pvpPacket.getPacketName();
            String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().tntPacket.getPacketName();
            String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().monsterPacket.getPacketName();

            String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(cubitLand)
                    + plugin.getRegionManager().potionPacket.getPacketName();

            player.sendMessage(
                    plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", cubitLand.getLandName()));

            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}",
                    Arrays.toString(cubitLand.getOwnerNames())));
            if (cubitLand.getMembersUUID().length != 0) {
                player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE3.replace("{members}",
                        Arrays.toString(cubitLand.getMemberNames())));
            }
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder)
                    .replace("{max}", maxBorder));
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE5.replace("{time}", formatedTime));
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
                    .replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
                    .replace("{tnt}", statusTNT).replace("{potion}", statusPotion));
            if (plugin.getDataAccessManager().databaseType.get_is_offer(cubitLand.getLandName(),
                    cubitLand.getWorld())) {
                player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA2.replace("{value}",
                        "" + plugin.getVaultManager().formattingToEconomy(plugin.getDataAccessManager().databaseType
                                .get_offer(cubitLand.getLandName(), cubitLand.getWorld()).getValue())));
            }

        } else {
            String value = plugin.getVaultManager().formattingToEconomy(plugin.getDataAccessManager().databaseType
                    .get_offer(cubitLand.getLandName(), cubitLand.getWorld()).getValue());

            player.sendMessage(
                    plugin.getYamlManager().getLanguage().landInfoE1.replace("{regionID}", cubitLand.getLandName()));
            player.sendMessage(plugin.getYamlManager().getLanguage().isFreeAndBuyable
                    .replace("{regionID}", cubitLand.getLandName()).replace("{price}", value));

        }
    }

    private void serverInfo(Player player, CubitLand cubitLand) {
        String minBorder = cubitLand.getMinPoint();
        String maxBorder = cubitLand.getMaxPoint();

        String statusLock = plugin.getRegionManager().lockPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().lockPacket.getPacketName();
        String statusFire = plugin.getRegionManager().firePacket.getStateColor(cubitLand)
                + plugin.getRegionManager().firePacket.getPacketName();
        String statusPvP = plugin.getRegionManager().pvpPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().pvpPacket.getPacketName();
        String statusTNT = plugin.getRegionManager().tntPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().tntPacket.getPacketName();
        String statusMonster = plugin.getRegionManager().monsterPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().monsterPacket.getPacketName();

        String statusPotion = plugin.getRegionManager().potionPacket.getStateColor(cubitLand)
                + plugin.getRegionManager().potionPacket.getPacketName();

        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE2.replace("{owner}", "Server"));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE4.replace("{min}", minBorder).replace("{max}",
                maxBorder));
        player.sendMessage(plugin.getYamlManager().getLanguage().landInfoE6.replace("{lock}", statusLock)
                .replace("{monster}", statusMonster).replace("{fire}", statusFire).replace("{pvp}", statusPvP)
                .replace("{tnt}", statusTNT).replace("{potion}", statusPotion));
        return;
    }

    private void noInfo(Player player, CubitLand cubitLand, Location loc, Chunk chunk) {
        if (this.type != CubitType.SHOP) {
            /* Buy-able region */
            double economyValue = 0D;
            if (!plugin.getYamlManager().getSettings().freeCubitLandWorld.contains(loc.getWorld().getName())) {
                economyValue = CubitBukkitPlugin.inst().getVaultManager().calculateLandCost(player.getUniqueId(),
                        loc.getWorld(), true);
            }
            final String regionID = plugin.getRegionManager().buildLandName(loc.getWorld().getName(), chunk.getX(),
                    chunk.getZ());
            player.sendMessage(plugin.getYamlManager().getLanguage().landInfoA1
                    .replace("{cost}", "" + plugin.getVaultManager().formattingToEconomy(economyValue))
                    .replace("{regionID}", regionID));
            return;
        } else {
            player.sendMessage(
                    plugin.getYamlManager().getLanguage().errorNoValidLandFound.replace("{type} ", type.toString()));
            return;
        }

    }

}
