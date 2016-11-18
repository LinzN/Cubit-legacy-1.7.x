package de.kekshaus.cubit.commandSuite.shopCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.commandSuite.shopCommands.main.BuyShop;
import de.kekshaus.cubit.commandSuite.shopCommands.main.HelpShop;
import de.kekshaus.cubit.commandSuite.shopCommands.main.SellShop;
import de.kekshaus.cubit.commandSuite.universalCommands.main.AddMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ChangeBiomeUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.InfoUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.KickUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListBiomesUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.RemoveMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.VersionUniversal;
import de.kekshaus.cubit.plugin.Landplugin;
import de.kekshaus.cubit.plugin.PermissionNodes;

public class CommandShop implements CommandExecutor {

	private Landplugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandShop(Landplugin plugin) {
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

				if (Landplugin.inst().getYamlManager().getSettings().shopEnabledWorlds.contains(worldName)
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
								"/shop help"));
					}
				} else {
					sender.sendMessage(plugin.getYamlManager().getLanguage().noEnabledWorld);
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
			this.cmdMap.put("version", new VersionUniversal(this.plugin, null, null));
			this.cmdMap.put("help", new HelpShop(this.plugin, perm.helpShop));
			this.cmdMap.put("buy", new BuyShop(this.plugin, perm.buyShop));
			this.cmdMap.put("sell", new SellShop(this.plugin, perm.sellShop));
			this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listShop, LandTypes.SHOP));
			this.cmdMap.put("info", new InfoUniversal(this.plugin, perm.infoShop, LandTypes.SHOP));

			this.cmdMap.put("add", new AddMemberUniversal(this.plugin, perm.addMemberShop, LandTypes.SHOP, false));
			this.cmdMap.put("remove",
					new RemoveMemberUniversal(this.plugin, perm.removeMemberShop, LandTypes.SHOP, false));
			this.cmdMap.put("kick", new KickUniversal(this.plugin, perm.kickShop, LandTypes.SHOP));
			this.cmdMap.put("changebiome",
					new ChangeBiomeUniversal(this.plugin, perm.changeBiomeShop, LandTypes.SHOP, false));
			this.cmdMap.put("listbiomes", new ListBiomesUniversal(this.plugin, perm.listBiomesShop, LandTypes.SHOP));

			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
