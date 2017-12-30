package de.linzn.cubit.internal.blockMgr.block;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Chunk;
import org.bukkit.Material;

import java.util.List;

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

	public boolean removeBlockOnShopBuy(Chunk chunk, List<Material> blockList) {
		try {
			if (this.plugin.getYamlManager().getSettings().useShopMaterialCleanup) {
				new ChunkBlockCleaner(plugin, chunk, blockList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
