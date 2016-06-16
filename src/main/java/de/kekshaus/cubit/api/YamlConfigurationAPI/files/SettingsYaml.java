package de.kekshaus.cubit.api.YamlConfigurationAPI.files;

import org.bukkit.Material;

import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.CustomConfig;

public class SettingsYaml {

	private CustomConfig configFile;

	/* Public config values */

	/* Land module */
	public double landBasePrice;
	public double landTaxAddition;
	public double landMaxPrice;
	public double landSellPercent;
	public double landDeprecatedMember;
	public double landDeprecatedOther;
	public boolean landUseMaterialBorder;
	public Material landBuyMaterialBorder;
	public Material landSellMaterialBorder;

	/* Particle module */
	public boolean particleUse;
	public boolean particleUseInventivetalentParticeApi;

	/* Database module */
	public boolean sqlUse;
	public boolean sqlUseXeonSuiteSameDatabase;
	public String sqlDataBase;
	public String sqlHostname;
	public int sqlPort;
	public String sqlUser;
	public String sqlPassword;

	public SettingsYaml(CustomConfig config) {
		this.configFile = config;
		initConfig();
	}

	private void initConfig() {
		saveConfig();
		initContent();
	}

	private void saveConfig() {
		this.configFile.save();
		this.configFile.reload();

	}

	private void initContent() {
		setContent();
		saveConfig();
		loadContent();
	}

	private void setContent() {

		/* Land module */
		checkContent("module.land.basePrice", 235.00D);
		checkContent("module.land.taxAddition", 5.00D);
		checkContent("module.land.maxPrice", 750.00D);
		checkContent("module.land.sellPercentInDecimal", 0.5D);
		checkContent("module.land.deprecatedBuyupMember", 30.0D);
		checkContent("module.land.deprecatedBuyupOther", 45.00D);
		checkContent("module.land.useMaterialBorder", true);
		checkContent("module.land.buyMaterialBorder", Material.TORCH.toString());
		checkContent("module.land.sellMaterialBorder", Material.REDSTONE_TORCH_ON.toString());

		/* Particle module */

		checkContent("module.particle.use", true);
		checkContent("module.particle.useInventivetalentParticleAPI", false);
		/* Database module */

		checkContent("module.database.useSql", false);
		checkContent("module.database.useXeonSuiteSameDatabase", false);
		checkContent("module.database.databaseName", "cubit");
		checkContent("module.database.hostName", "localhost");
		checkContent("module.database.port", 3306);
		checkContent("module.database.userName", "yourSqlUser");
		checkContent("module.database.password", "yourSqlPassword");

	}

	private void loadContent() {
		this.landBasePrice = (double) this.configFile.get("module.land.basePrice");
		this.landTaxAddition = (double) this.configFile.get("module.land.taxAddition");
		this.landMaxPrice = (double) this.configFile.get("module.land.maxPrice");
		this.landSellPercent = (double) this.configFile.get("module.land.sellPercentInDecimal");
		this.landDeprecatedMember = (double) this.configFile.get("module.land.deprecatedBuyupMember");
		this.landDeprecatedOther = (double) this.configFile.get("module.land.deprecatedBuyupOther");
		this.landUseMaterialBorder = (boolean) this.configFile.get("module.land.useMaterialBorder");
		this.landBuyMaterialBorder = Material.valueOf((String) this.configFile.get("module.land.buyMaterialBorder"));
		this.landSellMaterialBorder = Material.valueOf((String) this.configFile.get("module.land.sellMaterialBorder"));

		this.particleUse = (boolean) this.configFile.get("module.particle.use");
		this.particleUseInventivetalentParticeApi = (boolean) this.configFile
				.get("module.particle.useInventivetalentParticleAPI");

		this.sqlUse = (boolean) this.configFile.get("module.database.useSql");
		this.sqlUseXeonSuiteSameDatabase = (boolean) this.configFile.get("module.database.useXeonSuiteSameDatabase");
		this.sqlDataBase = (String) this.configFile.get("module.database.databaseName");
		this.sqlHostname = (String) this.configFile.get("module.database.hostName");
		this.sqlPort = (int) this.configFile.get("module.database.port");
		this.sqlUser = (String) this.configFile.get("module.database.userName");
		this.sqlPassword = (String) this.configFile.get("module.database.password");
	}

	private void checkContent(String path, Object defaultValue) {
		if (!containsContent(path)) {
			addContent(path, defaultValue);
		}
	}

	private void addContent(String path, Object defaultValue) {
		this.configFile.set(path, defaultValue);
	}

	private boolean containsContent(String path) {
		if (this.configFile.contains(path)) {
			return true;
		}
		return false;
	}

}
