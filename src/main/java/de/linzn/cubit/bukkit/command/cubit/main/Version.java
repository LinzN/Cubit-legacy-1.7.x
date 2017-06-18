package de.linzn.cubit.bukkit.command.cubit.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class Version implements ICommand {

	private CubitBukkitPlugin plugin;

	public Version(CubitBukkitPlugin plugin) {
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
