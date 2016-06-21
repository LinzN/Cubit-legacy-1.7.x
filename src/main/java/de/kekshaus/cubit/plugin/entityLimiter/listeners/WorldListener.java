package de.kekshaus.cubit.plugin.entityLimiter.listeners;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.kekshaus.cubit.plugin.Landplugin;
import de.kekshaus.cubit.plugin.entityLimiter.EntityLimiter;

public class WorldListener implements Listener {

	private final EntityLimiter mrg;
	private Plugin plugin;
	private final HashMap<Chunk, BukkitTask> chunkTasks;

	public WorldListener(EntityLimiter mrg, Plugin plugin) {
		this.mrg = mrg;
		this.plugin = plugin;
		this.chunkTasks = new HashMap<Chunk, BukkitTask>();
	}

	private class InspectTask extends BukkitRunnable {
		private final Chunk chunk;

		public InspectTask(Chunk chunk) {
			this.chunk = chunk;
		}

		@Override
		public void run() {
			mrg.debug("Active check " + chunk.getX() + " " + chunk.getZ());
			if (!chunk.isLoaded()) {
				chunkTasks.remove(chunk);
				this.cancel();
				return;
			}
			mrg.checkChunk(chunk, null);
		}
	}

	@EventHandler
	public void onChunkLoadEvent(final ChunkLoadEvent event) {
		mrg.debug("ChunkLoadEvent " + event.getChunk().getX() + " " + event.getChunk().getZ());
		if (Landplugin.inst().getYamlManager().getLimit().active_inspections) {
			BukkitTask task = new InspectTask(event.getChunk()).runTaskTimer(plugin, 0,
					Landplugin.inst().getYamlManager().getLimit().inspection_frequency * 20L);

			chunkTasks.put(event.getChunk(), task);
		} else if (Landplugin.inst().getYamlManager().getLimit().check_chunk_load) {
			// Active inspection will check immediately as well, no need to
			// check twice
			mrg.checkChunk(event.getChunk(), null);
		}
	}

	@EventHandler
	public void onChunkUnloadEvent(final ChunkUnloadEvent event) {
		mrg.debug("ChunkUnloadEvent " + event.getChunk().getX() + " " + event.getChunk().getZ());

		if (chunkTasks.containsKey(event.getChunk())) {
			chunkTasks.remove(event.getChunk()).cancel();
		}

		if (Landplugin.inst().getYamlManager().getLimit().check_chunk_unload) {
			mrg.checkChunk(event.getChunk(), null);
		}
	}

	public void cancelAllTasks() {
		for (BukkitTask task : chunkTasks.values()) {
			task.cancel();
		}
		chunkTasks.clear();
	}

}
