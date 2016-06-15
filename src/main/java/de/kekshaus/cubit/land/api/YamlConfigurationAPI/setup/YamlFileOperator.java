package de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class YamlFileOperator {

	public SettingsYaml settings;

	public YamlFileOperator(JavaPlugin plugin) {
		File configDirectory = new File(plugin.getDataFolder(), "configs");
		if (!configDirectory.exists()) {
			configDirectory.mkdir();
		}

		File languageDirectory = new File(plugin.getDataFolder(), "languages");
		if (!languageDirectory.exists()) {
			languageDirectory.mkdir();
		}

		/* Configs */
		CustomConfig settingsConfig = new CustomConfig(plugin, plugin.getDataFolder(), "settings.yml");
		this.settings = new SettingsYaml(settingsConfig);
	}
}
