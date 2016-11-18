package de.kekshaus.cubit.commandSuite.universalCommands.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class VersionUniversal implements ILandCmd {

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
