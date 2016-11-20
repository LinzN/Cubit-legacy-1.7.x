package de.kekshaus.cubit.commandSuite.adminCommands.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class HelpAdmin implements ILandCmd {

	private Landplugin plugin;
	private String permNode;

	public HelpAdmin(Landplugin plugin, String permNode) {
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
		} else if (args[1].toString().equalsIgnoreCase("5")) {
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

		return true;

	}

	private boolean page5(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpHeaderP5);
		sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE1P5);
		sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE2P5);
		sender.sendMessage(plugin.getYamlManager().getLanguage().adminHelpE3P5);

		return true;

	}

}
