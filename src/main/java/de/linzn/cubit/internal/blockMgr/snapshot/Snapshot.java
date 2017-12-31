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

import java.util.UUID;

public class Snapshot {

    private UUID ownerUUID;
    private String snapshotName;

    public Snapshot(UUID ownerUUID, String snapshotName) {
        this.ownerUUID = ownerUUID;
        this.snapshotName = snapshotName;

    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public String getSnapshotId() {
        return this.snapshotName;
    }

}
