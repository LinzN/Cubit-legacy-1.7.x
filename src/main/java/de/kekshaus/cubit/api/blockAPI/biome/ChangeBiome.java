package de.kekshaus.cubit.api.blockAPI.biome;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.plugin.Plugin;

import de.kekshaus.cubit.plugin.Landplugin;

public class ChangeBiome {

	private Plugin plugin;
	private Chunk chunk;
	private Biome biome;

	public ChangeBiome(Plugin plugin, Chunk chunk, Biome biome) {
		this.plugin = plugin;
		this.chunk = chunk;
		this.biome = biome;
	}

	public void change() {
		final int chunkX = this.chunk.getX() * 16;
		final int cZ = this.chunk.getZ() * 16;
		final World world = this.chunk.getWorld();

		for (int x = 0; x < 16; x++) {

			final int cordX = x + chunkX;

			Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
				public void run() {
					for (int z = 0; z < 16; z++) {
						world.setBiome(cordX, cZ + z, biome);
					}
				}

			});

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Landplugin.inst().getBlockManager().getNMSClass().refreshChunk(chunk);
	}

}
