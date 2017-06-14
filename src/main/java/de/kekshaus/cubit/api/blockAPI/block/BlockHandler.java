package de.kekshaus.cubit.api.blockAPI.block;

import org.bukkit.Chunk;
import org.bukkit.Material;
import de.kekshaus.cubit.plugin.Landplugin;

public class BlockHandler {

	private Landplugin plugin;

	public BlockHandler(Landplugin plugin) {
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
