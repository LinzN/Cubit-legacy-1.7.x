package de.kekshaus.cubit.api.YamlConfigurationAPI.files;

import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.CustomConfig;

public class FlatfileYaml {

	private CustomConfig configFile;

	public FlatfileYaml(CustomConfig configFile) {
		this.configFile = configFile;
	}

	public CustomConfig getFile() {
		return this.configFile;
	}

}
