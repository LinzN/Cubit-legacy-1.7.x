package de.linzn.cubit.internal.blockMgr.biome;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;

import de.kekshaus.cubit.plugin.CubitBukkitPlugin;

public class BiomeHandler {

	private CubitBukkitPlugin plugin;

	public BiomeHandler(CubitBukkitPlugin plugin) {
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
