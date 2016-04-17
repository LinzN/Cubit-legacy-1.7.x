package de.kekshaus.cookieApi.land.particleAPI.chunk;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.kekshaus.cookieApi.land.Landplugin;

public class SendParticle {

	public static void buildPaticleSpigot(Player p, Location loc, Effect primaryEffect, Effect secondaryEffect,
			int amt) {
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
			if (primaryEffect != null) {
				p.spigot().playEffect(edgeBlock, primaryEffect, 0, 0, 0f, 0f, 0f, 0.001f, amt, 32);
			}
			if (secondaryEffect != null) {
				p.spigot().playEffect(edgeBlock, secondaryEffect, 0, 0, 0f, 0f, 0f, 0.001f, amt, 32);
			}
		}

	}

	public SendParticle(Landplugin plugin, final Player p, final Location l, final Effect primaryEffect,
			final Effect secondaryEffect) {

		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				scheduleParticleTask(p, l, primaryEffect, secondaryEffect);
			}
		});

	}

	public void scheduleParticleTask(final Player p, final Location l, final Effect primaryEffect,
			final Effect secondaryEffect) {
		int loopValue = 0;
		while (loopValue <= 3) {
			buildPaticleSpigot(p, l, primaryEffect, secondaryEffect, 1);
			loopValue++;
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
