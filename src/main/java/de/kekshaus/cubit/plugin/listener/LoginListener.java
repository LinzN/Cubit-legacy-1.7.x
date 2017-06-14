package de.kekshaus.cubit.plugin.listener;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.kekshaus.cubit.plugin.CubitBukkitPlugin;

public class LoginListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerLoginEvent event) {
		Bukkit.getScheduler().runTaskAsynchronously(CubitBukkitPlugin.inst(), new Runnable() {

			@Override
			public void run() {

				CubitBukkitPlugin.inst().getDatabaseManager().updateProfile(event.getPlayer().getUniqueId(),
						event.getPlayer().getName(), new Date().getTime());
				if (event.getPlayer().hasPermission(CubitBukkitPlugin.inst().getPermNodes().checkUpdateAdmin)) {
					if (CubitBukkitPlugin.inst().getSpigetCheck().isAvailable) {
						event.getPlayer()
								.sendMessage(ChatColor.GREEN
										+ "A new update for Cubit is avaiable. Check out the new version "
										+ CubitBukkitPlugin.inst().getSpigetCheck().version + "!");
					}
				}

			}

		});
	}
	// }
}
