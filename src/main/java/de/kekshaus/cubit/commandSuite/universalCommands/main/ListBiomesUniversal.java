package de.kekshaus.cubit.commandSuite.universalCommands.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.plugin.Landplugin;

public class ListBiomesUniversal implements ICommand {

	private Landplugin plugin;
	private String permNode;

	public ListBiomesUniversal(Landplugin plugin, String permNode, LandTypes type) {
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

		Biome[] biomes = Biome.values();

		List<String> biomeList = new ArrayList<String>();
		for (Biome biome : biomes) {
			biomeList.add(biome.name());
		}

		sender.sendMessage(plugin.getYamlManager().getLanguage().landBiomeListHeader);
		sender.sendMessage(biomeList.toString().replace("[", " ").replace("]", " "));

		return true;
	}

}
