package de.kekshaus.cubit.api.blockAPI;

import org.bukkit.Chunk;
import org.bukkit.Material;
import de.kekshaus.cubit.api.blockAPI.biome.BiomeHandler;
import de.kekshaus.cubit.api.blockAPI.border.ChunkBorder;
import de.kekshaus.cubit.api.blockAPI.nmsPackets.NMSLoader;
import de.kekshaus.cubit.api.blockAPI.snapshot.SnapshotHandler;
import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import de.kekshaus.cubit.plugin.Landplugin;

public class BlockAPIManager {

	private Landplugin plugin;
	private NMSLoader nmsloader;
	private SnapshotHandler snapshotHandler;
	private BiomeHandler biomeHandler;

	public BlockAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading BlockAPIManager");
		this.plugin = plugin;
		this.nmsloader = new NMSLoader(this.plugin);
		this.snapshotHandler = new SnapshotHandler(this.plugin);
		this.biomeHandler = new BiomeHandler(this.plugin);
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


	
	public SnapshotHandler getSnapshotHandler(){
		return this.snapshotHandler;
	}
	
	public BiomeHandler getBiomeHandler(){
		return this.biomeHandler;
	}

	public INMSMask getNMSHandler() {
		return this.nmsloader.nmsHandler();
	}

}
