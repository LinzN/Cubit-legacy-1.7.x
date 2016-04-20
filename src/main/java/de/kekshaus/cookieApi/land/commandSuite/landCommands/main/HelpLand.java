package de.kekshaus.cookieApi.land.commandSuite.landCommands.main;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class HelpLand implements ILandCmd {

	private Landplugin plugin;

	public HelpLand(Landplugin plugin) {
		this.plugin = plugin;
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
		if (!player.hasPermission(plugin.getPermNodes().helpLand)) {
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
		return true;

	}

	private boolean page2(CommandSender sender) {
		return true;

	}

	private boolean page3(CommandSender sender) {
		return true;

	}

	private boolean page4(CommandSender sender) {
		return true;

	}

}
