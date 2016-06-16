package de.kekshaus.cubit.commandSuite.adminCommands.main;

import org.bukkit.command.CommandSender;

import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class TestAdminCmd implements ILandCmd {

	private Landplugin plugin;

	public TestAdminCmd(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean runCmd(final CommandSender sender, String[] args) {
		this.plugin.equals("");
		sender.sendMessage("Test hallo. Admin Command");
		return true;
	}

}
