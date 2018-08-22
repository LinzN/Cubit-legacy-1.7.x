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

package de.linzn.cubit.internal.particle;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.particle.border.ParticleSender;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ParticleManager {

    private CubitBukkitPlugin plugin;

    public ParticleManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("[Setup] ParticleManager");
        if (Bukkit.getPluginManager().getPlugin("ParticleLIB") != null) {
            plugin.getLogger().info("Using ParticleLIB as provider");
        } else if (isSpigot()) {
            plugin.getLogger().info("Using SpigotAPI as provider");
        } else {
            plugin.getLogger().info("No provider found. Disable particles");
        }
        this.plugin = plugin;
    }

    public boolean sendBuy(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.HAPPY_VILLAGER, Effect.FIREWORKS_SPARK));
            }

        }
        return true;
    }

    public boolean sendSell(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.WITCH_MAGIC, Effect.FIREWORKS_SPARK));
            }

        }
        return true;
    }

    public boolean sendInfo(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, null, Effect.FIREWORKS_SPARK));

            }
        }
        return true;
    }

    public boolean changeFlag(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.FLAME, Effect.FIREWORKS_SPARK));

            }
        }
        return true;
    }

    public boolean addMember(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.HEART, Effect.FIREWORKS_SPARK));
            }

        }
        return true;
    }

    public boolean removeMember(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.VILLAGER_THUNDERCLOUD, Effect.FIREWORKS_SPARK));

            }
        }
        return true;
    }

    public boolean changeBiome(final Player player, final Location loc) {
        if (CubitBukkitPlugin.inst().getYamlManager().getSettings().particleUse) {

            if (isSpigot()) {
                plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> new ParticleSender(player, loc, Effect.PORTAL, Effect.FIREWORKS_SPARK));
            }

        }
        return true;
    }

    public boolean isSpigot() {
        try {
            Class.forName("net.md_5.bungee.api.ChatColor");
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }
}
