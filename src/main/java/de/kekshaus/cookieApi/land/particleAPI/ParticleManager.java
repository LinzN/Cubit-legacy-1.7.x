package de.kekshaus.cookieApi.land.particleAPI;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.particleAPI.chunk.SendParticle;

public class ParticleManager {

	private Landplugin plugin;

	public ParticleManager(Landplugin plugin) {
		this.plugin = plugin;
	}

	public boolean sendDefaultPaticle(Player player, Location loc) {
		try {
			new SendParticle(plugin, player, loc, null, Effect.FIREWORKS_SPARK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
