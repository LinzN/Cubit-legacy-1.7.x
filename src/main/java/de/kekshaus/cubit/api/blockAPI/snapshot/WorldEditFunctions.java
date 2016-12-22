package de.kekshaus.cubit.api.blockAPI.snapshot;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import de.kekshaus.cubit.plugin.Landplugin;

@SuppressWarnings("deprecation")
public class WorldEditFunctions {

	private Landplugin plugin;
	private String snapshotsDirectory;

	public WorldEditFunctions(Landplugin plugin) {
		this.plugin = plugin;
		this.snapshotsDirectory = this.plugin.getDataFolder() + "/snapshots";
		File dir = new File(snapshotsDirectory);
		if (!dir.exists())
			dir.mkdirs();
	}

	public void save(UUID uuid, Chunk chunk, String snapshotName) {
		try {

			File schematicFile = getSnapshotFileLocation(uuid, snapshotName);

			EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
					.getEditSession(new BukkitWorld(chunk.getWorld()), 0x3b9ac9ff);
			Vector minPoint = new Vector(chunk.getX() * 16, 0, chunk.getZ() * 16);
			Vector maxPoint = new Vector(chunk.getX() * 16 + 15, 256, chunk.getZ() * 16 + 15);

			editSession.enableQueue();
			CuboidClipboard clipboard = new CuboidClipboard(maxPoint.subtract(minPoint).add(new Vector(1, 1, 1)),
					minPoint);
			clipboard.copy(editSession);
			SchematicFormat.MCEDIT.save(clipboard, schematicFile);
			editSession.flushQueue();

		} catch (DataException | IOException ex) {
			ex.printStackTrace();
		}
	}

	public void paste(final UUID uuid, final String snapshotName, final Chunk chunk) {
		final Location pasteLoc = convertChunkLocation(chunk);
		final File snapshotFile = getSnapshotFileLocation(uuid, snapshotName);
		Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
			@Override
			public void run() {
				try {

					EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
					editSession.enableQueue();

					SchematicFormat snapshot = SchematicFormat.getFormat(snapshotFile);
					CuboidClipboard clipboard = snapshot.load(snapshotFile);

					clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false, true);
					editSession.flushQueue();
				} catch (MaxChangedBlocksException | DataException | IOException ex) {
					ex.printStackTrace();
				}
			}

		});

	}

	public void regenerateChunk(final Chunk chunk) {
		Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
			@Override
			public void run() {
				chunk.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
			}

		});

	}

	public Location convertChunkLocation(Chunk chunk) {
		Location loc = new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16);
		return loc;
	}

	public void removeFile(final UUID uuid, final String snapshotName) {
		final File snapshotDirectory = getSnapshotDirectoryLocation(uuid, snapshotName);
		Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
			@Override
			public void run() {
				try {
					FileUtils.deleteDirectory(snapshotDirectory);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	public File getSnapshotFileLocation(UUID uuid, String fileName) {
		return new File(getSnapshotDirectoryLocation(uuid, fileName), "snapshot.cubit");
	}
	
	public File getSnapshotDirectoryLocation(UUID uuid, String fileName) {
		String fullPath = this.snapshotsDirectory + "/" + uuid.toString() +  "/" + fileName;
		File path = new File(fullPath);
		if (!path.exists())
			path.mkdirs();
		return path;
	}
}
