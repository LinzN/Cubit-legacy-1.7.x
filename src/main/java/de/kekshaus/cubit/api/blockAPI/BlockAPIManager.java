package de.kekshaus.cubit.api.blockAPI;

import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import de.kekshaus.cubit.api.blockAPI.biome.ChangeBiome;
import de.kekshaus.cubit.api.blockAPI.border.ChunkBorder;
import de.kekshaus.cubit.api.blockAPI.nmsPackets.NMSLoader;
import de.kekshaus.cubit.api.blockAPI.schematic.SchematicHandler;
import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import de.kekshaus.cubit.plugin.Landplugin;

public class BlockAPIManager {

	private Landplugin plugin;
	private NMSLoader nmsloader;
	private SchematicHandler schematicHandler;;

	public BlockAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading BlockAPIManager");
		this.plugin = plugin;
		this.nmsloader = new NMSLoader(this.plugin);
		this.schematicHandler = new SchematicHandler(this.plugin);
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

	public boolean changeBiomeChunk(Chunk chunk, Biome biome) {

		try {
			new ChangeBiome(plugin, chunk, biome).change();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean pasteSchematic(UUID uuid, Chunk chunk, String regionID, boolean removeFile) {
		try {
			this.schematicHandler.pasteSchematic(uuid, chunk, regionID, removeFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public boolean saveSchematic(UUID uuid, Chunk chunk, String regionID, boolean regenerateChunk) {
		try {
			this.schematicHandler.saveSchematic(uuid, chunk, regionID, regenerateChunk);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public INMSMask getNMSClass() {
		return this.nmsloader.getNMSClass();
	}

}
