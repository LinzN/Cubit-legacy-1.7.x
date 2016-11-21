package de.kekshaus.cubit.api.regionAPI.region;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cubit.plugin.Landplugin;

public class RegionData {
	private ProtectedRegion region;
	private LandTypes type;
	private World world;

	public RegionData(World world) {
		this.type = LandTypes.NOTYPE;
		this.world = world;

	}

	public void setWGRegion(ProtectedRegion region) {
		this.type = LandTypes.getLandType(region.getId());
		this.region = region;
	}

	public ProtectedRegion praseWGRegion() {
		return this.region;
	}

	public String getMinPoint() {
		return this.region.getMinimumPoint().getBlockX() + ", " + this.region.getMinimumPoint().getBlockZ();
	}

	public String getMaxPoint() {
		return this.region.getMaximumPoint().getBlockX() + ", " + this.region.getMaximumPoint().getBlockZ();
	}

	public UUID getOwnerUUID() {
		UUID playerUUID = null;
		for (UUID uuid : praseWGRegion().getOwners().getUniqueIds()) {
			playerUUID = uuid;
		}
		return playerUUID;
	}
	

	public Set<UUID> getMembersUUID() {
		return praseWGRegion().getMembers().getUniqueIds();
	}


	public String getOwnerName() {
		UUID uuid = getOwnerUUID();
		OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		LocalPlayer wgPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
		String name = wgPlayer.getName();
		if (name == null){
			name = "Undefined";
		}
		return name;
	}

	public Set<String> getMembersName() {
		Set<String> list = new HashSet<String>();
		for (UUID uuid : getMembersUUID()) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
			LocalPlayer wgPlayer = Landplugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
			String name = wgPlayer.getName();
			if (name == null){
				name = "Undefined";
			}
			list.add(name);
		}
		return list;
	}

	public World getWorld() {
		return this.world;
	}

	public LandTypes getLandType() {
		return this.type;
	}

	public String getRegionName() {
		return this.region.getId();
	}

}
