package de.linzn.cubit.internal.entityMgr.listeners;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.entityMgr.EntityManager;

public class EntityListener implements Listener {

	private final EntityManager plugin;

	public EntityListener(EntityManager plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true)
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {

		String reason = event.getSpawnReason().toString();

		if (!CubitBukkitPlugin.inst().getYamlManager().getLimit().getSpawnreasonValue(reason)) {
			plugin.debug("Ignoring " + event.getEntity().getType().toString() + " due to spawnreason " + reason);
			return;
		}

		Chunk chunk = event.getLocation().getChunk();

		if (CubitBukkitPlugin.inst().getYamlManager().getLimit().prevent_creature_spawns) {
			if (plugin.checkChunk(chunk, event.getEntity())) {
				event.setCancelled(true);
			}
			return;
		}

		int x = chunk.getX();
		int z = chunk.getZ();
		int endX = chunk.getX() + 1;
		int endZ = chunk.getZ() + 1;

		World w = event.getLocation().getWorld();
		for (; x < endX; x++) {
			for (; z < endZ; z++) {
				if (!w.isChunkLoaded(x, z)) {
					continue;
				}
				plugin.checkChunk(w.getChunkAt(x, z), null);
			}
		}
	}

}
