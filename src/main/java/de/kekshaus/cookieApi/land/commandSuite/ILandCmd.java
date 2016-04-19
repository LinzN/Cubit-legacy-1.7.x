package de.kekshaus.cookieApi.land.commandSuite;

import org.bukkit.command.CommandSender;

public interface ILandCmd {
	public abstract boolean runCmd(CommandSender sender, String[] args);
}
