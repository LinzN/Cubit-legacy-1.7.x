package de.kekshaus.cubit.commandSuite.shopCommands.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.plugin.Landplugin;

public class HelpShop implements ICommand {

	private Landplugin plugin;
	private String permNode;

	public HelpShop(Landplugin plugin, String permNode) {
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
		}

		return page1(sender);
	}

	private boolean page1(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpHeaderP1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE1P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE2P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE3P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE4P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpBottomP1);
		return true;

	}

	private boolean page2(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpHeaderP2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE1P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE2P2);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpBottomP2);
		return true;

	}

	private boolean page3(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpHeaderP3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE1P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE2P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpE3P3);
		sender.sendMessage(plugin.getYamlManager().getLanguage().shopHelpBottomP3);

		return true;

	}

}
