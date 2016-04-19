package de.kekshaus.cookieApi.land.commandSuite.shopCommands.main;

import org.bukkit.command.CommandSender;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;

public class TestShopCmd implements ILandCmd {

	private Landplugin plugin;

	public TestShopCmd(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {

		sender.sendMessage("Test hallo Shop");
		return true;
	}

}
