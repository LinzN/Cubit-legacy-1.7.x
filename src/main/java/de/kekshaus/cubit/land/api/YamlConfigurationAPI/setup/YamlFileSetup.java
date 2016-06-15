package de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import de.kekshaus.cubit.land.api.YamlConfigurationAPI.files.LanguageYaml;
import de.kekshaus.cubit.land.api.YamlConfigurationAPI.files.SettingsYaml;

public class YamlFileSetup {

	public SettingsYaml settings;
	public LanguageYaml language;

	public YamlFileSetup(JavaPlugin plugin) {
		File configDirectory = new File(plugin.getDataFolder(), "flatfile");
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

		CustomConfig languageConfig = new CustomConfig(plugin, languageDirectory, "language.yml");
		this.language = new LanguageYaml(languageConfig);

	}
}
