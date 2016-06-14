package de.kekshaus.cubit.land.commandSuite.landCommands.main;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class HelpLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;

	public HelpLand(Landplugin plugin, String permNode) {
		this.plugin = plugin;
		this.permNode = permNode;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			/* This is not possible from the server console */
			sender.sendMessage(plugin.getLanguageManager().noConsoleMode);
			return true;
		}
		/* Build and get all variables */
		Player player = (Player) sender;

		/* Permission Check */
		if (!player.hasPermission(this.permNode)) {
			sender.sendMessage(plugin.getLanguageManager().errorNoPermission);
			return true;
		}

		if (args.length < 2) {
		} else if (args[1].toString().equalsIgnoreCase("2")) {
			return page2(sender);
		} else if (args[1].toString().equalsIgnoreCase("3")) {
			return page3(sender);
		} else if (args[1].toString().equalsIgnoreCase("4")) {
			return page4(sender);
		}

		return page1(sender);
	}

	private boolean page1(CommandSender sender) {
		sender.sendMessage(plugin.getLanguageManager().landHelpE1P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE2P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE3P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE4P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE5P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE6P1);
		sender.sendMessage(plugin.getLanguageManager().landHelpE7P1);
		return true;

	}

	private boolean page2(CommandSender sender) {
		sender.sendMessage(plugin.getLanguageManager().landHelpE1P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE2P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE3P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE4P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE5P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE6P2);
		sender.sendMessage(plugin.getLanguageManager().landHelpE7P2);
		return true;

	}

	private boolean page3(CommandSender sender) {
		sender.sendMessage(plugin.getLanguageManager().landHelpE1P3);
		sender.sendMessage(plugin.getLanguageManager().landHelpE2P3);
		sender.sendMessage(plugin.getLanguageManager().landHelpE3P3);
		sender.sendMessage(plugin.getLanguageManager().landHelpE4P3);
		sender.sendMessage(plugin.getLanguageManager().landHelpE5P3);

		return true;

	}

	private boolean page4(CommandSender sender) {
		sender.sendMessage(plugin.getLanguageManager().landHelpE1P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE2P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE3P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE4P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE5P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE6P4);
		sender.sendMessage(plugin.getLanguageManager().landHelpE7P4);
		return true;

	}

}
