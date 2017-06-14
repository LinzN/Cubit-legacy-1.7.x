package de.kekshaus.cubit.api.blockAPI.biome;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;

import de.kekshaus.cubit.plugin.Landplugin;

public class BiomeHandler {

	private Landplugin plugin;

	public BiomeHandler(Landplugin plugin) {
		this.plugin = plugin;

	}

	public boolean changeBiomeChunk(Chunk chunk, Biome biome) {

		try {
			new ChangeBiome(plugin, chunk, biome).change();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
