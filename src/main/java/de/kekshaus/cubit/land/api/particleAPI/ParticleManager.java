package de.kekshaus.cubit.land.api.particleAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.particle.ParticleEffect;

import de.kekshaus.cubit.land.Landplugin;

public class ParticleManager {

	private Landplugin plugin;

	public ParticleManager(Landplugin plugin) {
		this.plugin = plugin;
	}

	private void buildPaticleSpigot(Collection<Player> pList, Location loc, ParticleEffect primaryEffect,
			ParticleEffect secondaryEffect) {
		Chunk chunk = loc.getChunk();
		ArrayList<Location> edgeBlocks = new ArrayList<Location>();
		for (int i = 0; i < 16; i++) {
			for (int ii = -1; ii <= 10; ii++) {
				edgeBlocks.add(chunk.getBlock(i, (int) (loc.getY()) + ii, 15).getLocation());
				edgeBlocks.add(chunk.getBlock(i, (int) (loc.getY()) + ii, 0).getLocation());
				edgeBlocks.add(chunk.getBlock(0, (int) (loc.getY()) + ii, i).getLocation());
				edgeBlocks.add(chunk.getBlock(15, (int) (loc.getY()) + ii, i).getLocation());
			}
		}
		for (Location edgeBlock : edgeBlocks) {
			edgeBlock.setZ(edgeBlock.getBlockZ() + .5);
			edgeBlock.setX(edgeBlock.getBlockX() + .5);
			double x = edgeBlock.getX();
			double y = edgeBlock.getY();
			double z = edgeBlock.getZ();
			if (primaryEffect != null) {
				primaryEffect.send(pList, x, y, z, 0D, 0D, 0D, 0.0001D, 1, 32D);
			}
			if (secondaryEffect != null) {
				secondaryEffect.send(pList, x, y, z, 0D, 0D, 0D, 0.0001D, 1, 32D);
			}
		}
	}

	private void scheduleParticleTask(Collection<Player> pList, Location loc, ParticleEffect primaryEffect,
			ParticleEffect secondaryEffect) {
		int loopValue = 0;
		while (loopValue <= 5) {
			buildPaticleSpigot(pList, loc, primaryEffect, secondaryEffect);
			loopValue++;
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean sendParticle(Player player, final Location loc, final ParticleEffect primaryEffect,
			final ParticleEffect secondaryEffect) {

		final Collection<Player> pList = new HashSet<Player>();
		pList.add(player);
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				scheduleParticleTask(pList, loc, primaryEffect, secondaryEffect);
			}
		});

		return true;
	}

}
