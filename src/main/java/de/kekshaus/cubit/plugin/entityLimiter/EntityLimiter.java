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

import de.kekshaus.cubit.plugin.Landplugin;
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

		// Register our event listeners.
		if (Landplugin.inst().getYamlManager().getLimit().watch_creature_spawns) {
			// Only register events once in the event of a config reload.
			if (entityListener == null) {
				entityListener = new EntityListener(this);
				this.plugin.getServer().getPluginManager().registerEvents(entityListener, this.plugin);
			}
		} else if (entityListener != null) {
			// Disable listeners that are not enabled when configuration is
			// reloaded.
			HandlerList.unregisterAll(entityListener);
			entityListener = null;
		}
		if (Landplugin.inst().getYamlManager().getLimit().active_inspections
				|| Landplugin.inst().getYamlManager().getLimit().check_chunk_load
				|| Landplugin.inst().getYamlManager().getLimit().check_chunk_unload) {
			if (worldListener == null) {
				worldListener = new WorldListener(this, this.plugin);
				this.plugin.getServer().getPluginManager().registerEvents(worldListener, this.plugin);
			}
		} else if (worldListener != null) {
			HandlerList.unregisterAll(worldListener);
			worldListener.cancelAllTasks();
			worldListener = null;
		}

		// Warn if no listeners are enabled.
		if (entityListener == null && worldListener == null) {
			this.plugin.getLogger().severe("No listeners are enabled, the plugin will do nothing!");
			this.plugin.getLogger()
					.severe("Enable creature spawn monitoring, active inspections, or chunk load inspections.");
			this.plugin.getLogger().severe("Edit your configuration and then run '/csl reload'");
		}

		ignoreMetadata = Landplugin.inst().getYamlManager().getLimit().ignore_metadata;
		excludedWorlds = Landplugin.inst().getYamlManager().getLimit().excluded_worlds;

	}

	public boolean checkChunk(Chunk chunk, Entity entity) {
		// Stop processing quickly if this world is excluded from limits.
		if (excludedWorlds.contains(chunk.getWorld().getName())) {
			return false;
		}

		if (entity != null) {
			// Quick return conditions for cancelling new spawns
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
			// Don't include HumanEntities in our summed list at all.
			// They're either Players or plugin-added and we probably shouldn't
			// touch them.
			if (chunkEntity instanceof HumanEntity) {
				continue;
			}

			// Ignore any Entity with listed metadata.
			for (String metadata : ignoreMetadata) {
				if (chunkEntity.hasMetadata(metadata)) {
					continue nextChunkEntity;
				}
			}

			String eType = chunkEntity.getType().name();
			String eGroup = getMobGroup(chunkEntity);

			if (Landplugin.inst().getYamlManager().getLimit().containsEntityGroup(eType)) {
				if (!types.containsKey(eType)) {
					types.put(eType, new ArrayList<Entity>());
				}
				types.get(eType).add(chunkEntity);
			}

			if (Landplugin.inst().getYamlManager().getLimit().containsEntityGroup(eGroup)) {
				if (!types.containsKey(eGroup)) {
					types.put(eGroup, new ArrayList<Entity>());
				}
				types.get(eGroup).add(chunkEntity);
			}
		}

		if (entity != null) {

			String eType = entity.getType().name();

			if (Landplugin.inst().getYamlManager().getLimit().containsEntityGroup(eType)) {
				int typeCount;
				if (types.containsKey(eType)) {
					typeCount = types.get(eType).size() + 1;
				} else {
					typeCount = 1;
				}
				if (typeCount > Landplugin.inst().getYamlManager().getLimit().getEntityGroupValue(eType)) {
					return true;
				}
			}

			String eGroup = getMobGroup(entity);

			if (Landplugin.inst().getYamlManager().getLimit().containsEntityGroup(eGroup)) {
				int typeCount;
				if (types.containsKey(eGroup)) {
					typeCount = types.get(eGroup).size() + 1;
				} else {
					typeCount = 1;
				}
				return typeCount > Landplugin.inst().getYamlManager().getLimit().getEntityGroupValue(eGroup);
			}

		}

		for (Entry<String, ArrayList<Entity>> entry : types.entrySet()) {

			String eType = entry.getKey();
			int limit = Landplugin.inst().getYamlManager().getLimit().getEntityGroupValue(eType);

			if (entry.getValue().size() < limit) {
				continue;
			}

			debug("Removing " + (entry.getValue().size() - limit) + " " + eType + " @ " + chunk.getX() + " "
					+ chunk.getZ());

			boolean skipNamed = Landplugin.inst().getYamlManager().getLimit().preserve_named_entities;
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
		if (Landplugin.inst().getYamlManager().getLimit().plugin_debug) {
			this.plugin.getLogger().info("[Debug] " + mess);
		}
	}

	public static String getMobGroup(Entity entity) {
		// Determine the general group this mob belongs to.
		if (entity instanceof Animals) {
			// Chicken, Cow, MushroomCow, Ocelot, Pig, Sheep, Wolf
			return "ANIMAL";
		}

		if (entity instanceof Monster) {
			// Blaze, CaveSpider, Creeper, Enderman, Giant, PigZombie,
			// Silverfish, Skeleton, Spider,
			// Witch, Wither, Zombie
			return "MONSTER";
		}

		if (entity instanceof Ambient) {
			// Bat
			return "AMBIENT";
		}

		if (entity instanceof WaterMob) {
			// Squid
			return "WATER_MOB";
		}

		if (entity instanceof NPC) {
			// Villager
			return "NPC";
		}

		// Anything else.
		return "OTHER";
	}

}
