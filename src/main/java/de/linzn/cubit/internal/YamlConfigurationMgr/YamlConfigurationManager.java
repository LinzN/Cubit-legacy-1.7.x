package de.linzn.cubit.internal.YamlConfigurationMgr;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.linzn.cubit.internal.YamlConfigurationMgr.files.FlagProtectionsYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.FlatfileYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.LanguageYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.LimitYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.SettingsYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.setup.YamlFileSetup;

public class YamlConfigurationManager {

	private YamlFileSetup fileOperator;
	private Plugin plugin;

	public YamlConfigurationManager(JavaPlugin plugin) {
		plugin.getLogger().info("Loading YamlConfigurationManager");
		this.plugin = plugin;
		this.fileOperator = new YamlFileSetup(this.plugin);
	}

	public void reloadAllConfigs() {
		plugin.getLogger().info("Reloading all cubit configs");
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

	public FlagProtectionsYaml getFlag() {
		return this.fileOperator.flag;
	}

}
