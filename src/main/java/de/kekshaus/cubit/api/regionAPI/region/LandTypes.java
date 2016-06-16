package de.kekshaus.cubit.api.regionAPI.region;

import org.bukkit.Bukkit;

public enum LandTypes {
	SERVER, SHOP, WORLD, NOTYPE;

	public static LandTypes getLandType(String regionName) {
		String cutName = regionName.split("_")[0];
		for (LandTypes type : LandTypes.values()) {
			if (type.toString().equalsIgnoreCase(cutName)) {
				return LandTypes.valueOf(cutName.toUpperCase());
			}
		}
		if (Bukkit.getWorld(cutName) != null) {
			return WORLD;
		} else {
			return NOTYPE;
		}

	}

}