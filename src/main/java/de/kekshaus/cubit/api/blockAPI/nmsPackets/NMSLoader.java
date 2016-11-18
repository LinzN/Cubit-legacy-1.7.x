package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NMSLoader {
	
	private HashMap<String, NMSMask> nmsList = new HashMap<String, NMSMask>();
	private NMSMask nmsMask;
	private Plugin plugin;
	
	public NMSLoader(Plugin plugin){
		this.plugin = plugin;
		addVersions();
		loadNMSClass();
	}

	public void loadNMSClass(){
		
		if (this.nmsList.containsKey(getVersion())){
			this.nmsMask = this.nmsList.get(getVersion());
			plugin.getLogger().info("Using " + getVersion() + " for NMS Class");
		} else {
			plugin.getLogger().info("No version found for " + getVersion() + "! Fallback to nonNMS. Chunk-Refresh will not work!");
			this.nmsMask = this.nmsList.get("v0_r1");
		}
		
	}
	
	public NMSMask getNMSClass(){
		return this.nmsMask;
	}
	
	private void addVersions(){
		this.nmsList.put("v0_r1", new NMS_Fallback(this.plugin));
		this.nmsList.put("v1_11_R1", new NMS_1_11_R1(this.plugin));
		this.nmsList.put("v1_10_R1", new NMS_1_10_R1(this.plugin));
		this.nmsList.put("v1_9_R2", new NMS_1_9_R2(this.plugin));
		this.nmsList.put("v1_9_R1", new NMS_1_9_R1(this.plugin));
		this.nmsList.put("v1_8_R3", new NMS_1_8_R3(this.plugin));
		this.nmsList.put("v1_8_R2", new NMS_1_8_R2(this.plugin));
		this.nmsList.put("v1_8_R1", new NMS_1_8_R1(this.plugin));
	}
	
	
	private static String getVersion() {
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4)
			return array[3];
		return "";
	}

}



