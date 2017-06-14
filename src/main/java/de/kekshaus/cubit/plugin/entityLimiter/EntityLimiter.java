package de.kekshaus.cubit.plugin.entityLimiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Chunk;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.NPC;
import org.bukkit.entity.WaterMob;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import de.kekshaus.cubit.plugin.CubitBukkitPlugin;
import de.kekshaus.cubit.plugin.entityLimiter.listeners.EntityListener;
import de.kekshaus.cubit.plugin.entityLimiter.listeners.WorldListener;

public class EntityLimiter {

	private List<String> ignoreMetadata, excludedWorlds;
	private EntityListener entityListener;
	private WorldListener worldListener;
	private Plugin plugin;

	public EntityLimiter(Plugin plugin) {
		this.plugin = plugin;
		loadLimiter();
	}

	public void loadLimiter() {

		if (CubitBukkitPlugin.inst().getYamlManager().getLimit().watch_creature_spawns) {
			if (entityListener == null) {
				entityListener = new EntityListener(this);
				this.plugin.getServer().getPluginManager().registerEvents(entityListener, this.plugin);
			}
		} else if (entityListener != null) {
			HandlerList.unregisterAll(entityListener);
			entityListener = null;
		}
		if (CubitBukkitPlugin.inst().getYamlManager().getLimit().active_inspections
				|| CubitBukkitPlugin.inst().getYamlManager().getLimit().check_chunk_load
				|| CubitBukkitPlugin.inst().getYamlManager().getLimit().check_chunk_unload) {
			if (worldListener == null) {
				worldListener = new WorldListener(this, this.plugin);
				this.plugin.getServer().getPluginManager().registerEvents(worldListener, this.plugin);
			}
		} else if (worldListener != null) {
			HandlerList.unregisterAll(worldListener);
			worldListener.cancelAllTasks();
			worldListener = null;
		}

		if (entityListener == null && worldListener == null) {
			this.plugin.getLogger().severe("No spawnreasons are enabled, the entityLimiter will do nothing!");
		}

		ignoreMetadata = CubitBukkitPlugin.inst().getYamlManager().getLimit().ignore_metadata;
		excludedWorlds = CubitBukkitPlugin.inst().getYamlManager().getLimit().excluded_worlds;

	}

	public boolean checkChunk(Chunk chunk, Entity entity) {

		if (excludedWorlds.contains(chunk.getWorld().getName())) {
			return false;
		}

		if (entity != null) {

			if (entity instanceof HumanEntity) {
				return false;
			}

			for (String metadata : ignoreMetadata) {
				if (entity.hasMetadata(metadata)) {
					return false;
				}
			}
		}

		Entity[] entities = chunk.getEntities();
		HashMap<String, ArrayList<Entity>> types = new HashMap<String, ArrayList<Entity>>();

		nextChunkEntity: for (int i = entities.length - 1; i >= 0; i--) {
			Entity chunkEntity = entities[i];
			if (chunkEntity instanceof HumanEntity) {
				continue;
			}

			for (String metadata : ignoreMetadata) {
				if (chunkEntity.hasMetadata(metadata)) {
					continue nextChunkEntity;
				}
			}

			String eType = chunkEntity.getType().name();
			String eGroup = getMobGroup(chunkEntity);

			if (CubitBukkitPlugin.inst().getYamlManager().getLimit().containsEntityGroup(eType)) {
				if (!types.containsKey(eType)) {
					types.put(eType, new ArrayList<Entity>());
				}
				types.get(eType).add(chunkEntity);
			}

			if (CubitBukkitPlugin.inst().getYamlManager().getLimit().containsEntityGroup(eGroup)) {
				if (!types.containsKey(eGroup)) {
					types.put(eGroup, new ArrayList<Entity>());
				}
				types.get(eGroup).add(chunkEntity);
			}
		}

		if (entity != null) {

			String eType = entity.getType().name();

			if (CubitBukkitPlugin.inst().getYamlManager().getLimit().containsEntityGroup(eType)) {
				int typeCount;
				if (types.containsKey(eType)) {
					typeCount = types.get(eType).size() + 1;
				} else {
					typeCount = 1;
				}
				if (typeCount > CubitBukkitPlugin.inst().getYamlManager().getLimit().getEntityGroupValue(eType)) {
					return true;
				}
			}

			String eGroup = getMobGroup(entity);

			if (CubitBukkitPlugin.inst().getYamlManager().getLimit().containsEntityGroup(eGroup)) {
				int typeCount;
				if (types.containsKey(eGroup)) {
					typeCount = types.get(eGroup).size() + 1;
				} else {
					typeCount = 1;
				}
				return typeCount > CubitBukkitPlugin.inst().getYamlManager().getLimit().getEntityGroupValue(eGroup);
			}

		}

		for (Entry<String, ArrayList<Entity>> entry : types.entrySet()) {

			String eType = entry.getKey();
			int limit = CubitBukkitPlugin.inst().getYamlManager().getLimit().getEntityGroupValue(eType);

			if (entry.getValue().size() < limit) {
				continue;
			}

			debug("Removing " + (entry.getValue().size() - limit) + " " + eType + " @ " + chunk.getX() + " "
					+ chunk.getZ());

			boolean skipNamed = CubitBukkitPlugin.inst().getYamlManager().getLimit().preserve_named_entities;
			int toRemove = entry.getValue().size() - limit;
			int index = entry.getValue().size() - 1;
			while (toRemove > 0 && index >= 0) {
				Entity toCheck = entry.getValue().get(index);
				if (!skipNamed || toCheck.getCustomName() == null
						|| toCheck instanceof LivingEntity && ((LivingEntity) toCheck).getRemoveWhenFarAway()) {
					toCheck.remove();
					--toRemove;
				}
				--index;
			}
			if (toRemove == 0) {
				continue;
			}
			index = entry.getValue().size() - toRemove - 1;
			for (; index < entry.getValue().size(); index++) {
				entry.getValue().get(index).remove();
			}
		}

		return false;
	}

	public void debug(String mess) {
		if (CubitBukkitPlugin.inst().getYamlManager().getLimit().plugin_debug) {
			this.plugin.getLogger().info("Debug: " + mess);
		}
	}

	public static String getMobGroup(Entity entity) {
		if (entity instanceof Animals) {
			return "ANIMAL";
		}

		if (entity instanceof Monster) {
			return "MONSTER";
		}

		if (entity instanceof Ambient) {
			return "AMBIENT";
		}

		if (entity instanceof WaterMob) {
			return "WATER_MOB";
		}

		if (entity instanceof NPC) {
			return "NPC";
		}
		return "OTHER";
	}

}
