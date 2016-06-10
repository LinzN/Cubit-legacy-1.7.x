package de.kekshaus.cubit.land.api.particleAPI;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.particle.ParticleEffect;

import de.kekshaus.cubit.land.Landplugin;

public class ParticleManager {

	private Landplugin plugin;
	private boolean useInventivLib;

	public ParticleManager(Landplugin plugin, boolean useInventivLib) {
		this.plugin = plugin;
		this.useInventivLib = useInventivLib;
	}

	public boolean sendParticle(final Player player, final Location loc, final ParticleEffect primaryEffectInventiv,
			final ParticleEffect secondaryEffectInventiv, final Effect primaryEffectSpigot,
			final Effect secondaryEffectSpigot) {
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				new ParticleSender(useInventivLib, player, loc, primaryEffectInventiv, secondaryEffectInventiv,
						primaryEffectSpigot, secondaryEffectSpigot);
			}
		});

		return true;
	}

}
