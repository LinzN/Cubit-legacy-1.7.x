package de.kekshaus.cubit.api.YamlConfigurationAPI.setup;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig extends YamlConfiguration {

	private File file;
	private String defaults;
	private JavaPlugin plugin;

	/**
	 * Creates new PluginFile, with defaults
	 * 
	 * @param plugin
	 *            - Your plugin
	 * @param file
	 *            - Your directory
	 * @param fileName
	 *            - Name of the file
	 * @param defaultsName
	 *            - Name of the defaults
	 */
	public CustomConfig(JavaPlugin plugin, File directory, String fileName) {
		this(plugin, directory, fileName, null);
	}

	public CustomConfig(JavaPlugin plugin, File directory, String fileName, String defaultsName) {
		this.plugin = plugin;
		this.defaults = defaultsName;
		this.file = new File(directory, fileName);
		reload();
	}

	/**
	 * Reload configuration
	 */
	public void reload() {

		if (!file.exists()) {

			try {
				file.getParentFile().mkdirs();
				file.createNewFile();

			} catch (IOException exception) {
				exception.printStackTrace();
				plugin.getLogger().severe("Error while creating file " + file.getName());
			}

		}

		try {
			load(file);

			if (defaults != null) {
				InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
				FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

				setDefaults(defaultsConfig);
				options().copyDefaults(true);

				reader.close();
				save();
			}

		} catch (IOException exception) {
			exception.printStackTrace();
			plugin.getLogger().severe("Error while loading file " + file.getName());

		} catch (InvalidConfigurationException exception) {
			exception.printStackTrace();
			plugin.getLogger().severe("Error while loading file " + file.getName());

		}

	}

	/**
	 * Save configuration
	 */
	public void save() {

		try {
			options().indent(2);
			save(file);

		} catch (IOException exception) {
			exception.printStackTrace();
			plugin.getLogger().severe("Error while saving file " + file.getName());
		}

	}

	public void saveAndReload() {
		save();
		reload();
	}

	public String getLanguageString(String path, String defaultValue) {
		if (!this.contains(path)) {
			this.set(path, defaultValue);
		}
		return this.getString(path);

	}

	public Object getObjectValue(String path, Object defaultValue) {
		if (!this.contains(path)) {
			this.set(path, defaultValue);
		}
		return this.get(path);

	}

}