package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.kekshaus.cubi.api.classes.interfaces.INMSMask;
import de.kekshaus.cubit.plugin.Landplugin;

public class NMS_v0_R1 implements INMSMask {

	Plugin plugin = Landplugin.inst();

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
