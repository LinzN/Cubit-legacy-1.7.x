package de.kekshaus.cubit.commandSuite.adminCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.commandSuite.ICommand;
import de.kekshaus.cubit.commandSuite.adminCommands.main.CreateServerAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.CreateShopAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.DeleteServerAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.DeleteShopAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.HelpAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.ReloadAdmin;
import de.kekshaus.cubit.commandSuite.landCommands.main.OfferLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.SellLand;
import de.kekshaus.cubit.commandSuite.universalCommands.main.AddMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ChangeBiomeUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ChangeFlagUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListBiomesUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListSnapshotsUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.ListUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.RemoveMemberUniversal;
import de.kekshaus.cubit.commandSuite.universalCommands.main.VersionUniversal;
import de.kekshaus.cubit.plugin.CubitBukkitPlugin;
import de.kekshaus.cubit.plugin.PermissionNodes;
import de.linzn.cubit.internal.regionMgr.LandTypes;

public class CommandAdmin implements CommandExecutor {

	private CubitBukkitPlugin plugin;
	private boolean isLoaded = false;
	public ThreadPoolExecutor cmdThread;

	public CommandAdmin(CubitBukkitPlugin plugin) {
		this.plugin = plugin;
		this.cmdThread = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, String label, final String[] args) {
		cmdThread.submit(new Runnable() {
			@Override
			public void run() {
				if (args.length == 0) {
					getCmdMap().get("help").runCmd(cmd, sender, args);
				} else if (getCmdMap().containsKey(args[0])) {
					String command = args[0];
					if (!getCmdMap().get(command).runCmd(cmd, sender, args)) {
						sender.sendMessage(
								plugin.getYamlManager().getLanguage().errorCommand.replace("{command}", command));
					}
				} else {
					sender.sendMessage(
							plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}", "/cadmin help"));
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
			/* Protection AdminCommands */
			this.cmdMap.put("version", new VersionUniversal(this.plugin, null, null));
			this.cmdMap.put("help", new HelpAdmin(this.plugin, perm.helpAdminLand));
			this.cmdMap.put("setpvp",
					new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().pvpPacket,
							perm.flagAdminLand + "pvp", LandTypes.NOTYPE, true));
			this.cmdMap.put("setfire",
					new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().firePacket,
							perm.flagAdminLand + "fire", LandTypes.NOTYPE, true));
			this.cmdMap.put("setlock",
					new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().lockPacket,
							perm.flagAdminLand + "lock", LandTypes.NOTYPE, true));
			this.cmdMap.put("settnt",
					new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().tntPacket,
							perm.flagAdminLand + "tnt", LandTypes.NOTYPE, true));
			this.cmdMap.put("setmonster",
					new ChangeFlagUniversal(this.plugin, CubitBukkitPlugin.inst().getRegionManager().monsterPacket,
							perm.flagAdminLand + "monster", LandTypes.NOTYPE, true));
			this.cmdMap.put("remove", new SellLand(this.plugin, perm.sellAdminLand, true));
			this.cmdMap.put("addplayer",
					new AddMemberUniversal(this.plugin, perm.addMemberAdminLand, LandTypes.NOTYPE, true));
			this.cmdMap.put("removeplayer",
					new RemoveMemberUniversal(this.plugin, perm.removeMemberAdminLand, LandTypes.NOTYPE, true));
			this.cmdMap.put("setoffer", new OfferLand(this.plugin, perm.offerAdminLand, true));

			this.cmdMap.put("createserver", new CreateServerAdmin(this.plugin, perm.createServerAdminLand));
			this.cmdMap.put("deleteserver", new DeleteServerAdmin(this.plugin, perm.deleteServerAdminLand));
			this.cmdMap.put("createshop", new CreateShopAdmin(this.plugin, perm.createShopAdminLand));
			this.cmdMap.put("deleteshop", new DeleteShopAdmin(this.plugin, perm.deleteShopAdminLand));

			this.cmdMap.put("changebiome",
					new ChangeBiomeUniversal(this.plugin, perm.changeBiomeAdminLand, LandTypes.NOTYPE, true));
			this.cmdMap.put("listbiomes",
					new ListBiomesUniversal(this.plugin, perm.listBiomesAdminLand, LandTypes.NOTYPE));
			this.cmdMap.put("list", new ListUniversal(this.plugin, perm.listAdminLand, LandTypes.NOTYPE, true));
			this.cmdMap.put("reload", new ReloadAdmin(this.plugin, perm.reloadCubit));

			this.cmdMap.put("listsaves",
					new ListSnapshotsUniversal(this.plugin, perm.listSavesAdmin, LandTypes.NOTYPE, true));

			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
