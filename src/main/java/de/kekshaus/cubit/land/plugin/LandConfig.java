package de.kekshaus.cubit.land.plugin;

import org.bukkit.configuration.file.FileConfiguration;

import de.kekshaus.cubit.land.Landplugin;

public class LandConfig {

	private Landplugin plugin;
	private FileConfiguration configFile;

	public String sqlDataBase;
	public String sqlHostname;
	public int sqlPort;
	public String sqlUser;
	public String sqlPassword;

	public LandConfig(Landplugin plugin) {
		this.plugin = plugin;
		initConfig();
	}

	private void initConfig() {
		saveConfig();
		initContent();
	}

	private void saveConfig() {
		this.plugin.saveDefaultConfig();
		this.plugin.saveConfig();
		this.plugin.reloadConfig();
		this.configFile = this.plugin.getConfig();
	}

	private void initContent() {
		setContent();
		saveConfig();
		loadContent();
	}

	private void setContent() {
		checkContent("path", 333306);
	}

	private void loadContent() {
		this.sqlDataBase = (String) this.configFile.get("path");
		this.sqlHostname = (String) this.configFile.get("path");
		this.sqlPort = (int) this.configFile.get("path");
		this.sqlUser = (String) this.configFile.get("path");
		this.sqlPassword = (String) this.configFile.get("path");
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
