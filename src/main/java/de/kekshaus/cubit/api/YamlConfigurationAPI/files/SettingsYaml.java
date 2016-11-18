package de.kekshaus.cubit.api.YamlConfigurationAPI.files;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.CustomConfig;

public class SettingsYaml {

	private CustomConfig configFile;

	/* Public config values */

	/* Land module */

	public List<String> landEnabledWorlds;
	public double landBasePrice;
	public double landTaxAddition;
	public double landMaxPrice;
	public double landSellPercent;
	public double landDeprecatedMember;
	public double landDeprecatedOther;
	public boolean landUseMaterialBorder;
	public Material landBuyMaterialBorder;
	public Material landSellMaterialBorder;

	/* Shop module */

	public List<String> shopEnabledWorlds;
	public double shopBasePrice;
	public int shopLimit;

	/* Particle module */
	public boolean particleUse;

	public boolean entityLimiterUse;

	public boolean physicWaterLavaFlowLand;

	/* Database module */
	public boolean sqlUse;
	// public boolean sqlUseXeonSuiteSameDatabase;
	public String sqlDataBase;
	public String sqlHostname;
	public int sqlPort;
	public String sqlUser;
	public String sqlPassword;

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
		this.landBasePrice = (double) this.getObjectValue("module.land.basePrice", 235D);
		this.landTaxAddition = (double) this.getObjectValue("module.land.taxAddition", 5D);
		this.landMaxPrice = (double) this.getObjectValue("module.land.maxPrice", 750D);
		this.landSellPercent = (double) this.getObjectValue("module.land.sellPercentInDecimal", 0.4D);
		this.landDeprecatedMember = (double) this.getObjectValue("module.land.deprecatedBuyupMember", 35D);
		this.landDeprecatedOther = (double) this.getObjectValue("module.land.deprecatedBuyupOther", 45D);
		this.landUseMaterialBorder = (boolean) this.getObjectValue("module.land.useMaterialBorder", true);
		this.landBuyMaterialBorder = Material
				.valueOf((String) this.getObjectValue("module.land.buyMaterialBorder", Material.TORCH.toString()));
		this.landSellMaterialBorder = Material.valueOf(
				(String) this.getObjectValue("module.land.sellMaterialBorder", Material.REDSTONE_TORCH_ON.toString()));

		List<String> shopsEnabledWorlds = new ArrayList<>();
		shopsEnabledWorlds.add("shops");
		this.shopEnabledWorlds = (List<String>) this.getStringList("module.shop.enabledWorlds", shopsEnabledWorlds);
		this.shopBasePrice = (double) this.getObjectValue("module.shop.basePrice", 300D);
		this.shopLimit = (int) this.getObjectValue("module.shop.shopLimit", 4);

		this.particleUse = (boolean) this.getObjectValue("module.particle.use", true);

		this.sqlUse = (boolean) this.getObjectValue("module.database.useSql", false);
		// this.sqlUseXeonSuiteSameDatabase = (boolean)
		// getObjectValue("module.database.useXeonSuiteSameDatabase", false);
		this.sqlDataBase = (String) this.getObjectValue("module.database.databaseName", "databaseName");
		this.sqlHostname = (String) getObjectValue("module.database.hostName", "localhost");
		this.sqlPort = (int) getObjectValue("module.database.port", 3306);
		this.sqlUser = (String) getObjectValue("module.database.userName", "mysqlUsername");
		this.sqlPassword = (String) getObjectValue("module.database.password", "mysqlPassword");

		this.entityLimiterUse = (boolean) getObjectValue("module.entityLimiter.use", false);

		this.physicWaterLavaFlowLand = (boolean) getObjectValue("module.physicWaterLavaFlowLand.use", false);

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

	public CustomConfig getFile() {
		return this.configFile;
	}
}
