package de.kekshaus.cubit.commandSuite.universalCommands.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.plugin.Landplugin;

public class VersionUniversal implements ICommand {

	private Landplugin plugin;

	public VersionUniversal(Landplugin plugin, String permNode, LandTypes type) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().landHeader);
		sender.sendMessage(
				ChatColor.GREEN + "Cubit version: " + ChatColor.YELLOW + this.plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GREEN + "Written with love by Kekshaus");
		sender.sendMessage(ChatColor.GREEN + "For more Cubit informations, check this out:");
		sender.sendMessage(ChatColor.GREEN + "Enigmar Systems - " + ChatColor.YELLOW + ChatColor.BOLD
				+ "https://public.enigmar.de");
		sender.sendMessage(ChatColor.GREEN + "You want cookies? Sorry they're all out :(");
		return true;
	}

}
