/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.bukkit.command.cubit;

import com.google.common.collect.Maps;
import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.command.cubit.main.HelpCubit;
import de.linzn.cubit.bukkit.command.cubit.main.Reload;
import de.linzn.cubit.bukkit.command.cubit.main.Version;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.bukkit.plugin.PermissionNodes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandCubit implements CommandExecutor {

    public ThreadPoolExecutor cmdThread;
    private CubitBukkitPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public CommandCubit(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
        cmdThread.submit(() -> {
            if (args.length == 0) {
                getCmdMap().get("help").runCmd(cmd, sender, args);
            } else if (getCmdMap().containsKey(args[0])) {
                String command = args[0];
                if (!getCmdMap().get(command).runCmd(cmd, sender, args)) {
                    sender.sendMessage(
                            plugin.getYamlManager().getLanguage().errorCommand.replace("{command}", command));
                }
            } else {
                sender.sendMessage(
                        plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}", "/cubit help"));
            }
        });
        return true;
    }

    public TreeMap<String, ICommand> getCmdMap() {
        return cmdMap;
    }

    public void loadCmd() {
        try {
            PermissionNodes perm = CubitBukkitPlugin.inst().getPermNodes();
            /* Protection CubitCommand */
            this.cmdMap.put("help", new HelpCubit(this.plugin));
            this.cmdMap.put("h", new HelpCubit(this.plugin));
            this.cmdMap.put("version", new Version(this.plugin));
            this.cmdMap.put("v", new Version(this.plugin));
            this.cmdMap.put("reload", new Reload(this.plugin, perm.reloadCubit));
            this.cmdMap.put("rl", new Reload(this.plugin, perm.reloadCubit));

            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

}
