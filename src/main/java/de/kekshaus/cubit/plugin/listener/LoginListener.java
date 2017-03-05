package de.kekshaus.cubit.plugin.listener;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.kekshaus.cubit.plugin.Landplugin;

public class LoginListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerLoginEvent event) {
		Bukkit.getScheduler().runTaskAsynchronously(Landplugin.inst(), new Runnable() {

			@Override
			public void run() {

				Landplugin.inst().getDatabaseManager().updateProfile(event.getPlayer().getUniqueId(),
						event.getPlayer().getName(), new Date().getTime());

			}

		});
	}
	// }
}
