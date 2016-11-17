package de.kekshaus.cubit.commandSuite.universalCommands.main;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.commandSuite.ILandCmd;
import de.kekshaus.cubit.plugin.Landplugin;

public class ListUniversal implements ILandCmd {

	private Landplugin plugin;
	private String permNode;
	private LandTypes type;

	public ListUniversal(Landplugin plugin, String permNode, LandTypes type) {
		this.plugin = plugin;
		this.permNode = permNode;
		this.type = type;
	}

	@Override
	public boolean runCmd(final Command cmd, final CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			/* This is not possible from the server console */
			sender.sendMessage(plugin.getYamlManager().getLanguage().noConsoleMode);
			return true;
		}

		/* Build and get all variables */
		Player player = (Player) sender;

		/* Permission Check */
		if (!player.hasPermission(this.permNode)) {
			sender.sendMessage(plugin.getYamlManager().getLanguage().errorNoPermission);
			return true;
		}
		
		List<RegionData> list = this.plugin.getLandManager().getAllRegionsFromPlayer(player.getUniqueId(), player.getLocation().getWorld(), type);

		showList(player, list, args);

		return true;
	}
	
	
	
	private void showList(Player player, List<RegionData> regionList, String[] args){
		if (args.length <= 2) {
			int pageNumb = 0;
			
			try {
				if (args.length == 2) {
					int number = Integer.valueOf(args[1]);
					if (number < 1) {
						pageNumb = 0;
					} else {
						pageNumb = Integer.valueOf(args[1]) - 1;
					}

				} else {
					pageNumb = 0;
				}
			} catch (Exception e) {
				player.sendMessage(plugin.getYamlManager().getLanguage().noNumberFound);
				return;
			}

			int rgCount = regionList.size();
			
			if (pageNumb * 10 >= rgCount) {
				player.sendMessage(plugin.getYamlManager().getLanguage().pageNotFound);
				return;
			}
			
			List<RegionData> subList = regionList.subList(pageNumb * 10, pageNumb * 10 + 10 > rgCount ? rgCount : pageNumb * 10 + 10);
			
			player.sendMessage(plugin.getYamlManager().getLanguage().landListHeader.replace("{count}", ""+ rgCount).replace("{entryMin}", "" + (pageNumb * 10 + 1)).replace("{entryMax}", "" + (pageNumb * 10 + 10)));

			int counter = pageNumb * 10 + 1;
			
			for (RegionData rgData : subList) {
				player.sendMessage(plugin.getYamlManager().getLanguage().landListEntry.replace("{counter}", "" + counter).replace("{regionID}", rgData.getRegionName()).replace("{minPoints}", rgData.getMinPoint()).replace("{maxPoints}", rgData.getMaxPoint()));
				counter++;
			}
			
		}
	}


}
