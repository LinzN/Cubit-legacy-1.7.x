package de.kekshaus.cubit.api.YamlConfigurationAPI.files;

import de.kekshaus.cubit.api.YamlConfigurationAPI.setup.CustomConfig;

public class FlagConfig {

	private CustomConfig configFile;

	public String examplePath;

	public FlagConfig(CustomConfig configFile) {
		this.configFile = configFile;
	}

	public void setup() {
		examplePath = (String) this.getObjectValue("path", "value");

	}

	public Object getObjectValue(String path, Object defaultValue) {
		if (!this.configFile.contains(path)) {
			this.configFile.set(path, defaultValue);
		}
		return this.configFile.get(path);

	}

	public CustomConfig getFile() {
		return this.configFile;
	}

}
