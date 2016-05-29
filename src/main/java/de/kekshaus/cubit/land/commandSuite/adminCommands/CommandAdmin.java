package de.kekshaus.cubit.land.commandSuite.adminCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.commandSuite.ILandCmd;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.FlagLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.SellLand;
import de.kekshaus.cubit.land.plugin.PermissionNodes;

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
						sender.sendMessage(plugin.getLanguageManager().errorCommand.replace("{command}", command));
					}
				} else {
					sender.sendMessage(plugin.getLanguageManager().errorNoCommand.replace("{command}", "/ladmin help"));
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
			PermissionNodes perm = Landplugin.inst().getPermNodes();
			/* Protection AdminCommands */
			this.cmdMap.put("setpvp", new FlagLand(this.plugin, Landplugin.inst().getLandManager().pvpPacket, true,
					perm.flagAdminLand + "pvp"));
			this.cmdMap.put("setfire", new FlagLand(this.plugin, Landplugin.inst().getLandManager().firePacket, true,
					perm.flagAdminLand + "fire"));
			this.cmdMap.put("setlock", new FlagLand(this.plugin, Landplugin.inst().getLandManager().lockPacket, true,
					perm.flagAdminLand + "lock"));
			this.cmdMap.put("settnt", new FlagLand(this.plugin, Landplugin.inst().getLandManager().tntPacket, true,
					perm.flagAdminLand + "tnt"));
			this.cmdMap.put("setmonster", new FlagLand(this.plugin, Landplugin.inst().getLandManager().monsterPacket,
					true, perm.flagAdminLand + "monster"));
			this.cmdMap.put("remove", new SellLand(this.plugin, true, perm.sellAdminLand));
			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
