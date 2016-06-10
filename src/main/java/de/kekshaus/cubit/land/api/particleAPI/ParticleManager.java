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

	public boolean sendBuy(final Player player, final Location loc) {
		if (this.useInventivLib) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, ParticleEffect.VILLAGER_HAPPY, ParticleEffect.FIREWORKS_SPARK);
				}
			});
		} else {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, Effect.HAPPY_VILLAGER, Effect.FIREWORKS_SPARK);
				}

			});

		}
		return true;
	}

	public boolean sendSell(final Player player, final Location loc) {
		if (this.useInventivLib) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, ParticleEffect.SPELL_WITCH, ParticleEffect.FIREWORKS_SPARK);
				}
			});
		} else {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, Effect.WITCH_MAGIC, Effect.FIREWORKS_SPARK);
				}

			});

		}
		return true;
	}

	public boolean sendInfo(final Player player, final Location loc) {
		if (this.useInventivLib) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, null, ParticleEffect.END_ROD);
				}
			});
		} else {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				@Override
				public void run() {
					new ParticleSender(player, loc, null, Effect.FIREWORKS_SPARK);
				}

			});

		}
		return true;
	}
}
