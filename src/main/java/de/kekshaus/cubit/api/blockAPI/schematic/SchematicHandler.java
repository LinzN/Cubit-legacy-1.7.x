package de.kekshaus.cubit.api.blockAPI.schematic;

import java.util.UUID;

import org.bukkit.Chunk;
import de.kekshaus.cubit.plugin.Landplugin;

public class SchematicHandler {

	private Landplugin plugin;
	private WorldEditFunctions weFunctions;

	public SchematicHandler(Landplugin plugin) {
		this.plugin = plugin;
		this.weFunctions = new WorldEditFunctions(this.plugin);
	}

	public void pasteSchematic(UUID uuid, Chunk chunk, String schematicName, boolean removeFile) {
		this.weFunctions.paste(uuid, schematicName, chunk);
		if (removeFile){
			this.weFunctions.removeFile(uuid, schematicName);
		}
		this.plugin.getBlockManager().getNMSClass().refreshChunk(chunk);
	}
	
	public void saveSchematic(UUID uuid, Chunk chunk, String schematicName, boolean regenerateChunk){
		this.weFunctions.save(uuid, chunk, schematicName);
		
		if (regenerateChunk){
			this.weFunctions.regenerateChunk(chunk);
		}
		this.plugin.getBlockManager().getNMSClass().refreshChunk(chunk);
	}

}
