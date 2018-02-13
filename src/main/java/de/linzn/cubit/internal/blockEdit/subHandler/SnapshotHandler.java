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

package de.linzn.cubit.internal.blockEdit.subHandler;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.blockEdit.normal.snapshot.Snapshot;
import de.linzn.cubit.internal.blockEdit.normal.snapshot.WorldEditFunctions;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SnapshotHandler {

    private CubitBukkitPlugin plugin;
    private WorldEditFunctions weFunctions;
    private boolean useFAWE;

    public SnapshotHandler(CubitBukkitPlugin plugin, boolean useFAWE) {
        this.plugin = plugin;
        this.useFAWE = useFAWE;
        if (this.useFAWE) {
            // some code
        } else {
            this.weFunctions = new WorldEditFunctions(this.plugin);
        }
    }

    public boolean restoreSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean removeFile) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.restoreSnapshotDefault(uuid, chunk, snapshotName, removeFile);
        }
        return false;
    }

    public boolean createSnapshot(UUID uuid, Chunk chunk, String snapshotName, boolean regenerateChunk) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.createSnapshotDefault(uuid, chunk, snapshotName, regenerateChunk);
        }
        return false;

    }

    public boolean isSnapshot(UUID uuid, String snapshotName) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.isSnapshotDefault(uuid, snapshotName);
        }
        return false;
    }

    public List<Snapshot> getSnapshots(UUID playerUUID) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.getSnapshotsDefault(playerUUID);
        }
        return null;
    }

    public boolean resetChunk(Chunk chunk) {
        if (this.useFAWE) {
            // some code
        } else {
            return this.resetChunkDefault(chunk);
        }
        return false;
    }

    public boolean hasValidAdapter() {
        if (this.useFAWE) {
            // some code
        } else {
            return this.hasValidAdapterDefault();
        }
        return false;
    }

    private boolean restoreSnapshotDefault(UUID uuid, Chunk chunk, String snapshotName, boolean removeFile) {
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

    private boolean createSnapshotDefault(UUID uuid, Chunk chunk, String snapshotName, boolean regenerateChunk) {
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

    private boolean isSnapshotDefault(UUID uuid, String snapshotName) {
        return this.weFunctions.isSnapshotDirectory(uuid, snapshotName);
    }

    private List<Snapshot> getSnapshotsDefault(UUID playerUUID) {
        List<Snapshot> snapshots = new ArrayList<>();
        HashSet<String> names = this.weFunctions.getSnapshotNames(playerUUID);

        for (String snapshotName : names) {
            Snapshot snapshot = new Snapshot(playerUUID, snapshotName);
            snapshots.add(snapshot);
        }

        return snapshots;
    }

    private boolean resetChunkDefault(Chunk chunk) {
        this.weFunctions.regenerateChunk(chunk);
        return true;
    }

    private boolean hasValidAdapterDefault() {
        return this.weFunctions.hasValidAdapter;
    }

}
