package de.kekshaus.cubit.commandSuite.landCommands.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class HelpLand implements ILandCmd {

	private Landplugin plugin;
	private String permNode;

	public HelpLand(Landplugin plugin, String permNode) {
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
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE1P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE2P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE3P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE4P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE5P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE6P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE7P1);
		return true;

	}

	private boolean page2(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE1P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE2P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE3P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE4P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE5P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE6P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE7P2);
		return true;

	}

	private boolean page3(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE1P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE2P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE3P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE4P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE5P3);

		return true;

	}

	private boolean page4(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE1P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE2P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE3P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE4P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE5P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE6P4);
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHelpE7P4);
		return true;

	}

}
