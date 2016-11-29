package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.kekshaus.cubit.api.classes.interfaces.INMSMask;

public class NMSLoader {

	private HashSet<String> nmsList = new HashSet<String>();
	private INMSMask nmsMask;
	private Plugin plugin;

	public NMSLoader(Plugin plugin) {
		this.plugin = plugin;
		addVersions();
		loadNMSClass();
	}

	public void loadNMSClass() {

		String versionNumber = "v0_R1";
		if (this.nmsList.contains(getVersion())) {
			versionNumber = getVersion();
			plugin.getLogger().info("Using " + getVersion() + " for NMS Class");
		} else {
			plugin.getLogger().info(
					"No version found for " + getVersion() + "! Fallback to nonNMS. Chunk-Refresh will not work!");
		}

		Object obj = null;
		try {
			obj = Class.forName("de.kekshaus.cubit.api.blockAPI.nmsPackets.NMS_" + versionNumber).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.nmsMask = (INMSMask) obj;

	}

	public INMSMask getNMSClass() {
		return this.nmsMask;
	}

	private void addVersions() {
		this.nmsList.add("v1_11_R1");
		this.nmsList.add("v1_10_R1");
		this.nmsList.add("v1_9_R2");
		this.nmsList.add("v1_9_R1");
		this.nmsList.add("v1_8_R3");
		this.nmsList.add("v1_8_R2");
	}

	private static String getVersion() {
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4)
			return array[3];
		return "";
	}

}
