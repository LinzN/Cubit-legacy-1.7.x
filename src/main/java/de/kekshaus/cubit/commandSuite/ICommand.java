package de.kekshaus.cubit.commandSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {
	public abstract boolean runCmd(Command cmd, CommandSender sender, String[] args);
}
