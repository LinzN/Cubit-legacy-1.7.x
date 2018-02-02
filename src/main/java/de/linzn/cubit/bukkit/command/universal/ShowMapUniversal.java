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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by spatium on 20.06.17.
 */
public class ShowMapUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;

    public ShowMapUniversal(CubitBukkitPlugin plugin, String permNode, CubitType type) {
        this.plugin = plugin;
        this.permNode = permNode;
    }

    @Override
    public boolean runCmd(Command cmd, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            /* This is not possible from the server console */
            sender.sendMessage(plugin.getYamlManager().getLanguage().noConsoleMode);
            return true;
        }
        if (this.plugin.getYamlManager().getSettings().landUseScoreboardMap) {

            /* Build and get all variables */
            final Player player = (Player) sender;

            /* Permission Check */
            if (!player.hasPermission(this.permNode)) {
                sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoPermission);
                return true;
            }

            try {
                new BukkitRunnable() {
                    public void run() {
                        plugin.getScoreboardMapManager().toggleScoreboardMap(player.getUniqueId());
                    }
                }.runTask(plugin);
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "No map available");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This plugin part is not activated!");
        }

        return true;
    }
}
