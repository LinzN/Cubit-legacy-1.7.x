package de.kekshaus.cookieApi.land.commandSuite.adminCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.commandSuite.ILandCmd;
import de.kekshaus.cookieApi.land.commandSuite.adminCommands.main.TestAdminCmd;

public class CommandAdmin implements CommandExecutor {

	private Landplugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandAdmin(Landplugin plugin) {
		this.plugin = plugin;
		this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	}

	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		cmdThread.submit(new Runnable() {
			public void run() {
				if (args.length == 0) {
					// help site
				} else if (getCmdMap().containsKey(args[0])) {
					String command = args[0];
					if (!getCmdMap().get(command).runCmd(sender, args)) {
						// Command error
					}
				} else {
					// No valid command
				}
			}
		});
		return true;
	}

	private TreeMap<String, ILandCmd> cmdMap = Maps.newTreeMap();

	public TreeMap<String, ILandCmd> getCmdMap() {
		return cmdMap;
	}

	public void loadCmd() {
		try {
			this.cmdMap.put("testcmd", new TestAdminCmd(this.plugin));
			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
