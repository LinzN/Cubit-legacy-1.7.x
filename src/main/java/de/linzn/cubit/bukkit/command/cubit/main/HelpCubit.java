package de.linzn.cubit.bukkit.command.cubit.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class HelpCubit implements ICommand {

	private CubitBukkitPlugin plugin;

	public HelpCubit(CubitBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {

		// if (args.length < 2) {
		// } else if (args[1].toString().equalsIgnoreCase("2")) {
		// return page2(sender);
		// }

		return page1(sender);
	}

	private boolean page1(CommandSender sender) {
		sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpHeaderP1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpE1P1);
		sender.sendMessage(plugin.getYamlManager().getLanguage().cubitHelpE2P1);

		return true;

	}

}
