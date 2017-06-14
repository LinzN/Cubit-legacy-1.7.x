package de.kekshaus.cubit.api.blockAPI;

import de.kekshaus.cubit.api.blockAPI.biome.BiomeHandler;
import de.kekshaus.cubit.api.blockAPI.block.BlockHandler;
import de.kekshaus.cubit.api.blockAPI.nmsPackets.NMSLoader;
import de.kekshaus.cubit.api.blockAPI.snapshot.SnapshotHandler;
import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import de.kekshaus.cubit.plugin.Landplugin;

public class BlockAPIManager {

	private Landplugin plugin;
	private NMSLoader nmsloader;
	private SnapshotHandler snapshotHandler;
	private BiomeHandler biomeHandler;
	private BlockHandler blockHandler;

	public BlockAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading BlockAPIManager");
		this.plugin = plugin;
		this.nmsloader = new NMSLoader(this.plugin);
		this.snapshotHandler = new SnapshotHandler(this.plugin);
		this.biomeHandler = new BiomeHandler(this.plugin);
		this.blockHandler = new BlockHandler(this.plugin);
	}

	public SnapshotHandler getSnapshotHandler() {
		return this.snapshotHandler;
	}

	public BiomeHandler getBiomeHandler() {
		return this.biomeHandler;
	}

	public BlockHandler getBlockHandler() {
		return this.blockHandler;
	}

	public INMSMask getNMSHandler() {
		return this.nmsloader.nmsHandler();
	}

}
