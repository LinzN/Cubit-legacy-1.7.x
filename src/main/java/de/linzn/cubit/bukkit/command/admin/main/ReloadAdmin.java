package de.linzn.cubit.bukkit.command.admin.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class ReloadAdmin implements ICommand {

	private CubitBukkitPlugin plugin;
	private String permNode;

	public ReloadAdmin(CubitBukkitPlugin plugin, String permNode) {
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

		try {
			this.plugin.getYamlManager().reloadAllConfigs();
			sender.sendMessage("Cubit configs reloaded!");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
