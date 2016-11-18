package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NMS_1_9_R2 implements NMSMask {

	Plugin plugin;

	public NMS_1_9_R2(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	@SuppressWarnings("deprecation")
	public final void refreshChunk(final org.bukkit.Chunk bukkitChunk) {

		Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
			public void run() {
				final World world = bukkitChunk.getWorld();
				final int xCord = bukkitChunk.getX();
				final int zcord = bukkitChunk.getZ();

				final net.minecraft.server.v1_9_R2.Chunk nmsChunk = ((org.bukkit.craftbukkit.v1_9_R2.CraftChunk) bukkitChunk)
						.getHandle();
				for (final Player player : Bukkit.getOnlinePlayers()) {
					sendChunkPacket(player, nmsChunk);
				}
				world.refreshChunk(xCord, zcord);
			}
		});
	}

	private final void sendChunkPacket(final Player player, final net.minecraft.server.v1_9_R2.Chunk chunk) {
		((org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new net.minecraft.server.v1_9_R2.PacketPlayOutMapChunk(chunk, 20));
	}

}
