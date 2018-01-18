/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.internal.entityMgr.listeners;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.entityMgr.EntityManager;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class WorldListener implements Listener {

    private final EntityManager mrg;
    private final HashMap<Chunk, BukkitTask> chunkTasks;
    private Plugin plugin;

    public WorldListener(EntityManager mrg, Plugin plugin) {
        this.mrg = mrg;
        this.plugin = plugin;
        this.chunkTasks = new HashMap<>();
    }

    @EventHandler
    public void onChunkLoadEvent(final ChunkLoadEvent event) {
        mrg.debug("ChunkLoadEvent " + event.getChunk().getX() + " " + event.getChunk().getZ());
        if (CubitBukkitPlugin.inst().getYamlManager().getLimit().active_inspections) {
            BukkitTask task = new InspectTask(event.getChunk()).runTaskTimer(plugin, 0,
                    CubitBukkitPlugin.inst().getYamlManager().getLimit().inspection_frequency * 20L);

            chunkTasks.put(event.getChunk(), task);
        } else if (CubitBukkitPlugin.inst().getYamlManager().getLimit().check_chunk_load) {

            mrg.checkChunk(event.getChunk(), null);
        }
    }

    @EventHandler
    public void onChunkUnloadEvent(final ChunkUnloadEvent event) {
        mrg.debug("ChunkUnloadEvent " + event.getChunk().getX() + " " + event.getChunk().getZ());

        if (chunkTasks.containsKey(event.getChunk())) {
            chunkTasks.remove(event.getChunk()).cancel();
        }

        if (CubitBukkitPlugin.inst().getYamlManager().getLimit().check_chunk_unload) {
            mrg.checkChunk(event.getChunk(), null);
        }
    }

    public void cancelAllTasks() {
        for (BukkitTask task : chunkTasks.values()) {
            task.cancel();
        }
        chunkTasks.clear();
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

}
