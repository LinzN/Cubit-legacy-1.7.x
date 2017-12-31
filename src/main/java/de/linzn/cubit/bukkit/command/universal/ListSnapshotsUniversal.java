/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.bukkit.command.universal;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.blockMgr.snapshot.Snapshot;
import de.linzn.cubit.internal.regionMgr.LandTypes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class ListSnapshotsUniversal implements ICommand {

    private CubitBukkitPlugin plugin;
    private String permNode;
    private LandTypes type;
    private boolean isAdmin;

    public ListSnapshotsUniversal(CubitBukkitPlugin plugin, String permNode, LandTypes type, boolean isAdmin) {
        this.plugin = plugin;
        this.permNode = permNode;
        this.type = type;
        this.isAdmin = isAdmin;
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

        listUni(player, args, cmd);

        return true;
    }

    @SuppressWarnings("deprecation")
    private void listUni(Player player, String[] args, Command cmd) {
        int argForward = 0;
        UUID searchUUID = player.getUniqueId();
        String usage = " (page)";

        if (this.isAdmin) {
            argForward = 1;
            usage = " [name] (page)";
        }

        if (args.length >= 1 + argForward) {

            if (this.isAdmin) {
                searchUUID = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            }

            List<Snapshot> snapshotList = this.plugin.getBlockManager().getSnapshotHandler().getSnapshots(searchUUID);

            int pageNumber = 0;

            try {
                if (args.length == 2 + argForward) {
                    int argNumber = Integer.valueOf(args[1 + argForward]);
                    if (argNumber < 1) {
                        pageNumber = 0;
                    } else {
                        pageNumber = argNumber - 1;
                    }

                } else {
                    pageNumber = 0;
                }
            } catch (Exception e) {
                player.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
                return;
            }

            show(player, pageNumber, snapshotList);

        } else {
            player.sendMessage(plugin.getYamlManager().getLanguage().wrongArguments.replace("{usage}",
                    "/" + cmd.getLabel() + " " + args[0].toLowerCase() + usage));
            return;
        }
    }

    private void show(Player player, int pageNumber, List<Snapshot> snapshotList) {
        int snapshotCount = snapshotList.size();

        if (snapshotCount == 0) {
            player.sendMessage(plugin.getYamlManager().getLanguage().noSnapshotsFound);
            return;
        }

        if (pageNumber * 10 >= snapshotCount) {
            player.sendMessage(plugin.getYamlManager().getLanguage().pageNotFound);
            return;
        }

        List<Snapshot> subRegionList = snapshotList.subList(pageNumber * 10,
                pageNumber * 10 + 10 > snapshotCount ? snapshotCount : pageNumber * 10 + 10);

        player.sendMessage(plugin.getYamlManager().getLanguage().landListsnapshotsHeader
                .replace("{count}", "" + snapshotCount).replace("{entryMin}", "" + (pageNumber * 10 + 1))
                .replace("{entryMax}", "" + (pageNumber * 10 + 10)));

        int counter = pageNumber * 10 + 1;

        for (Snapshot snapshotObject : subRegionList) {
            player.sendMessage(
                    plugin.getYamlManager().getLanguage().landListsnapshotsEntry.replace("{counter}", "" + counter)
                            .replace("{snapshotName}", snapshotObject.getSnapshotId()).replace("{biome}", "NULL"));
            counter++;
        }
    }

}
