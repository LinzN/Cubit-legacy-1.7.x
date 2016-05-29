package de.kekshaus.cubit.land.api.blockAPI;

import org.bukkit.Chunk;
import org.bukkit.Material;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.blockAPI.border.ChunkBorder;

public class BlockManager {

	private Landplugin plugin;

	public BlockManager(Landplugin plugin) {
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

}
