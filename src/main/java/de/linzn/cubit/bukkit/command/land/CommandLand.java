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

package de.linzn.cubit.bukkit.command.land;

import com.google.common.collect.Maps;
import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.command.land.main.*;
import de.linzn.cubit.bukkit.command.universal.*;
import de.linzn.cubit.bukkit.command.universal.blockedit.EditBiomeUniversal;
import de.linzn.cubit.bukkit.command.universal.blockedit.EditResetUniversal;
import de.linzn.cubit.bukkit.command.universal.blockedit.EditRestoreUniversal;
import de.linzn.cubit.bukkit.command.universal.blockedit.EditSaveUniversal;
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

public class CommandLand implements CommandExecutor {

    public ThreadPoolExecutor cmdThread;
    private CubitBukkitPlugin plugin;
    private boolean isLoaded = false;
    private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

    public CommandLand(CubitBukkitPlugin plugin) {
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

            if (CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnabledWorlds.contains(worldName)
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
                            "/land help"));
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
            /* GS Buy/Sell Commands */
            this.cmdMap.put("help", new HelpLand(this.plugin, perm.helpLand));
            this.cmdMap.put("hilfe", new HelpLand(this.plugin, perm.helpLand));

            this.cmdMap.put("buy", new BuyLand(this.plugin, perm.buyLand));
            this.cmdMap.put("kaufen", new BuyLand(this.plugin, perm.buyLand));

            this.cmdMap.put("sell", new SellLand(this.plugin, perm.sellLand, false));
            this.cmdMap.put("verkaufen", new SellLand(this.plugin, perm.sellLand, false));

            this.cmdMap.put("offer", new OfferLand(this.plugin, perm.offerLand, false));
            this.cmdMap.put("anbieten", new OfferLand(this.plugin, perm.offerLand, false));

            this.cmdMap.put("map", new ShowMapUniversal(plugin, perm.showMapLand, CubitType.WORLD));
            this.cmdMap.put("karte", new ShowMapUniversal(plugin, perm.showMapLand, CubitType.WORLD));

            this.cmdMap.put("takeoffer", new TakeOfferLand(this.plugin, perm.takeOfferLand));
            this.cmdMap.put("abkaufen", new TakeOfferLand(this.plugin, perm.takeOfferLand));

            this.cmdMap.put("buyup", new BuyupLand(this.plugin, perm.buyupLand));
            this.cmdMap.put("aufkaufen", new BuyupLand(this.plugin, perm.buyupLand));

            /* Universal Commands */
            this.cmdMap.put("info", new InfoUniversal(this.plugin, perm.infoLand, CubitType.WORLD));
            this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listLand, CubitType.WORLD, false));
            this.cmdMap.put("kick", new KickUniversal(this.plugin, perm.kickLand, CubitType.WORLD));

            this.cmdMap.put("add", new AddMemberUniversal(this.plugin, perm.addMemberLand, CubitType.WORLD, false));
            this.cmdMap.put("remove",
                    new RemoveMemberUniversal(this.plugin, perm.removeMemberLand, CubitType.WORLD, false));

            /* Protection Commands */
            this.cmdMap.put("pvp",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().pvpPacket,
                            perm.flagLand + "pvp", CubitType.WORLD, false));
            this.cmdMap.put("fire",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().firePacket,
                            perm.flagLand + "fire", CubitType.WORLD, false));
            this.cmdMap.put("lock",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().lockPacket,
                            perm.flagLand + "lock", CubitType.WORLD, false));
            this.cmdMap.put("tnt",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().tntPacket,
                            perm.flagLand + "tnt", CubitType.WORLD, false));
            this.cmdMap.put("monster",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().monsterPacket,
                            perm.flagLand + "monster", CubitType.WORLD, false));
            this.cmdMap.put("potion",
                    new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().potionPacket,
                            perm.flagLand + "potion", CubitType.WORLD, false));

            this.cmdMap.put("changebiome",
                    new EditBiomeUniversal(this.plugin, perm.changeBiomeLand, CubitType.WORLD, false));

            this.cmdMap.put("listbiomes", new ListBiomesUniversal(this.plugin, perm.listBiomesLand, CubitType.WORLD));
            this.cmdMap.put("listsaves",
                    new ListSnapshotsUniversal(this.plugin, perm.listSavesLand, CubitType.WORLD, false));

            this.cmdMap.put("save", new EditSaveUniversal(this.plugin, perm.saveLand, CubitType.WORLD));
            this.cmdMap.put("restore", new EditRestoreUniversal(this.plugin, perm.restoreLand, CubitType.WORLD));
            this.cmdMap.put("reset", new EditResetUniversal(this.plugin, perm.resetLand, CubitType.WORLD, false));

            this.isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

}
