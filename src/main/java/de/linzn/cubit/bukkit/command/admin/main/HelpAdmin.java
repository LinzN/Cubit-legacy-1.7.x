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

package de.linzn.cubit.bukkit.command.admin.main;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpAdmin implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;

    public HelpAdmin(CubitBukkitPlugin plugin, String permNode) {
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

        if (args.length < 2) {
        } else if (args[1].equalsIgnoreCase("2")) {
            return page2(sender);
        } else if (args[1].equalsIgnoreCase("3")) {
            return page3(sender);
        } else if (args[1].equalsIgnoreCase("4")) {
            return page4(sender);
        } else if (args[1].equalsIgnoreCase("5")) {
            return page5(sender);
        }

        return page1(sender);
    }

    private boolean page1(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE4P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE5P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE6P1);

        return true;

    }

    private boolean page2(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP2);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P2);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P2);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P2);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE4P2);

        return true;

    }

    private boolean page3(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP3);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P3);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P3);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P3);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE4P3);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE5P3);

        return true;

    }

    private boolean page4(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP4);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P4);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P4);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P4);

        return true;

    }

    private boolean page5(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP5);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P5);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P5);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P5);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE4P5);
        sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE5P5);

        return true;

    }

}
