package de.kekshaus.cubit.api.regionAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cubit.api.regionAPI.flags.FirePacket;
import de.kekshaus.cubit.api.regionAPI.flags.LockPacket;
import de.kekshaus.cubit.api.regionAPI.flags.MonsterPacket;
import de.kekshaus.cubit.api.regionAPI.flags.PotionPacket;
import de.kekshaus.cubit.api.regionAPI.flags.PvPPacket;
import de.kekshaus.cubit.api.regionAPI.flags.TNTPacket;
import de.kekshaus.cubit.api.regionAPI.region.LandTypes;
import de.kekshaus.cubit.api.regionAPI.region.ManageRegionEntities;
import de.kekshaus.cubit.api.regionAPI.region.ManageRegions;
import de.kekshaus.cubit.api.regionAPI.region.RegionData;
import de.kekshaus.cubit.api.regionAPI.region.SaveRegions;
import de.kekshaus.cubit.plugin.Landplugin;

public class RegionAPIManager {

	private Landplugin plugin;
	private ManageRegions mReg;
	private ManageRegionEntities mRegE;
	private SaveRegions saveMrg;
	public FirePacket firePacket;
	public LockPacket lockPacket;
	public MonsterPacket monsterPacket;
	public PvPPacket pvpPacket;
	public TNTPacket tntPacket;
	public PotionPacket potionPacket;

	public RegionAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading RegionAPIManager");
		this.plugin = plugin;
		this.mReg = new ManageRegions();
		this.mRegE = new ManageRegionEntities();
		this.saveMrg = new SaveRegions();
		this.firePacket = new FirePacket();
		this.lockPacket = new LockPacket();
		this.monsterPacket = new MonsterPacket();
		this.pvpPacket = new PvPPacket();
		this.tntPacket = new TNTPacket();
		this.potionPacket = new PotionPacket();
	}

	public boolean isLand(final World world, final int valueX, final int valueZ) {

		RegionManager manager = plugin.getWorldGuardPlugin().getRegionManager(world);
		String serverName = buildLandName(LandTypes.SERVER.toString().toLowerCase(), valueX, valueZ);
		String shopName = buildLandName(LandTypes.SHOP.toString().toLowerCase(), valueX, valueZ);
		String worldName = buildLandName(world.getName().toLowerCase(), valueX, valueZ);
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

			regionData = this.lockPacket.switchState(regionData, true, false);
			regionData = this.monsterPacket.switchState(regionData, true, false);
			regionData = this.pvpPacket.switchState(regionData, true, false);
			regionData = this.tntPacket.switchState(regionData, true, false);
			regionData = this.firePacket.switchState(regionData, true, false);
			regionData = this.potionPacket.switchState(regionData, true, false);
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

			regionData = this.lockPacket.switchState(regionData, true, false);
			regionData = this.monsterPacket.switchState(regionData, true, false);
			regionData = this.pvpPacket.switchState(regionData, true, false);
			regionData = this.tntPacket.switchState(regionData, true, false);
			regionData = this.firePacket.switchState(regionData, true, false);
			regionData = this.potionPacket.switchState(regionData, true, false);
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
			RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, null, regionName);

			regionData = this.lockPacket.switchState(regionData, true, false);
			regionData = this.monsterPacket.switchState(regionData, true, false);
			regionData = this.pvpPacket.switchState(regionData, true, false);
			regionData = this.tntPacket.switchState(regionData, true, false);
			regionData = this.firePacket.switchState(regionData, true, false);
			regionData = this.potionPacket.switchState(regionData, true, false);
			saveMrg.save(regionData);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean restoreDefaultSettings(final RegionData regionData, final World world, final UUID playerUUID) {
		try {

			List<RegionData> list = new ArrayList<>();
			list.add(regionData);
			this.mRegE.clearMember(list, world);
			this.mRegE.clearOwners(list, world);
			if (playerUUID != null) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
				this.mRegE.setOwner(list, world, player);
			}

			this.lockPacket.switchState(regionData, true, false);
			this.monsterPacket.switchState(regionData, true, false);
			this.pvpPacket.switchState(regionData, true, false);
			this.tntPacket.switchState(regionData, true, false);
			this.firePacket.switchState(regionData, true, false);
			this.potionPacket.switchState(regionData, true, false);

			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeLand(final RegionData regionData, final World world) {
		try {
			mReg.removeRegion(regionData, world);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addMember(final RegionData regionData, final World world, final UUID playerUUID) {
		try {
			OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
			List<RegionData> list = new ArrayList<>();
			list.add(regionData);
			mRegE.addMember(list, world, player);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addMemberAll(final UUID ownerUUID, final World world, final UUID playerUUID, final LandTypes type) {
		try {

			List<RegionData> list = new ArrayList<RegionData>();
			OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUUID);
			OfflinePlayer member = Bukkit.getOfflinePlayer(playerUUID);
			for (ProtectedRegion region : mRegE.getRegionList(owner, world, type)) {
				RegionData data = new RegionData(world);
				data.setWGRegion(region);
				list.add(data);
			}
			mRegE.addMember(list, world, member);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeMemberAll(final UUID ownerUUID, final World world, final UUID playerUUID,
			final LandTypes type) {
		try {

			List<RegionData> list = new ArrayList<>();
			OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUUID);
			OfflinePlayer member = Bukkit.getOfflinePlayer(playerUUID);
			for (ProtectedRegion region : mRegE.getRegionList(owner, world, type)) {
				RegionData data = new RegionData(world);
				data.setWGRegion(region);
				list.add(data);
			}
			mRegE.removeMember(list, world, member);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeMember(final RegionData regionData, final World world, final UUID playerUUID) {
		try {
			OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
			List<RegionData> list = new ArrayList<>();
			list.add(regionData);
			mRegE.removeMember(list, world, player);
			saveMrg.save(world);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getStringState(boolean value) {
		String stateString = "INAKTIV";
		if (value) {
			stateString = "AKTIV";
		}
		return stateString;
	}

	public List<RegionData> getAllRegionsFromPlayer(final UUID ownerUUID, final World world, final LandTypes type) {
		List<RegionData> list = new ArrayList<RegionData>();
		try {

			OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUUID);
			for (ProtectedRegion region : mRegE.getRegionList(owner, world, type)) {
				RegionData data = new RegionData(world);
				data.setWGRegion(region);
				list.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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

	public SaveRegions getRegionSaver() {
		return this.saveMrg;
	}

	public boolean hasReachLimit(UUID playerUUID, World world, LandTypes type, int limit) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		List<ProtectedRegion> regions = this.mRegE.getRegionList(player, world, type);
		if (regions.size() >= limit) {
			return true;
		}

		return false;
	}

	public boolean hasLandPermission(final RegionData regionData, final UUID uuid) {
		if (regionData.praseWGRegion().getOwners().getUniqueIds().contains(uuid)) {

			return true;
		}
		return false;

	}
}
