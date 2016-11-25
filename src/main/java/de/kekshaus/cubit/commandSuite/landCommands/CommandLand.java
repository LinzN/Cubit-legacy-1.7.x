package de.kekshaus.cubit.commandSuite.landCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.api.classes.enums.LandTypes;
import de.kekshaus.cubit.api.classes.interfaces.ICommand;
import de.kekshaus.cubit.commandSuite.landCommands.main.BuyLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.BuyupLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.HelpLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.OfferLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.SellLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.TakeOfferLand;
import de.kekshaus.cubit.commandSuite.universalCommands.main.AddMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ChangeBiomeUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ChangeFlagUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.InfoUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.KickUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListBiomesUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.RemoveMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.VersionUniversal;
import de.kekshaus.cubit.plugin.Landplugin;
import de.kekshaus.cubit.plugin.PermissionNodes;

public class CommandLand implements CommandExecutor {

	private Landplugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandLand(Landplugin plugin) {
		this.plugin = plugin;
		this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
		cmdThread.submit(new Runnable() {
			@Override
			public void run() {
				String worldName = null;
				if (sender instanceof Player) {
					Player player = (Player) sender;
					worldName = player.getWorld().getName();
				}

				if (Landplugin.inst().getYamlManager().getSettings().landEnabledWorlds.contains(worldName)
						|| worldName == null) {
					if (args.length == 0) {
						// help site
						getCmdMap().get("help").runCmd(cmd, sender, args);
					} else if (getCmdMap().containsKey(args[0])) {
						String command = args[0];
						if (!getCmdMap().get(command).runCmd(cmd, sender, args)) {
							sender.sendMessage(
									plugin.getYamlManager().getLanguage().errorCommand.replace("{command}", command));
						}
					} else {
						sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}",
								"/land help"));
					}
				} else {
					sender.sendMessage(plugin.getYamlManager().getLanguage().noEnabledWorld);
				}
			}
		});
		return true;
	}

	private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

	public TreeMap<String, ICommand> getCmdMap() {
		return cmdMap;
	}

	public void loadCmd() {
		try {
			PermissionNodes perm = Landplugin.inst().getPermNodes();
			/* GS Buy/Sell Commands */
			this.cmdMap.put("version", new VersionUniversal(this.plugin, null, null));
			this.cmdMap.put("help", new HelpLand(this.plugin, perm.helpLand));
			this.cmdMap.put("buy", new BuyLand(this.plugin, perm.buyLand));
			this.cmdMap.put("sell", new SellLand(this.plugin, perm.sellLand, false));
			this.cmdMap.put("kaufen", new BuyLand(this.plugin, perm.buyLand));
			this.cmdMap.put("verkaufen", new SellLand(this.plugin, perm.sellLand, false));
			this.cmdMap.put("offer", new OfferLand(this.plugin, perm.offerLand, false));

			this.cmdMap.put("takeoffer", new TakeOfferLand(this.plugin, perm.takeOfferLand));
			this.cmdMap.put("buyup", new BuyupLand(this.plugin, perm.buyupLand));

			/* Universal Commands */
			this.cmdMap.put("info", new InfoUniversal(this.plugin, perm.infoLand, LandTypes.WORLD));
			this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listLand, LandTypes.WORLD, false));
			this.cmdMap.put("kick", new KickUniversal(this.plugin, perm.kickLand, LandTypes.WORLD));
			this.cmdMap.put("add", new AddMemberUniversal(this.plugin, perm.addMemberLand, LandTypes.WORLD, false));
			this.cmdMap.put("remove",
					new RemoveMemberUniversal(this.plugin, perm.removeMemberLand, LandTypes.WORLD, false));

			/* Protection Commands */
			this.cmdMap.put("pvp", new ChangeFlagUniversal(this.plugin, Landplugin.inst().getRegionManager().pvpPacket,
					perm.flagLand + "pvp", LandTypes.WORLD, false));
			this.cmdMap.put("fire", new ChangeFlagUniversal(this.plugin,
					Landplugin.inst().getRegionManager().firePacket, perm.flagLand + "fire", LandTypes.WORLD, false));
			this.cmdMap.put("lock", new ChangeFlagUniversal(this.plugin,
					Landplugin.inst().getRegionManager().lockPacket, perm.flagLand + "lock", LandTypes.WORLD, false));
			this.cmdMap.put("tnt", new ChangeFlagUniversal(this.plugin, Landplugin.inst().getRegionManager().tntPacket,
					perm.flagLand + "tnt", LandTypes.WORLD, false));
			this.cmdMap.put("monster",
					new ChangeFlagUniversal(this.plugin, Landplugin.inst().getRegionManager().monsterPacket,
							perm.flagLand + "monster", LandTypes.WORLD, false));
			this.cmdMap.put("potion",
					new ChangeFlagUniversal(this.plugin, Landplugin.inst().getRegionManager().potionPacket,
							perm.flagLand + "potion", LandTypes.WORLD, false));

			this.cmdMap.put("changebiome",
					new ChangeBiomeUniversal(this.plugin, perm.changeBiomeLand, LandTypes.WORLD, false));

			this.cmdMap.put("listbiomes", new ListBiomesUniversal(this.plugin, perm.listBiomesLand, LandTypes.WORLD));

			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
