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

	public void pasteSchematic(UUID uuid, Chunk chunk, String schematicName) {
		this.weFunctions.paste(uuid, schematicName, chunk, false);
	}
	
	public void saveSchematic(UUID uuid, Chunk chunk, String schematicName){
		this.weFunctions.save(uuid, chunk, schematicName);
	}

}
