package de.kekshaus.cubit.commandSuite.universalCommands.main;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.blockAPI.snapshot.Snapshot;
import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.plugin.Landplugin;

@SuppressWarnings("unused")
public class ListSnapshotsUniversal implements ICommand {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;
	private boolean isAdmin;

	public ListSnapshotsUniversal(Landplugin plugin, String permNode, LandTypes type, boolean isAdmin) {
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
			
			if (this.isAdmin){
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

		player.sendMessage(plugin.getYamlManager().getLanguage().landListsnapshotsHeader.replace("{count}", "" + snapshotCount)
				.replace("{entryMin}", "" + (pageNumber * 10 + 1)).replace("{entryMax}", "" + (pageNumber * 10 + 10)));

		int counter = pageNumber * 10 + 1;

		for (Snapshot snapshotObject : subRegionList) {
			player.sendMessage(plugin.getYamlManager().getLanguage().landListsnapshotsEntry.replace("{counter}", "" + counter).replace("{snapshotName}", snapshotObject.getSnapshotId()).replace("{biome}", "NULL"));
			counter++;
		}
	}

}
