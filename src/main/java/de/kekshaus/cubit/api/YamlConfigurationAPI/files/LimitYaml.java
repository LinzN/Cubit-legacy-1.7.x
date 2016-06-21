package de.kekshaus.cubit.api.YamlConfigurationAPI.files;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.CustomConfig;

public class LimitYaml {

	private CustomConfig configFile;
	private String entities = "entities";
	private String spawnreason = "spawn-reasons";

	public boolean watch_creature_spawns;
	public boolean active_inspections;
	public boolean check_chunk_load;
	public boolean check_chunk_unload;
	public boolean preserve_named_entities;
	public boolean prevent_creature_spawns;
	public int inspection_frequency;
	public boolean plugin_debug;

	public List<String> ignore_metadata;
	public List<String> excluded_worlds;

	public LimitYaml(CustomConfig configFile) {
		this.configFile = configFile;
		setup();
		this.configFile.saveAndReload();
	}

	@SuppressWarnings("unchecked")
	public void setup() {
		watch_creature_spawns = (boolean) this.getObjectValue("properties.watch-creature-spawns", true);
		active_inspections = (boolean) this.getObjectValue("properties.active-inspections", true);
		inspection_frequency = (int) this.getObjectValue("properties.inspection-frequency", 300);

		check_chunk_load = (boolean) this.getObjectValue("properties.check-chunk-load", true);
		check_chunk_unload = (boolean) this.getObjectValue("properties.check-chunk-unload", true);
		preserve_named_entities = (boolean) this.getObjectValue("properties.preserve-named-entities", true);
		prevent_creature_spawns = (boolean) this.getObjectValue("properties.prevent-creature-spawns", true);
		plugin_debug = (boolean) this.getObjectValue("properties.debug-messages", true);

		List<String> meta = new ArrayList<String>();
		meta.add("shopkeeper");
		ignore_metadata = (List<String>) this.getStringList("properties.ignore-metadata", meta);

		List<String> worlds = new ArrayList<String>();
		worlds.add("world_nether");
		excluded_worlds = (List<String>) this.getStringList("excluded-worlds", worlds);

		for (SpawnReason reason : SpawnReason.values()) {
			if (!this.configFile.contains(spawnreason + "." + reason.toString())) {
				this.configFile.set(spawnreason + "." + reason.toString(), true);
			}
		}

		if (!this.configFile.contains(entities)) {
			this.configFile.set(entities + ".ANIMAL", 50);
			this.configFile.set(entities + ".MONSTER", 43);
			this.configFile.set(entities + ".AMBIENT", 31);
			this.configFile.set(entities + ".NPC", 66);
			this.configFile.set(entities + ".OTHER", 30);
			this.configFile.set(entities + ".CREEPER", 30);
			this.configFile.set(entities + ".COW", 30);
			this.configFile.set(entities + ".ZOMBIE", 30);
		}

	}

	public Object getObjectValue(String path, Object defaultValue) {
		if (!this.configFile.contains(path)) {
			this.configFile.set(path, defaultValue);
		}
		return this.configFile.get(path);

	}

	public Object getStringList(String path, List<String> list) {
		if (!this.configFile.contains(path)) {
			this.configFile.set(path, list);
		}
		return this.configFile.getStringList(path);

	}

	public boolean containsEntityGroup(String entityName) {
		return this.configFile.contains(entities + "." + entityName);
	}

	public int getEntityGroupValue(String entityName) {
		return this.configFile.getInt(entities + "." + entityName);
	}

	public boolean getSpawnreasonValue(String reason) {
		return (boolean) getObjectValue(spawnreason + "." + reason, false);
	}

	public CustomConfig getFile() {
		return this.configFile;
	}

}
