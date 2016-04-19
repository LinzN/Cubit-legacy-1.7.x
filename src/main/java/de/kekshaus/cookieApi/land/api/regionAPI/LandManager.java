package de.kekshaus.cookieApi.land.api.regionAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.FirePacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.LockPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.MobPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.PvPPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.TNTPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.region.LandTypes;
import de.kekshaus.cookieApi.land.api.regionAPI.region.ManageRegionEntities;
import de.kekshaus.cookieApi.land.api.regionAPI.region.ManageRegions;
import de.kekshaus.cookieApi.land.api.regionAPI.region.RegionData;
import de.kekshaus.cookieApi.land.api.regionAPI.region.SaveRegions;

public class LandManager {

	private Landplugin plugin;
	private ManageRegions mReg;
	private ManageRegionEntities mRegE;
	private SaveRegions saveMrg;
	private FirePacket firePacket;
	private LockPacket lockPacket;
	private MobPacket mobPacket;
	private PvPPacket pvpPacket;
	private TNTPacket tntPacket;

	public LandManager(Landplugin plugin) {
		this.plugin = plugin;
		this.mReg = new ManageRegions();
		this.mRegE = new ManageRegionEntities();
		this.saveMrg = new SaveRegions();
		this.firePacket = new FirePacket();
		this.lockPacket = new LockPacket();
		this.mobPacket = new MobPacket();
		this.pvpPacket = new PvPPacket();
		this.tntPacket = new TNTPacket();
	}

	public boolean isLand(final World world, final int valueX, final int valueZ) {
		List<String> types = getAvailableNames();
		RegionManager manager = plugin.getWorldGuardPlugin().getRegionManager(world);

		for (String name : types) {
			String regionName = buildLandName(name, valueX, valueZ);
			if (manager.hasRegion(regionName)) {
				return true;
			}
		}
		return false;
	}

	public boolean createLand(final Location loc, final Player player, final LandTypes type) {
		switch (type) {
		case SERVER:
			return serverLand(loc);
		case SHOP:
			return shopLand(loc, player);
		case WORLD:
			return defaultLand(loc, player);
		default:
			System.err.println("No valid LandType!");
			return false;
		}
	}

	private boolean defaultLand(final Location loc, final Player player) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = buildLandName(world.getName(), chunkX, chunkZ);
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, player, regionName);
			regionData.setRegionState(true);
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.mobPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	private boolean serverLand(final Location loc) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = buildLandName(LandTypes.SERVER.toString(), chunkX, chunkZ);
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, null, regionName);
			regionData.setRegionState(true);
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.mobPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	private boolean shopLand(final Location loc, final Player player) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = buildLandName(LandTypes.SHOP.toString(), chunkX, chunkZ);
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, player, regionName);
			regionData.setRegionState(true);
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.mobPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean removeLand(final RegionData regionData, final World world, final Player player) {
		try {
			RegionData emptyRegionData = mReg.removeRegion(regionData, world);
			saveMrg.save(emptyRegionData, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addMember(final RegionData regionData, final World world, final Player player) {
		try {

			List<RegionData> list = new ArrayList<RegionData>();
			list.add(regionData);
			mRegE.addMember(list, world, player);
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeMember(final RegionData regionData, final World world, final Player player) {
		try {

			List<RegionData> list = new ArrayList<RegionData>();
			list.add(regionData);
			mRegE.removeMember(list, world, player);
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean switchFirePacket(final RegionData regionData, final World world, final boolean state,
			final boolean random) {
		try {
			if (random) {
				this.firePacket.switchState(regionData);
			} else {
				this.firePacket.switchState(regionData, state);
			}
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean switchLockPacket(final RegionData regionData, final World world, final boolean state,
			final boolean random) {
		try {
			if (random) {
				this.lockPacket.switchState(regionData);
			} else {
				this.lockPacket.switchState(regionData, state);
			}
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean switchMobPacket(final RegionData regionData, final World world, final boolean state,
			final boolean random) {
		try {
			if (random) {
				this.mobPacket.switchState(regionData);
			} else {
				this.mobPacket.switchState(regionData, state);
			}
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean switchPvPPacket(final RegionData regionData, final World world, final boolean state,
			final boolean random) {
		try {
			if (random) {
				this.pvpPacket.switchState(regionData);
			} else {
				this.pvpPacket.switchState(regionData, state);
			}
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean switchTNTPacket(final RegionData regionData, final World world, final boolean state,
			final boolean random) {
		try {
			if (random) {
				this.tntPacket.switchState(regionData);
			} else {
				this.tntPacket.switchState(regionData, state);
			}
			saveMrg.save(null, world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private List<String> getAvailableNames() {
		List<String> list = new ArrayList<String>();
		for (LandTypes type : LandTypes.values()) {
			list.add(type.toString());
		}
		for (World world : plugin.getServer().getWorlds()) {
			list.add(world.getName());
		}
		return list;
	}

	public String buildLandName(final String type, final int valueX, final int valueZ) {
		return type.toLowerCase() + "_" + valueX + "_" + valueZ;
	}
}
