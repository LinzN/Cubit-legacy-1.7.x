package de.kekshaus.cubit.api.blockAPI.snapshot;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import de.kekshaus.cubit.plugin.Landplugin;

@SuppressWarnings("deprecation")
public class WorldEditFunctions {

	private Landplugin plugin;
	private String snapshotsDirectory;
	public boolean hasValidAdapter;

	public WorldEditFunctions(Landplugin plugin) {
		this.plugin = plugin;
		this.hasValidAdapter = this.checkWorldEditAdapter();
		this.snapshotsDirectory = this.plugin.getDataFolder() + "/snapshots";
		File dir = new File(snapshotsDirectory);
		if (!dir.exists())
			dir.mkdirs();
	}

	public void save(UUID uuid, Chunk chunk, String snapshotName) {
		if (this.hasValidAdapter) {
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
			for (Entity entity : chunk.getEntities()) {
				if (entity instanceof LivingEntity) {
					moveEntityToTop(entity);
				}

			}
		}
	}

	public void paste(final UUID uuid, final String snapshotName, final Chunk chunk) {
		if (this.hasValidAdapter) {
			final Location pasteLoc = convertChunkLocation(chunk);
			final File snapshotFile = getSnapshotFileLocation(uuid, snapshotName);
			Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
				@Override
				public void run() {
					try {

						EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()),
								Integer.MAX_VALUE);
						editSession.enableQueue();

						SchematicFormat snapshot = SchematicFormat.getFormat(snapshotFile);
						CuboidClipboard clipboard = snapshot.load(snapshotFile);

						clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false, true);
						editSession.flushQueue();
					} catch (MaxChangedBlocksException | DataException | IOException ex) {
						ex.printStackTrace();
					}
					for (Entity entity : chunk.getEntities()) {
						if (entity instanceof LivingEntity) {
							moveEntityToTop(entity);
						}

					}
				}

			});
		}
	}

	public void regenerateChunk(final Chunk chunk) {
		if (this.hasValidAdapter) {

			Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
				@Override
				public void run() {
					chunk.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
					for (Entity entity : chunk.getEntities()) {
						if (entity instanceof LivingEntity) {
							moveEntityToTop(entity);
						}

					}
				}

			});
		}

	}

	public Location convertChunkLocation(Chunk chunk) {
		Location loc = new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16);
		return loc;
	}

	public void removeFile(final UUID uuid, final String snapshotName) {
		if (this.hasValidAdapter) {
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
	}

	public File getSnapshotFileLocation(UUID uuid, String fileName) {
		return new File(getSnapshotDirectoryLocation(uuid, fileName), "snapshot.cubit");
	}

	public File getSnapshotDirectoryLocation(UUID uuid, String snapshotName) {
		String fullPath = this.snapshotsDirectory + "/" + uuid.toString() + "/" + snapshotName;
		File path = new File(fullPath);
		if (!path.exists())
			path.mkdirs();
		return path;
	}

	public boolean isSnapshotDirectory(UUID uuid, String snapshotName) {
		String fullPath = this.snapshotsDirectory + "/" + uuid.toString() + "/" + snapshotName;
		File path = new File(fullPath);
		return path.exists();
	}

	public HashSet<String> getSnapshotNames(UUID playerUUID) {
		HashSet<String> snapshotNames = new HashSet<String>();
		String path = this.snapshotsDirectory + "/" + playerUUID.toString();
		File filePath = new File(path);
		filePath.listFiles();
		for (File file : filePath.listFiles()) {
			if (file.isDirectory()) {
				snapshotNames.add(file.getName());
			}
		}

		return snapshotNames;
	}

	private void moveEntityToTop(Entity entity) {
		double x, z;
		x = entity.getLocation().getX();
		z = entity.getLocation().getZ();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.isFlying()) {
				return;
			}
			player.teleport(new Location(player.getLocation().getWorld(), x,
					player.getLocation().getWorld().getHighestBlockYAt((int) x, (int) z), z));
		} else {
			entity.teleport(new Location(entity.getLocation().getWorld(), x,
					entity.getLocation().getWorld().getHighestBlockYAt((int) x, (int) z), z));
		}

	}

	public boolean checkWorldEditAdapter() {
		String className = "com.sk89q.worldedit.bukkit.adapter.impl.";
		try {
			Class<?> cls = Class.forName(className + "Spigot_" + getVersion());
			if (!BukkitImplAdapter.class.isAssignableFrom(cls)) {
				this.plugin.getLogger().warning("WARN: WorldEdit has no valid bukkit adapter for this server version!");
				this.plugin.getLogger().warning(
						"WARN: All Snapshot actions like /land save, /land restore and /land reset are disabled!");
				this.plugin.getLogger().warning("WARN: Please update your worldedit for full support!");
				return false;
			}
		} catch (ClassNotFoundException e) {
			this.plugin.getLogger().warning("WARN: WorldEdit has no valid bukkit adapter for this server version!");
			this.plugin.getLogger()
					.warning("WARN: All Snapshot actions like /land save or /land restore are disabled!");
			this.plugin.getLogger().warning("WARN: Please update your worldedit for full support!");
			return false;
		}
		return true;
	}

	private static String getVersion() {
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4)
			return array[3];
		return "";
	}

}
