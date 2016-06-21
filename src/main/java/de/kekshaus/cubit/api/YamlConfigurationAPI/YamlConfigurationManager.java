package de.kekshaus.cubit.api.YamlConfigurationAPI;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.kekshaus.cubit.api.YamlConfigurationAPI.files.FlatfileYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.LanguageYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.LimitYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.files.SettingsYaml;
import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.YamlFileSetup;

public class YamlConfigurationManager {

	private YamlFileSetup fileOperator;
	private Plugin plugin;

	public YamlConfigurationManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.fileOperator = new YamlFileSetup(this.plugin);
	}

	public SettingsYaml getSettings() {
		return this.fileOperator.settings;
	}

	public LanguageYaml getLanguage() {
		return this.fileOperator.language;
	}

	public FlatfileYaml getFlatfile() {
		return this.fileOperator.flatFileDatabase;
	}

	public LimitYaml getLimit() {
		return this.fileOperator.limit;
	}

}
