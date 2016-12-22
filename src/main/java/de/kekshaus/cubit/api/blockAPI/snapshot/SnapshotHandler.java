package de.kekshaus.cubit.api.blockAPI.snapshot;

import java.util.UUID;

import org.bukkit.Chunk;
import de.kekshaus.cubit.plugin.Landplugin;

public class SnapshotHandler {

	private Landplugin plugin;
	private WorldEditFunctions weFunctions;

	public SnapshotHandler(Landplugin plugin) {
		this.plugin = plugin;
		this.weFunctions = new WorldEditFunctions(this.plugin);
	}

	public boolean restoreSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean removeFile) {
		try {
			this.weFunctions.paste(uuid, snapshotName, chunk);
			if (removeFile) {
				this.weFunctions.removeFile(uuid, snapshotName);
			}
			this.plugin.getBlockManager().getNMSHandler().refreshChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean createSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean regenerateChunk) {
		try {

			this.weFunctions.save(uuid, chunk, snapshotName);

			if (regenerateChunk) {
				this.weFunctions.regenerateChunk(chunk);
			}
			this.plugin.getBlockManager().getNMSHandler().refreshChunk(chunk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
