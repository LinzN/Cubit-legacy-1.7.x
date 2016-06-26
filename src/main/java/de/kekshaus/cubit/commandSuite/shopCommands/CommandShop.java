package de.kekshaus.cubit.commandSuite.shopCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.commandSuite.shopCommands.main.CreateShop;
import de.kekshaus.cubit.commandSuite.universalCommands.main.AddMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.InfoUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.KickUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.RemoveMemberUniversal;
import de.kekshaus.cubit.plugin.Landplugin;

public class CommandShop implements CommandExecutor {

	private Landplugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandShop(Landplugin plugin) {
		this.plugin = plugin;
		this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	}

	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, final String[] args) {
		cmdThread.submit(new Runnable() {
			@Override
			public void run() {
				if (args.length == 0) {
					// help site
				} else if (getCmdMap().containsKey(args[0])) {
					String command = args[0];
					if (!getCmdMap().get(command).runCmd(sender, args)) {
						sender.sendMessage(
								plugin.getYamlManager().getLanguage().errorCommand.replace("{command}", command));
					}
				} else {
					sender.sendMessage(
							plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}", "/shop help"));
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
			this.cmdMap.put("create", new CreateShop(this.plugin, null));

			this.cmdMap.put("info", new InfoUniversal(this.plugin, null));

			this.cmdMap.put("add", new AddMemberUniversal(this.plugin, null, LandTypes.SHOP, false));
			this.cmdMap.put("remove", new RemoveMemberUniversal(this.plugin, null, LandTypes.SHOP, false));
			this.cmdMap.put("kick", new KickUniversal(this.plugin, null, LandTypes.SHOP));
			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
