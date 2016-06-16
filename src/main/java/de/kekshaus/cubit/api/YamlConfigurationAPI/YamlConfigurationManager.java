package de.kekshaus.cubit.api.YamlConfigurationAPI;

import org.bukkit.plugin.java.JavaPlugin;

import de.kekshaus.cubit.api.YamlConfigurationAPI.files.LanguageYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.SettingsYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.YamlFileSetup;

public class YamlConfigurationManager {

	private YamlFileSetup fileOperator;

	public YamlConfigurationManager(JavaPlugin plugin) {
		this.fileOperator = new YamlFileSetup(plugin);
	}

	public SettingsYaml getSettings() {
		return this.fileOperator.settings;
	}

	public LanguageYaml getLanguage() {
		return this.fileOperator.language;
	}

}
