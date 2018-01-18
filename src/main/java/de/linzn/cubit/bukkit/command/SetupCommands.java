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

package de.linzn.cubit.bukkit.command;

import de.linzn.cubit.bukkit.command.admin.CommandAdmin;
import de.linzn.cubit.bukkit.command.cubit.CommandCubit;
import de.linzn.cubit.bukkit.command.land.CommandLand;
import de.linzn.cubit.bukkit.command.shop.CommandShop;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class SetupCommands {

    private CubitBukkitPlugin plugin;
    private String commandCubit = "cubit";
    private String commandLand = "land";
    private String commandShop = "shop";
    private String commandAdmin = "cadmin";

    public SetupCommands(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("Loading CommandSuite");
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        /* Command setup for /cubit */
        CommandCubit cubitClass = new CommandCubit(this.plugin);
        if (!cubitClass.isLoaded())
            cubitClass.loadCmd();
        plugin.getLogger().info("Register command /cubit");
        plugin.getCommand(commandCubit).setExecutor(cubitClass);

		/* Command setup for /land */
        CommandLand landClass = new CommandLand(this.plugin);
        if (!landClass.isLoaded())
            landClass.loadCmd();
        plugin.getLogger().info("Register command /land");
        plugin.getCommand(commandLand).setExecutor(landClass);

		/* Command setup for /shop */
        CommandShop shopClass = new CommandShop(this.plugin);
        if (!shopClass.isLoaded())
            shopClass.loadCmd();
        plugin.getLogger().info("Register command /shop");
        plugin.getCommand(commandShop).setExecutor(shopClass);

		/* Command setup for /cadmin */
        CommandAdmin adminClass = new CommandAdmin(this.plugin);
        if (!adminClass.isLoaded())
            adminClass.loadCmd();
        plugin.getLogger().info("Register command /cadmin");
        plugin.getCommand(commandAdmin).setExecutor(adminClass);
    }

}
