/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.blockMgr.snapshot;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SnapshotHandler {

    private CubitBukkitPlugin plugin;
    private WorldEditFunctions weFunctions;

    public SnapshotHandler(CubitBukkitPlugin plugin) {
        this.plugin = plugin;
        this.weFunctions = new WorldEditFunctions(this.plugin);
    }

    public boolean restoreSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean removeFile) {
        try {
            this.weFunctions.paste(uuid, snapshotName, chunk);
            if (removeFile) {
                this.weFunctions.removeFile(uuid, snapshotName);
            }
            this.plugin.getBlockManager().getNMSHandler().refreshChunk(chunk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean createSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean regenerateChunk) {
        try {

            this.weFunctions.save(uuid, chunk, snapshotName);

            if (regenerateChunk) {
                this.weFunctions.regenerateChunk(chunk);
            }
            this.plugin.getBlockManager().getNMSHandler().refreshChunk(chunk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean isSnapshot(UUID uuid, String snapshotName) {
        return this.weFunctions.isSnapshotDirectory(uuid, snapshotName);
    }

    public List<Snapshot> getSnapshots(UUID playerUUID) {
        List<Snapshot> snapshots = new ArrayList<>();
        HashSet<String> names = this.weFunctions.getSnapshotNames(playerUUID);

        for (String snapshotName : names) {
            Snapshot snapshot = new Snapshot(playerUUID, snapshotName);
            snapshots.add(snapshot);
        }

        return snapshots;
    }

    public boolean resetChunk(Chunk chunk) {
        this.weFunctions.regenerateChunk(chunk);
        return true;
    }

    public boolean hasValidAdapter() {
        return this.weFunctions.hasValidAdapter;
    }

}
