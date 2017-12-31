/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.regionMgr;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.cache.NameCache;
import de.linzn.cubit.internal.regionMgr.flags.*;
import de.linzn.cubit.internal.regionMgr.region.ManageRegionEntities;
import de.linzn.cubit.internal.regionMgr.region.ManageRegions;
import de.linzn.cubit.internal.regionMgr.region.RegionData;
import de.linzn.cubit.internal.regionMgr.region.SaveRegions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CubitregionManager {

    public FirePacket firePacket;
    public LockPacket lockPacket;
    public MonsterPacket monsterPacket;
    public PvPPacket pvpPacket;
    public TNTPacket tntPacket;
    public PotionPacket potionPacket;
    private CubitBukkitPlugin plugin;
    private ManageRegions mReg;
    private ManageRegionEntities mRegE;
    private SaveRegions saveMrg;
    private NameCache nameCache;

    public CubitregionManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("Loading CubitregionManager");
        this.plugin = plugin;
        this.nameCache = new NameCache();
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

    public boolean isValidRegion(final World world, final int valueX, final int valueZ) {

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

    public boolean createRegion(final Location loc, final UUID playerUUID, final LandTypes type) {

        switch (type) {
            case SERVER:
                return serverRegion(loc);
            case SHOP:
                return shopRegion(loc, playerUUID);
            case WORLD:
                return worldRegion(loc, playerUUID);
            default:
                System.err.println("No valid LandType!");
                return false;
        }
    }

    private boolean worldRegion(final Location loc, final UUID playerUUID) {
        try {
            int chunkX = loc.getChunk().getX();
            int chunkZ = loc.getChunk().getZ();
            World world = loc.getWorld();
            String regionName = buildLandName(world.getName(), chunkX, chunkZ);
            RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, playerUUID, regionName);

            regionData = this.lockPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketLock, false);
            regionData = this.monsterPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketMonster, false);
            regionData = this.pvpPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketPVP, false);
            regionData = this.tntPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketTNT, false);
            regionData = this.firePacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketFire, false);
            regionData = this.potionPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().worldRegionPacketPotion, false);
            saveMrg.save(regionData);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private boolean serverRegion(final Location loc) {
        try {
            int chunkX = loc.getChunk().getX();
            int chunkZ = loc.getChunk().getZ();
            World world = loc.getWorld();
            String regionName = buildLandName(LandTypes.SERVER.toString(), chunkX, chunkZ);
            RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, null, regionName);

            regionData = this.lockPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketLock, false);
            regionData = this.monsterPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketMonster, false);
            regionData = this.pvpPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketPVP, false);
            regionData = this.tntPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketTNT, false);
            regionData = this.firePacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketFire, false);
            regionData = this.potionPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().serverRegionPacketPotion, false);
            saveMrg.save(regionData);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private boolean shopRegion(final Location loc, final UUID playerUUID) {
        try {
            int chunkX = loc.getChunk().getX();
            int chunkZ = loc.getChunk().getZ();
            World world = loc.getWorld();
            String regionName = buildLandName(LandTypes.SHOP.toString(), chunkX, chunkZ);
            RegionData regionData = mReg.newRegion(chunkX, chunkZ, world, null, regionName);

            regionData = this.lockPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketLock, false);
            regionData = this.monsterPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketMonster, false);
            regionData = this.pvpPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketPVP, false);
            regionData = this.tntPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketTNT, false);
            regionData = this.firePacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketFire, false);
            regionData = this.potionPacket.switchState(regionData,
                    this.plugin.getYamlManager().getFlag().shopRegionPacketPotion, false);
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

            this.lockPacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketLock,
                    false);
            this.monsterPacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketMonster,
                    false);
            this.pvpPacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketPVP, false);
            this.tntPacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketTNT, false);
            this.firePacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketFire,
                    false);
            this.potionPacket.switchState(regionData, this.plugin.getYamlManager().getFlag().worldRegionPacketPotion,
                    false);

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

            List<RegionData> list = new ArrayList<>();
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
        String stateString = plugin.getYamlManager().getLanguage().flagStateInactive;
        if (value) {
            stateString = plugin.getYamlManager().getLanguage().flagStateActive;
        }
        return stateString;
    }

    public List<RegionData> getAllRegionsFromPlayer(final UUID searchUUID, final World world, final LandTypes type) {
        List<RegionData> list = new ArrayList<>();
        try {

            OfflinePlayer owner = Bukkit.getOfflinePlayer(searchUUID);
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
        return regions.size() >= limit;

    }

    public boolean hasLandPermission(final RegionData regionData, final UUID uuid) {
        return regionData.praseWGRegion().getOwners().getUniqueIds().contains(uuid);

    }

    public boolean isToLongOffline(final UUID uuid, final boolean isMember) {
        long currentTimeStamp = new Date().getTime();

        long lastLogin = plugin.getDataAccessManager().databaseType.get_last_login_profile(uuid);
        long landDeprecated = (long) (this.plugin.getYamlManager().getSettings().landDeprecatedOther * 24 * 60 * 60
                * 1000);

        if (isMember) {
            landDeprecated = (long) (this.plugin.getYamlManager().getSettings().landDeprecatedMember * 24 * 60 * 60
                    * 1000);
        }

        return currentTimeStamp - lastLogin >= landDeprecated;
    }

    public List<String> getPlayerNames(UUID[] playerUUIDs) {
        List<String> playerNames = new ArrayList<>();
        for (UUID playerUUID : playerUUIDs) {
            playerNames.add(nameCache.getCacheName(playerUUID));
        }

        return playerNames;
    }

    public String getPlayerName(UUID playerUUID) {
        return nameCache.getCacheName(playerUUID);
    }

}
