package de.kekshaus.cubit.land.api.YamlConfigurationAPI.files;

import de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup.CustomConfig;

public class FlagConfig {

	private CustomConfig configFile;

	public String examplePath;

	public FlagConfig(CustomConfig configFile) {
		this.configFile = configFile;
	}

	public void setup() {
		examplePath = (String) this.configFile.getObjectValue("path", "value");

	}

}
