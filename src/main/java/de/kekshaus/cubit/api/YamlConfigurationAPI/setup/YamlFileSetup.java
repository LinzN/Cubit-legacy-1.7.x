package de.kekshaus.cubit.api.YamlConfigurationAPI.setup;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import de.kekshaus.cubit.api.YamlConfigurationAPI.files.LanguageYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.SettingsYaml;

public class YamlFileSetup {

	public SettingsYaml settings;
	public LanguageYaml language;

	public YamlFileSetup(JavaPlugin plugin) {
		File flatfileDirectory = new File(plugin.getDataFolder().getAbsolutePath() + "/flatfiles");
		if (!flatfileDirectory.exists()) {
			boolean setting = flatfileDirectory.mkdir();
			if (setting) {
				System.out.println("Created flatfile directory");
			} else {
				System.out.println("Error while creating flatfile directory");
			}
		}

		File languageDirectory = new File(plugin.getDataFolder().getAbsolutePath() + "/languages");
		if (!languageDirectory.exists()) {
			boolean language = languageDirectory.mkdir();
			if (language) {
				System.out.println("Created languages directory");
			} else {
				System.out.println("Error while creating languages directory");
			}
		}

		CustomConfig languageConfig = new CustomConfig(plugin, languageDirectory, "language.yml");
		this.language = new LanguageYaml(languageConfig);
		languageConfig.saveAndReload();
		/* Configs */
		CustomConfig settingsConfig = new CustomConfig(plugin, plugin.getDataFolder(), "settings.yml");
		this.settings = new SettingsYaml(settingsConfig);
		settingsConfig.saveAndReload();

	}
}
