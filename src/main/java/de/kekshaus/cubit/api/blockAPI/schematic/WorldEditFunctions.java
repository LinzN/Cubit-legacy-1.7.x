package de.kekshaus.cubit.api.blockAPI.schematic;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
	private String schematicFolder;

	public WorldEditFunctions(Landplugin plugin) {
		this.plugin = plugin;
		this.schematicFolder = this.plugin.getDataFolder() + "/schematics";
		File dir = new File(schematicFolder);
		if (!dir.exists())
			dir.mkdirs();
	}



	public void save(UUID uuid, Chunk chunk, String schematicName) {
		try {
			
			File schematicFile = getSchematicLocation(uuid, schematicName);

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


	public void paste(final UUID uuid, String schematicName, Chunk chunk, Boolean ignoreAir) {
		final Location pasteLoc = convertChunkLocation(chunk);
		if (ignoreAir) {
			try {
				File schematicFile = getSchematicLocation(uuid, schematicName);
				EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
				editSession.enableQueue();

				SchematicFormat schematic = SchematicFormat.getFormat(schematicFile);
				CuboidClipboard clipboard = schematic.load(schematicFile);

				clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), true, true);
				editSession.flushQueue();
			} catch (MaxChangedBlocksException | DataException | IOException ex) {
				ex.printStackTrace();
			}
		} else {
			paste(uuid, schematicName, chunk);
		}
	}

	public void paste(final UUID uuid, final String schematicName, final Chunk chunk) {
		final Location pasteLoc = convertChunkLocation(chunk);
		final File schematicFile = getSchematicLocation(uuid, schematicName);
		Bukkit.getScheduler().runTask(Landplugin.inst(), new Runnable() {
			@Override
			public void run() {
				try {

					EditSession editSession = new EditSession(new BukkitWorld(pasteLoc.getWorld()), Integer.MAX_VALUE);
					editSession.enableQueue();

					SchematicFormat schematic = SchematicFormat.getFormat(schematicFile);
					CuboidClipboard clipboard = schematic.load(schematicFile);

					clipboard.paste(editSession, BukkitUtil.toVector(pasteLoc), false, true);
					editSession.flushQueue();
				} catch (MaxChangedBlocksException | DataException | IOException ex) {
					ex.printStackTrace();
				}
			}

		});

	}

	public Location convertChunkLocation(Chunk chunk) {
		Location loc = new Location(chunk.getWorld(), chunk.getX() * 16, 0, chunk.getZ() * 16);
		return loc;
	}
	
	public File getSchematicLocation(UUID uuid, String fileName){
		String fullPath = this.schematicFolder + "/" + uuid.toString();
		File path = new File(fullPath);
		if (!path.exists())
			path.mkdirs();
		return  new File(fullPath, fileName + ".cubit");
	}
}
