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

package de.linzn.cubit.bukkit.command.shop;

import com.google.common.collect.Maps;
import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.command.shop.main.BuyShop;
import de.linzn.cubit.bukkit.command.shop.main.HelpShop;
import de.linzn.cubit.bukkit.command.shop.main.SellShop;
import de.linzn.cubit.bukkit.command.universal.*;
import de.linzn.cubit.bukkit.command.universal.blockedit.EditBiomeUniversal;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.bukkit.plugin.PermissionNodes;
import de.linzn.cubit.internal.cubitRegion.CubitType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandShop implements CommandExecutor {

    public ThreadPoolExecutor cmdThread;
    private CubitBukkitPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public CommandShop(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
        cmdThread.submit(() -> {
            String worldName = null;
            if (sender instanceof Player) {
                Player player = (Player) sender;
                worldName = player.getWorld().getName();
            }

            if (CubitBukkitPlugin.inst().getYamlManager().getSettings().shopEnabledWorlds.contains(worldName)
                    || worldName == null) {
                if (args.length == 0) {
                    // help site
                    getCmdMap().get("help").runCmd(cmd, sender, args);
                } else if (getCmdMap().containsKey(args[0])) {
                    String command = args[0];
                    if (!CubitBukkitPlugin.inst().getYamlManager().getDisabledCommands().disabledWorldCommandList.get(worldName).contains(command)) {
                        if (!getCmdMap().get(command).runCmd(cmd, sender, args)) {
                            sender.sendMessage(
                                    plugin.getYamlManager().getLanguage().errorCommand.replace("{command}", command));
                        }
                    } else {
                        sender.sendMessage(plugin.getYamlManager().getLanguage().noEnabledWorld);
                    }
                } else {
                    sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}",
                            "/shop help"));
                }
            } else {
                sender.sendMessage(plugin.getYamlManager().getLanguage().noEnabledWorld);
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
            this.cmdMap.put("help", new HelpShop(this.plugin, perm.helpShop));
            this.cmdMap.put("hilfe", new HelpShop(this.plugin, perm.helpShop));

            this.cmdMap.put("buy", new BuyShop(this.plugin, perm.buyShop));
            this.cmdMap.put("kaufen", new BuyShop(this.plugin, perm.buyShop));

            this.cmdMap.put("sell", new SellShop(this.plugin, perm.sellShop));
            this.cmdMap.put("verkaufen", new SellShop(this.plugin, perm.sellShop));

            this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listShop, CubitType.SHOP, false));
            this.cmdMap.put("info", new InfoUniversal(this.plugin, perm.infoShop, CubitType.SHOP));

            /* Universal Commands */
            this.cmdMap.put("add", new AddMemberUniversal(this.plugin, perm.addMemberShop, CubitType.SHOP, false));
            this.cmdMap.put("remove",
                    new RemoveMemberUniversal(this.plugin, perm.removeMemberShop, CubitType.SHOP, false));
            this.cmdMap.put("kick", new KickUniversal(this.plugin, perm.kickShop, CubitType.SHOP));
            this.cmdMap.put("changebiome",
                    new EditBiomeUniversal(this.plugin, perm.changeBiomeShop, CubitType.SHOP, false));
            this.cmdMap.put("listbiomes", new ListBiomesUniversal(this.plugin, perm.listBiomesShop, CubitType.SHOP));

            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

}
