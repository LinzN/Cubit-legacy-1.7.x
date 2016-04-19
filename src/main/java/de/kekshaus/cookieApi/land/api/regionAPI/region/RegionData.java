package de.kekshaus.cookieApi.land.api.regionAPI.region;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.guild.api.InternAPI;
import de.kekshaus.cookieApi.guild.objects.Guild;
import de.kekshaus.cookieApi.land.Landplugin;

public class RegionData {
	private String regionID;
	private ProtectedRegion region;
	private LandTypes type;
	private String world;
	private boolean newRegion = false;

	public RegionData(ProtectedRegion region, World world) {
		this.regionID = region.getId();
		this.type = LandTypes.getLandType(region.getId());
		this.world = world.getName();
		this.region = region;

	}

	public ProtectedRegion praseWGRegion() {
		if (newRegion) {
			return this.region;
		}
		return Landplugin.inst().getWorldGuardPlugin().getRegionManager(Bukkit.getWorld(world)).getRegion(regionID);
	}

	public void setRegionState(boolean state) {
		this.newRegion = state;
	}

	public Guild getGuild() {
		Guild guild = InternAPI.getForceGuildPlayer(getOwnerUUID()).getGuild();
		return guild;
	}

	public UUID getOwnerUUID() {
		UUID playerUUID = null;
		for (UUID uuid : Landplugin.inst().getWorldGuardPlugin().getRegionManager(Bukkit.getWorld(world))
				.getRegion(regionID).getOwners().getUniqueIds()) {
			playerUUID = uuid;
		}
		return playerUUID;
	}

	public String getOwnerName() {
		String name = null;
		for (UUID uuid : Landplugin.inst().getWorldGuardPlugin().getRegionManager(Bukkit.getWorld(world))
				.getRegion(regionID).getMembers().getUniqueIds()) {
			name = Bukkit.getServer().getOfflinePlayer(uuid).getName();
		}
		return name;
	}

	public Set<UUID> getMembersUUID() {
		return Landplugin.inst().getWorldGuardPlugin().getRegionManager(Bukkit.getWorld(world)).getRegion(regionID)
				.getMembers().getUniqueIds();
	}

	public Set<String> getMembersName() {
		Set<String> list = new HashSet<String>();
		for (UUID uuid : Landplugin.inst().getWorldGuardPlugin().getRegionManager(Bukkit.getWorld(world))
				.getRegion(regionID).getMembers().getUniqueIds()) {
			list.add(Bukkit.getServer().getOfflinePlayer(uuid).getName());
		}
		return list;
	}

	public LandTypes getLandType() {
		return this.type;
	}

}
