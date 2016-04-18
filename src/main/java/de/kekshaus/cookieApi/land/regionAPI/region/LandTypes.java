package de.kekshaus.cookieApi.land.regionAPI.region;

import org.bukkit.Bukkit;

public enum LandTypes {
	SERVER, SHOP, DEFAULT, NOTYPE;

	public static LandTypes getLandType(String regionName) {
		String cutName = regionName.split("_")[0];
		if (SERVER.toString().equalsIgnoreCase(cutName)) {
			return SERVER;
		} else if (SHOP.toString().equalsIgnoreCase(cutName)) {
			return SHOP;
		} else if (Bukkit.getWorld(cutName) != null) {
			return DEFAULT;
		} else {
			return NOTYPE;
		}

	}

}