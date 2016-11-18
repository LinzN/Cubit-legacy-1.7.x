package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NMS_Fallback implements NMSMask {

	Plugin plugin;

	public NMS_Fallback(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	@SuppressWarnings("deprecation")
	public final void refreshChunk(final org.bukkit.Chunk bukkitChunk) {

		Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
			public void run() {
				bukkitChunk.getWorld().refreshChunk(bukkitChunk.getX(), bukkitChunk.getZ());
			}
		});
	}


}
