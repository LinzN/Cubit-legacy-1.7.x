package de.linzn.cubit.bukkit.command.cubit.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;

public class Reload implements ICommand {

	private CubitBukkitPlugin plugin;
	private String permNode;

	public Reload(CubitBukkitPlugin plugin, String permNode) {
		this.plugin = plugin;
		this.permNode = permNode;
	}

	@Override
	public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			/* Build and get all variables */
			Player player = (Player) sender;
			/* Permission Check */
			if (!player.hasPermission(this.permNode)) {
				sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoPermission);
				return true;
			}
		}

		try {
			this.plugin.getYamlManager().reloadAllConfigs();
			sender.sendMessage("Cubit configs has been reloaded!");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
