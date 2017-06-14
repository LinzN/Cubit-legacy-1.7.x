package de.linzn.cubit.internal.regionMgr.region;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cubit.plugin.CubitBukkitPlugin;

public class ManageRegions {

	public RegionData newRegion(final int chunkX, final int chunkZ, final World world, final UUID playerUUID,
			final String regionName) {

		final Vector min;
		final Vector max;
		final Vector2D min2D;

		min2D = new Vector2D(chunkX * 16, chunkZ * 16);
		min = new Vector(min2D.getBlockX(), 0, min2D.getBlockZ());
		max = min.add(15, world.getMaxHeight(), 15);
		ProtectedRegion region = new ProtectedCuboidRegion(regionName, min.toBlockVector(), max.toBlockVector());

		if (playerUUID != null) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
			LocalPlayer localplayer = CubitBukkitPlugin.inst().getWorldGuardPlugin().wrapOfflinePlayer(player);
			DefaultDomain domain = new DefaultDomain();
			region.getMembers().clear();
			region.getOwners().clear();
			domain.addPlayer(localplayer);
			region.setOwners(domain);

		}
		RegionData regionData = new RegionData(world);
		regionData.setWGRegion(region);
		return regionData;
	}

	public RegionData removeRegion(RegionData regionData, World world) {
		RegionManager manager = CubitBukkitPlugin.inst().getWorldGuardPlugin().getRegionManager(world);
		manager.removeRegion(regionData.getRegionName());
		return regionData;

	}

}
