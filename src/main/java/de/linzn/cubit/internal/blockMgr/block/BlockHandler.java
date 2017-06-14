package de.linzn.cubit.internal.blockMgr.block;

import org.bukkit.Chunk;
import org.bukkit.Material;
import de.kekshaus.cubit.plugin.CubitBukkitPlugin;

public class BlockHandler {

	private CubitBukkitPlugin plugin;

	public BlockHandler(CubitBukkitPlugin plugin) {
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
