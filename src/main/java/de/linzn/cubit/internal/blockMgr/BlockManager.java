package de.linzn.cubit.internal.blockMgr;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.blockMgr.biome.BiomeHandler;
import de.linzn.cubit.internal.blockMgr.block.BlockHandler;
import de.linzn.cubit.internal.blockMgr.nmsPackets.NMSLoader;
import de.linzn.cubit.internal.blockMgr.snapshot.SnapshotHandler;

public class BlockManager {

	private CubitBukkitPlugin plugin;
	private NMSLoader nmsloader;
	private SnapshotHandler snapshotHandler;
	private BiomeHandler biomeHandler;
	private BlockHandler blockHandler;

	public BlockManager(CubitBukkitPlugin plugin) {
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
