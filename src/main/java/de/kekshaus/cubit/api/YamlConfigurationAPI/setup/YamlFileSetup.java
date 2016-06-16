package de.kekshaus.cubit.api.YamlConfigurationAPI.setup;

import java.io.File;

import org.bukkit.plugin.Plugin;

import de.kekshaus.cubit.api.YamlConfigurationAPI.files.LanguageYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.SettingsYaml;

public class YamlFileSetup {

	public SettingsYaml settings;
	public LanguageYaml language;
	private Plugin plugin;

	public YamlFileSetup(Plugin plugin) {
		this.plugin = plugin;
		File flatfileDirectory = new File(this.plugin.getDataFolder(), "flatfiles");
		if (!flatfileDirectory.exists()) {
			try {
				boolean setting = flatfileDirectory.mkdirs();
				if (setting) {
					System.out.println("Created flatfile directory");
				} else {
					System.out.println("Error while creating flatfile directory");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		File languageDirectory = new File(this.plugin.getDataFolder(), "languages");
		if (!languageDirectory.exists()) {
			try {
				boolean language = languageDirectory.mkdirs();
				if (language) {
					System.out.println("Created languages directory");
				} else {
					System.out.println("Error while creating languages directory");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		CustomConfig languageConfig = new CustomConfig(this.plugin, languageDirectory, "language.yml");
		this.language = new LanguageYaml(languageConfig);
		languageConfig.saveAndReload();
		/* Configs */
		CustomConfig settingsConfig = new CustomConfig(this.plugin, this.plugin.getDataFolder(), "settings.yml");
		this.settings = new SettingsYaml(settingsConfig);
		settingsConfig.saveAndReload();

	}
}
