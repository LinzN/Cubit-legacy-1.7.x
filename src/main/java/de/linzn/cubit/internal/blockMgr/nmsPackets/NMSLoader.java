package de.linzn.cubit.internal.blockMgr.nmsPackets;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.linzn.cubit.internal.blockMgr.INMSMask;

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
			obj = Class.forName("de.linzn.cubit.internal.blockMgr.nmsPackets.NMS_" + versionNumber).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.nmsMask = (INMSMask) obj;

	}

	public INMSMask nmsHandler() {
		return this.nmsMask;
	}

	private void addVersions() {
		this.nmsList.add("v1_12_R1");
	}

	private static String getVersion() {
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4)
			return array[3];
		return "";
	}

}
