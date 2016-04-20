package de.kekshaus.cookieApi.land.api.regionAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.FirePacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.LockPacket;
import de.kekshaus.cookieApi.land.api.regionAPI.flags.MonsterPacket;
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
	private MonsterPacket monsterPacket;
	private PvPPacket pvpPacket;
	private TNTPacket tntPacket;

	public LandManager(Landplugin plugin) {
		this.plugin = plugin;
		this.mReg = new ManageRegions();
		this.mRegE = new ManageRegionEntities();
		this.saveMrg = new SaveRegions();
		this.firePacket = new FirePacket();
		this.lockPacket = new LockPacket();
		this.monsterPacket = new MonsterPacket();
		this.pvpPacket = new PvPPacket();
		this.tntPacket = new TNTPacket();
	}

	public boolean isLand(final World world, final int valueX, final int valueZ) {

		RegionManager manager = plugin.getWorldGuardPlugin().getRegionManager(world);
		String serverName = buildLandName(LandTypes.SERVER.toString(), valueX, valueZ);
		String shopName = buildLandName(LandTypes.SHOP.toString(), valueX, valueZ);
		String worldName = buildLandName(world.getName(), valueX, valueZ);
		if (manager.hasRegion(serverName)) {
			return true;
		} else if (manager.hasRegion(shopName)) {
			return true;
		} else if (manager.hasRegion(worldName)) {
			return true;
		}
		return false;
	}

	public boolean createLand(final Location loc, final UUID playerUUID, final LandTypes type) {
		switch (type) {
		case SERVER:
			return serverLand(loc);
		case SHOP:
			return shopLand(loc, playerUUID);
		case WORLD:
			return defaultLand(loc, playerUUID);
		default:
			System.err.println("No valid LandType!");
			return false;
		}
	}

	private boolean defaultLand(final Location loc, final UUID playerUUID) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = buildLandName(world.getName(), chunkX, chunkZ);
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, playerUUID, regionName);
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.monsterPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData);
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
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.monsterPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	private boolean shopLand(final Location loc, final UUID playerUUID) {
		try {
			int chunkX = loc.getChunk().getX();
			int chunkZ = loc.getChunk().getZ();
			World world = loc.getWorld();
			String regionName = buildLandName(LandTypes.SHOP.toString(), chunkX, chunkZ);
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, playerUUID, regionName);
			regionData = this.lockPacket.switchState(regionData, true);
			regionData = this.monsterPacket.switchState(regionData, true);
			regionData = this.pvpPacket.switchState(regionData, true);
			regionData = this.tntPacket.switchState(regionData, true);
			regionData = this.firePacket.switchState(regionData, true);
			saveMrg.save(regionData);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean removeLand(final RegionData regionData, final World world) {
		try {
			RegionData emptyRegionData = mReg.removeRegion(regionData, world);
			saveMrg.save(emptyRegionData);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addMember(final RegionData regionData, final World world, final UUID playerUUID) {
		try {

			List<RegionData> list = new ArrayList<RegionData>();
			list.add(regionData);
			mRegE.addMember(list, world, playerUUID);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeMember(final RegionData regionData, final World world, final UUID playerUUID) {
		try {

			List<RegionData> list = new ArrayList<RegionData>();
			list.add(regionData);
			mRegE.removeMember(list, world, playerUUID);
			saveMrg.save(world);
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
			saveMrg.save(world);
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
			saveMrg.save(world);
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
				this.monsterPacket.switchState(regionData);
			} else {
				this.monsterPacket.switchState(regionData, state);
			}
			saveMrg.save(world);
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
			saveMrg.save(world);
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
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getColoredLockState(RegionData regionData) {
		return this.lockPacket.getStateColor(regionData) + this.lockPacket.getPacketName();
	}

	public String getColoredFireState(RegionData regionData) {
		return this.firePacket.getStateColor(regionData) + this.firePacket.getPacketName();
	}

	public String getColoredMonsterState(RegionData regionData) {
		return this.monsterPacket.getStateColor(regionData) + this.monsterPacket.getPacketName();
	}

	public String getColoredPvPState(RegionData regionData) {
		return this.pvpPacket.getStateColor(regionData) + this.pvpPacket.getPacketName();
	}

	public String getColoredTNTState(RegionData regionData) {
		return this.tntPacket.getStateColor(regionData) + this.tntPacket.getPacketName();
	}

	public RegionData praseRegionData(final World world, final int valueX, final int valueZ) {
		ProtectedRegion region = praseWGRegion(world, valueX, valueZ);
		RegionData regionData = new RegionData(world);
		if (region != null) {
			regionData.setWGRegion(region);
		}
		return regionData;
	}

	private ProtectedRegion praseWGRegion(final World world, final int valueX, final int valueZ) {
		RegionManager manager = plugin.getWorldGuardPlugin().getRegionManager(world);
		String serverName = buildLandName(LandTypes.SERVER.toString(), valueX, valueZ);
		String shopName = buildLandName(LandTypes.SHOP.toString(), valueX, valueZ);
		String worldName = buildLandName(world.getName(), valueX, valueZ);
		if (manager.hasRegion(serverName)) {
			return manager.getRegion(serverName);

		} else if (manager.hasRegion(shopName)) {
			return manager.getRegion(shopName);

		} else if (manager.hasRegion(worldName)) {
			return manager.getRegion(worldName);

		}
		return null;
	}

	public String buildLandName(final String type, final int valueX, final int valueZ) {
		return type.toLowerCase() + "_" + valueX + "_" + valueZ;
	}
}
