package de.kekshaus.cookieApi.land.regionAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.regionAPI.flags.FirePacket;
import de.kekshaus.cookieApi.land.regionAPI.flags.LockPacket;
import de.kekshaus.cookieApi.land.regionAPI.flags.MobPacket;
import de.kekshaus.cookieApi.land.regionAPI.flags.PvPPacket;
import de.kekshaus.cookieApi.land.regionAPI.flags.TNTPacket;

public class LandManager {

	private Landplugin plugin;

	public LandManager(Landplugin plugin) {
		this.plugin = plugin;
	}

	public boolean isRegion(World world, int valueX, int valueZ) {
		List<String> types = getRegionTypes();
		RegionManager manager = plugin.getWorldGuardPlugin().getRegionManager(world);

		for (String name : types) {
			String regionName = buildRegionName(name, valueX, valueZ);
			if (manager.hasRegion(regionName)) {
				return true;
			}
		}
		return false;
	}

	public boolean newCubitLand(final Location loc, final Player player) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = "";
			ProtectedRegion region = CookieRegion.newRegion(chunkX, chunkZ, world, player, regionName);
			region = LockPacket.activateData(region);
			region = MobPacket.activateData(region);
			region = PvPPacket.activateData(region);
			region = TNTPacket.activateData(region);
			region = FirePacket.activateData(region);
			new SaveManager(region, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean removeCubitLand(final ProtectedRegion region, final World world, final Player player) {
		try {
			CookieRegion.removeRegion(region, world);
			new SaveManager(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private List<String> getRegionTypes() {
		List<String> list = new ArrayList<String>();
		list.add("server");
		for (World world : plugin.getServer().getWorlds()) {
			list.add(world.getName());
		}
		return list;
	}

	public String buildRegionName(String type, int valueX, int valueZ) {
		return type.toLowerCase() + "_" + valueX + "_" + valueZ;
	}
}
