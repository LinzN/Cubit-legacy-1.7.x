package de.kekshaus.cubit.api.blockAPI;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import de.kekshaus.cubit.api.blockAPI.biome.ChangeBiome;
import de.kekshaus.cubit.api.blockAPI.border.ChunkBorder;
import de.kekshaus.cubit.plugin.Landplugin;

public class BlockAPIManager {

	private Landplugin plugin;

	public BlockAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading BlockAPIManager");
		this.plugin = plugin;
	}

	public boolean placeLandBorder(Chunk chunk, Material material) {
		try {
			new ChunkBorder(plugin, chunk, material);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean changeBiomeChunk(Chunk chunk, Biome biome){
		
		try {
			new ChangeBiome(plugin, chunk, biome);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
