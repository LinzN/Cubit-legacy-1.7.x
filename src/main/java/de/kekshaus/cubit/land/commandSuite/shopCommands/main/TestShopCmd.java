package de.kekshaus.cubit.land.commandSuite.shopCommands.main;

import org.bukkit.command.CommandSender;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class TestShopCmd implements ILandCmd {

	private Landplugin plugin;

	public TestShopCmd(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {
		this.plugin.equals("");
		sender.sendMessage("Test hallo Shop");
		return true;
	}

}
