package de.linzn.cubit.internal.particleMgr.border;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.inventivetalent.particle.ParticleEffect;

public class ParticleSender {

	private Player player;
	private ArrayList<Location> edgeBlocks;
	private ParticleEffect primaryEffectInventiv;
	private ParticleEffect secondaryEffectInventiv;
	private Effect primaryEffectSpigot;
	private Effect secondaryEffectSpigot;

	public ParticleSender(Player player, Location location, ParticleEffect primaryEffectInventiv,
			ParticleEffect secondaryEffectInventiv) {

		this.player = player;
		this.edgeBlocks = getChunkEdgeLocation(location);
		this.primaryEffectInventiv = primaryEffectInventiv;
		this.secondaryEffectInventiv = secondaryEffectInventiv;

		startInventiv();

	}

	public ParticleSender(Player player, Location location, Effect primaryEffectSpigot, Effect secondaryEffectSpigot) {

		this.player = player;
		this.edgeBlocks = getChunkEdgeLocation(location);
		this.primaryEffectSpigot = primaryEffectSpigot;
		this.secondaryEffectSpigot = secondaryEffectSpigot;
		startSpigot();

	}

	private ArrayList<Location> getChunkEdgeLocation(Location loc) {
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
		return edgeBlocks;
	}

	@SuppressWarnings("deprecation")
	private void sendSpigotParticle() {
		for (Location edgeBlock : this.edgeBlocks) {
			edgeBlock.setZ(edgeBlock.getBlockZ() + .5);
			edgeBlock.setX(edgeBlock.getBlockX() + .5);
			if (this.primaryEffectSpigot != null) {
				this.player.spigot().playEffect(edgeBlock, this.primaryEffectSpigot, 0, 0, 0f, 0f, 0f, 0.001f, 1, 32);
			}
			if (this.secondaryEffectSpigot != null) {
				this.player.spigot().playEffect(edgeBlock, this.secondaryEffectSpigot, 0, 0, 0f, 0f, 0f, 0.001f, 1, 32);
			}
		}
	}

	private void sendInventivParticle() {
		Collection<Player> pList = new HashSet<Player>();
		pList.add(this.player);
		for (Location edgeBlock : this.edgeBlocks) {
			edgeBlock.setZ(edgeBlock.getBlockZ() + .5);
			edgeBlock.setX(edgeBlock.getBlockX() + .5);
			double x = edgeBlock.getX();
			double y = edgeBlock.getY();
			double z = edgeBlock.getZ();
			if (this.primaryEffectInventiv != null) {
				this.primaryEffectInventiv.send(pList, x, y, z, 0D, 0D, 0D, 0.0001D, 1, 32D);
			}
			if (this.secondaryEffectInventiv != null) {
				this.secondaryEffectInventiv.send(pList, x, y, z, 0D, 0D, 0D, 0.0001D, 1, 32D);
			}
		}
	}

	private void startInventiv() {
		int loopValue = 0;
		while (loopValue <= 5) {
			sendInventivParticle();
			loopValue++;
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void startSpigot() {
		int loopValue = 0;
		while (loopValue <= 5) {
			sendSpigotParticle();
			loopValue++;
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
