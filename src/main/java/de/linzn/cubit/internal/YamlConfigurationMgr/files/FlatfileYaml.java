package de.linzn.cubit.internal.YamlConfigurationMgr.files;

import java.util.UUID;

import org.bukkit.World;

import de.linzn.cubit.internal.YamlConfigurationMgr.setup.CustomConfig;
import de.linzn.cubit.internal.databaseMgr.OfferData;

public class FlatfileYaml {

	private CustomConfig configFile;
	private String uuidPath = "UUID";
	private String offerAmount = "AMOUNT";

	public FlatfileYaml(CustomConfig configFile) {
		this.configFile = configFile;
	}

	public CustomConfig getFile() {
		return this.configFile;
	}

	public boolean isOffered(String regionID, World world) {
		if (this.configFile.contains(buildPath(regionID, world))) {
			return true;
		}
		return false;

	}

	public OfferData getOfferdata(String regionID, World world) {
		OfferData offerData = null;
		if (this.configFile.contains(buildPath(regionID, world))) {
			UUID uuid = UUID.fromString((String) this.configFile.get(buildPath(regionID, world) + "." + uuidPath));
			double value = (Double) this.configFile.get(buildPath(regionID, world) + "." + offerAmount);

			offerData = new OfferData(regionID, world, uuid, value);
		}
		return offerData;

	}

	public boolean setOfferdata(OfferData data) {
		UUID uuid = data.getPlayerUUID();
		double value = data.getValue();
		World world = data.getWorld();
		String regionID = data.getRegionID();
		try {
			this.configFile.set(buildPath(regionID, world) + "." + uuidPath, uuid.toString());
			this.configFile.set(buildPath(regionID, world) + "." + offerAmount, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.configFile.saveAndReload();
		return true;

	}

	public boolean removeOfferdata(String regionID, World world) {
		try {
			this.configFile.set(buildPath(regionID, world), null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.configFile.saveAndReload();
		return true;

	}

	private String buildPath(String regionID, World world) {
		return world.getName() + "." + regionID;
	}

}
