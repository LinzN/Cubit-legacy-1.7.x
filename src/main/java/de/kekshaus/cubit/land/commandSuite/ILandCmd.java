package de.kekshaus.cubit.land.commandSuite;

import org.bukkit.command.CommandSender;

public interface ILandCmd {
	public abstract boolean runCmd(CommandSender sender, String[] args);
}
