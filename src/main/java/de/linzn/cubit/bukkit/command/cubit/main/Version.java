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

package de.linzn.cubit.bukkit.command.cubit.main;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Version implements ICommand {

    private CubitBukkitPlugin plugin;

    public Version(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().landHeader);
        sender.sendMessage(
                ChatColor.GREEN + "Cubit version: " + ChatColor.YELLOW + this.plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GREEN + "Written with love by Kekshaus");
        sender.sendMessage(ChatColor.GREEN + "For more Cubit informations, check this out:");
        sender.sendMessage(ChatColor.GREEN + "MineGaming - " + ChatColor.YELLOW + ChatColor.BOLD
                + "https://www.MineGaming.de");
        sender.sendMessage(ChatColor.GREEN + "You want cookies? Sorry they're all out :(");
        return true;
    }

}
