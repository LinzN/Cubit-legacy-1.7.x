package de.kekshaus.cubit.land.commandSuite.landCommands;

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
import de.kekshaus.cubit.land.commandSuite.landCommands.main.AddMemberLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.BuyLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.FlagLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.HelpLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.InfoLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.OfferLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.RemoveMemberLand;
import de.kekshaus.cubit.land.commandSuite.landCommands.main.SellLand;
import de.kekshaus.cubit.land.plugin.PermissionNodes;

public class CommandLand implements CommandExecutor {

	private Landplugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandLand(Landplugin plugin) {
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
					getCmdMap().get("help").runCmd(sender, args);
				} else if (getCmdMap().containsKey(args[0])) {
					String command = args[0];
					if (!getCmdMap().get(command).runCmd(sender, args)) {
						sender.sendMessage(plugin.getLanguageManager().errorCommand.replace("{command}", command));
					}
				} else {
					sender.sendMessage(plugin.getLanguageManager().errorNoCommand.replace("{command}", "/land help"));
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
			/* GS Buy/Sell Commands */
			this.cmdMap.put("info", new InfoLand(this.plugin, perm.infoLand));
			this.cmdMap.put("help", new HelpLand(this.plugin, perm.helpLand));
			this.cmdMap.put("buy", new BuyLand(this.plugin, perm.buyLand));
			this.cmdMap.put("sell", new SellLand(this.plugin, false, perm.sellLand));

			this.cmdMap.put("add", new AddMemberLand(this.plugin, false, perm.addMemberLand));
			this.cmdMap.put("remove", new RemoveMemberLand(this.plugin, false, perm.removeMemberLand));

			this.cmdMap.put("offer", new OfferLand(this.plugin, false, perm.offerLand));

			/* Protection Commands */
			this.cmdMap.put("pvp", new FlagLand(this.plugin, Landplugin.inst().getLandManager().pvpPacket, false,
					perm.flagLand + "pvp"));
			this.cmdMap.put("fire", new FlagLand(this.plugin, Landplugin.inst().getLandManager().firePacket, false,
					perm.flagLand + "fire"));
			this.cmdMap.put("lock", new FlagLand(this.plugin, Landplugin.inst().getLandManager().lockPacket, false,
					perm.flagLand + "lock"));
			this.cmdMap.put("tnt", new FlagLand(this.plugin, Landplugin.inst().getLandManager().tntPacket, false,
					perm.flagLand + "tnt"));
			this.cmdMap.put("monster", new FlagLand(this.plugin, Landplugin.inst().getLandManager().monsterPacket,
					false, perm.flagLand + "monster"));
			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
