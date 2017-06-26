package de.linzn.cubit.internal.landmapMgr;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FakeOfflinePlayer  implements OfflinePlayer {
	String name;

	public FakeOfflinePlayer(String name) {
		this.name = name;
	}

	public Player getPlayer() {
		return null;
	}

	public boolean hasPlayedBefore() {
		return false;
	}

	public String getName() {
		return name;
	}

	public UUID getUniqueId() {
		return null;
	}

	public long getFirstPlayed() {
		return 0;
	}

	public boolean isBanned() {
		return false;
	}

	@Deprecated
	public void setBanned(boolean b) {
		return;
	}

	public Map<String, Object> serialize() {
		return null;
	}

	public boolean isWhitelisted() {
		return true;
	}

	public void setWhitelisted(boolean b) {
		return;
	}

	public Location getBedSpawnLocation() {
		return null;
	}

	public boolean isOnline() {
		return false;
	}

	public long getLastPlayed() {
		return 0;
	}

	public boolean isOp() {
		return false;
	}

	public void setOp(boolean b) {
		return;
	}
}
