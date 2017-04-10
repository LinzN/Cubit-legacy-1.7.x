package de.kekshaus.cubit.api.blockAPI.snapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	public boolean isSnapshot(UUID uuid, String snapshotName) {
		return this.weFunctions.isSnapshotDirectory(uuid, snapshotName);
	}

	public List<Snapshot> getSnapshots(UUID playerUUID) {
		List<Snapshot> snapshots = new ArrayList<Snapshot>();
		HashSet<String> names = this.weFunctions.getSnapshotNames(playerUUID);

		for (String snapshotName : names) {
			Snapshot snapshot = new Snapshot(playerUUID, snapshotName);
			snapshots.add(snapshot);
		}

		return snapshots;
	}
	
	public boolean resetChunk(Chunk chunk){
		this.weFunctions.regenerateChunk(chunk);
		return true;
	}

	public boolean hasValidAdapter() {
		return this.weFunctions.hasValidAdapter;
	}

}
