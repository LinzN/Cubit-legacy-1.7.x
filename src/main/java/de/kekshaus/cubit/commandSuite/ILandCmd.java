package de.kekshaus.cubit.commandSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ILandCmd {
	public abstract boolean runCmd(Command cmd, CommandSender sender, String[] args);
}
