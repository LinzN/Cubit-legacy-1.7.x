package de.kekshaus.cubit.land.api.YamlConfigurationAPI;

import org.bukkit.plugin.java.JavaPlugin;

import de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup.LanguageYaml;
import de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup.SettingsYaml;
import de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup.YamlFileOperator;

public class YamlConfigurationManager {

	private YamlFileOperator fileOperator;

	public YamlConfigurationManager(JavaPlugin plugin) {
		this.fileOperator = new YamlFileOperator(plugin);
	}

	public SettingsYaml getSettings() {
		return this.fileOperator.settings;
	}

	public LanguageYaml getLanguage() {
		return this.fileOperator.language;
	}

}
