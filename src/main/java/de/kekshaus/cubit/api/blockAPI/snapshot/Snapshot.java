package de.kekshaus.cubit.api.blockAPI.snapshot;

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
