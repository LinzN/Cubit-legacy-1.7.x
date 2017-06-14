package de.linzn.cubit.bukkit.command.shop;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import de.linzn.cubit.bukkit.command.ICommand;
import de.linzn.cubit.bukkit.command.shop.main.BuyShop;
import de.linzn.cubit.bukkit.command.shop.main.HelpShop;
import de.linzn.cubit.bukkit.command.shop.main.SellShop;
import de.linzn.cubit.bukkit.command.universal.AddMemberUniversal;
import de.linzn.cubit.bukkit.command.universal.ChangeBiomeUniversal;
import de.linzn.cubit.bukkit.command.universal.InfoUniversal;
import de.linzn.cubit.bukkit.command.universal.KickUniversal;
import de.linzn.cubit.bukkit.command.universal.ListBiomesUniversal;
import de.linzn.cubit.bukkit.command.universal.ListUniversal;
import de.linzn.cubit.bukkit.command.universal.RemoveMemberUniversal;
import de.linzn.cubit.bukkit.command.universal.VersionUniversal;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.bukkit.plugin.PermissionNodes;
import de.linzn.cubit.internal.regionMgr.LandTypes;

public class CommandShop implements CommandExecutor {

	private CubitBukkitPlugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandShop(CubitBukkitPlugin plugin) {
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

				if (CubitBukkitPlugin.inst().getYamlManager().getSettings().shopEnabledWorlds.contains(worldName)
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

	private TreeMap<String, ICommand> cmdMap = Maps.newTreeMap();

	public TreeMap<String, ICommand> getCmdMap() {
		return cmdMap;
	}

	public void loadCmd() {
		try {
			PermissionNodes perm = CubitBukkitPlugin.inst().getPermNodes();
			this.cmdMap.put("version", new VersionUniversal(this.plugin, null, null));
			this.cmdMap.put("help", new HelpShop(this.plugin, perm.helpShop));
			this.cmdMap.put("buy", new BuyShop(this.plugin, perm.buyShop));
			this.cmdMap.put("sell", new SellShop(this.plugin, perm.sellShop));
			this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listShop, LandTypes.SHOP, false));
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
