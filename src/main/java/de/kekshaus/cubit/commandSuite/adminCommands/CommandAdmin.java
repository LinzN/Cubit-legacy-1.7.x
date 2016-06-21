package de.kekshaus.cubit.commandSuite.adminCommands;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.commandSuite.adminCommands.main.CreateServerAdmin;
import de.kekshaus.cubit.commandSuite.adminCommands.main.DeleteServerAdmin;
import de.kekshaus.cubit.commandSuite.landCommands.main.AddMemberLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.FlagLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.OfferLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.RemoveMemberLand;
import de.kekshaus.cubit.commandSuite.landCommands.main.SellLand;
import de.kekshaus.cubit.plugin.Landplugin;
import de.kekshaus.cubit.plugin.PermissionNodes;

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
							plugin.getYamlManager().getLanguage().errorNoCommand.replace("{command}", "/ladmin help"));
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
			this.cmdMap.put("addplayer", new AddMemberLand(this.plugin, true, perm.addMemberAdminLand));
			this.cmdMap.put("removeplayer", new RemoveMemberLand(this.plugin, true, perm.removeMemberAdminLand));
			this.cmdMap.put("offer", new OfferLand(this.plugin, true, perm.offerAdminLand));

			this.cmdMap.put("createserver", new CreateServerAdmin(this.plugin, perm.createServerAdminLand));
			this.cmdMap.put("deleteserver", new DeleteServerAdmin(this.plugin, perm.deleteServerAdminLand));

			this.isLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

}
