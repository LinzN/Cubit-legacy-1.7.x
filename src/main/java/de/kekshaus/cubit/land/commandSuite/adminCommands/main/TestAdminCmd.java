package de.kekshaus.cubit.land.commandSuite.adminCommands.main;

import org.bukkit.command.CommandSender;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;

public class TestAdminCmd implements ILandCmd {

	private Landplugin plugin;

	public TestAdminCmd(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {

		sender.sendMessage("Test hallo. Admin Command");
		return true;
	}

}
