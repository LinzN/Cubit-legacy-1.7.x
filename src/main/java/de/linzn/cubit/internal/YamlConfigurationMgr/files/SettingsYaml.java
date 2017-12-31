/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.cubit.internal.YamlConfigurationMgr.files;

import de.linzn.cubit.internal.YamlConfigurationMgr.setup.CustomConfig;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettingsYaml {

    public List<String> landEnabledWorlds;

	/* Public config values */

    /* Land module */
    public boolean landEnableEconomy;
    public double landBasePrice;
    public double landTaxAddition;
    public double landMaxPrice;
    public double landSellPercent;
    public double landChangeBiomePrice;
    public boolean landUseSnapshots;
    public double landSaveSnapshotPrice;
    public double landRestoreSnapshotPrice;
    public double landResetSnapshotPrice;
    public double landDeprecatedMember;
    public double landDeprecatedOther;
    public boolean landUseMaterialBorder;
    public Material landBuyMaterialBorder;
    public Material landSellMaterialBorder;
    public boolean landUseScoreboardMap;
    public List<String> shopEnabledWorlds;

    /* Shop module */
    public double shopBasePrice;
    public int shopLimit;
    public boolean useShopMaterialCleanup;
    public List<Material> shopMaterialCleanup;
    /* Particle module */
    public boolean particleUse;
    public boolean entityLimiterUse;
    public boolean physicWaterLavaFlowLand;
    /* Database module */
    public boolean sqlUse;
    public String sqlDataBase;
    public String sqlHostname;
    public int sqlPort;
    public String sqlUser;
    public String sqlPassword;
    public boolean updateCheck;
    public long cubitSetupDate;
    private CustomConfig configFile;

    public SettingsYaml(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }

    @SuppressWarnings("unchecked")
    private void setup() {
        List<String> landEnabledWorlds = new ArrayList<>();
        landEnabledWorlds.add("world");
        this.landEnabledWorlds = (List<String>) this.getStringList("module.land.enabledWorlds", landEnabledWorlds);
        this.landEnableEconomy = (boolean) this.getObjectValue("module.land.enableEconomy", true);
        this.landBasePrice = (double) this.getObjectValue("module.land.basePrice", 235D);
        this.landTaxAddition = (double) this.getObjectValue("module.land.taxAddition", 5D);
        this.landMaxPrice = (double) this.getObjectValue("module.land.maxPrice", 750D);
        this.landSellPercent = (double) this.getObjectValue("module.land.sellPercentInDecimal", 0.4D);
        this.landChangeBiomePrice = (double) this.getObjectValue("module.land.landChangeBiomePrice", 25D);
        this.landUseSnapshots = (boolean) this.getObjectValue("module.land.landUseSnapshots", true);
        this.landSaveSnapshotPrice = (double) this.getObjectValue("module.land.landSaveSnapshot", 350D);
        this.landRestoreSnapshotPrice = (double) this.getObjectValue("module.land.landRestoreSnapshot", 150D);
        this.landResetSnapshotPrice = (double) this.getObjectValue("module.land.landResetSnapshotPrice", 50D);
        this.landDeprecatedMember = (double) this.getObjectValue("module.land.deprecatedBuyupMember", 35D);
        this.landDeprecatedOther = (double) this.getObjectValue("module.land.deprecatedBuyupOther", 45D);
        this.landUseMaterialBorder = (boolean) this.getObjectValue("module.land.useMaterialBorder", true);
        this.landBuyMaterialBorder = Material
                .valueOf((String) this.getObjectValue("module.land.buyMaterialBorder", Material.TORCH.toString()));
        this.landSellMaterialBorder = Material.valueOf(
                (String) this.getObjectValue("module.land.sellMaterialBorder", Material.REDSTONE_TORCH_ON.toString()));
        this.landUseScoreboardMap = (boolean) this.getObjectValue("module.land.useScoreboardMap", false);

        List<String> shopsEnabledWorlds = new ArrayList<>();
        shopsEnabledWorlds.add("shops");
        this.shopEnabledWorlds = (List<String>) this.getStringList("module.shop.enabledWorlds", shopsEnabledWorlds);
        this.shopBasePrice = (double) this.getObjectValue("module.shop.basePrice", 300D);
        this.shopLimit = (int) this.getObjectValue("module.shop.shopLimit", 4);

        this.useShopMaterialCleanup = (boolean) this.getObjectValue("module.shop.useShopMaterialCleanup", false);
        this.shopMaterialCleanup = new ArrayList<>();

        List<String> shopCleanupMaterial = new ArrayList<>();
        shopCleanupMaterial.add(Material.SIGN_POST.toString());
        shopCleanupMaterial.add(Material.SIGN.toString());
        shopCleanupMaterial.add(Material.WALL_SIGN.toString());

        for (String materialEntry : (List<String>) this.getStringList("module.shop.shopMaterialCleanup", shopCleanupMaterial)) {
            this.shopMaterialCleanup.add(Material.valueOf(materialEntry));
        }

        this.particleUse = (boolean) this.getObjectValue("module.particle.use", true);

        this.sqlUse = (boolean) this.getObjectValue("module.database.useSql", false);
        this.sqlDataBase = (String) this.getObjectValue("module.database.databaseName", "databaseName");
        this.sqlHostname = (String) getObjectValue("module.database.hostName", "localhost");
        this.sqlPort = (int) getObjectValue("module.database.port", 3306);
        this.sqlUser = (String) getObjectValue("module.database.userName", "mysqlUsername");
        this.sqlPassword = (String) getObjectValue("module.database.password", "mysqlPassword");

        this.entityLimiterUse = (boolean) getObjectValue("module.entityLimiter.use", false);

        this.physicWaterLavaFlowLand = (boolean) getObjectValue("module.physicWaterLavaFlowLand.use", false);

        this.updateCheck = (boolean) getObjectValue("module.updateCheck", true);

        this.cubitSetupDate = (long) getObjectValue("doNotTouch.cubitSetupDate", new Date().getTime());

    }

    public Object getObjectValue(String path, Object defaultValue) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, defaultValue);
        }
        return this.configFile.get(path);

    }

    public Object getStringList(String path, List<String> list) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, list);
        }
        return this.configFile.getStringList(path);
    }

    public Object getMaterialList(String path, List<Material> list) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, list);
        }
        return this.configFile.getStringList(path);
    }

    public CustomConfig getFile() {
        return this.configFile;
    }
}
