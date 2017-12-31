/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.bukkit.command.cubit.main;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCubit implements ICommand {

    private CubitBukkitPlugin plugin;

    public HelpCubit(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
        return page1(sender);
    }

    private boolean page1(CommandSender sender) {
        sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpHeaderP1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpE1P1);
        sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpE2P1);

        return true;

    }

}
