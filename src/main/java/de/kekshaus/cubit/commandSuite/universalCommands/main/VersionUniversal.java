package de.kekshaus.cubit.commandSuite.universalCommands.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.kekshaus.cubi.api.classes.enums.LandTypes;
import de.kekshaus.cubi.api.classes.interfaces.ICommand;
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
		sender.sendMessage(ChatColor.GREEN + "Written and copyleft by Kekshaus");
		sender.sendMessage(ChatColor.GREEN + "You want cubit too? Visit https://www.EXYLON.de");
		return true;
	}

}
