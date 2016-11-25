package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.kekshaus.cubit.api.blockAPI.fastBlock.BlockUtil;
import de.kekshaus.cubit.api.blockAPI.fastBlock.LocationUtil;
import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import de.kekshaus.cubit.plugin.Landplugin;

public class NMS_v1_10_R1 implements INMSMask {

	Plugin plugin = Landplugin.inst();

	@Override
	@SuppressWarnings("deprecation")
	public final void refreshChunk(final org.bukkit.Chunk bukkitChunk) {

		Bukkit.getScheduler().runTask(this.plugin, new Runnable() {
			public void run() {
				final World world = bukkitChunk.getWorld();
				final int xCord = bukkitChunk.getX();
				final int zcord = bukkitChunk.getZ();

				final net.minecraft.server.v1_10_R1.Chunk nmsChunk = ((org.bukkit.craftbukkit.v1_10_R1.CraftChunk) bukkitChunk)
						.getHandle();
				for (final Player player : Bukkit.getOnlinePlayers()) {
					sendChunkPacket(player, nmsChunk);
				}
				world.refreshChunk(xCord, zcord);
			}
		});
	}

	private final void sendChunkPacket(final Player player, final net.minecraft.server.v1_10_R1.Chunk chunk) {
		((org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer) player).getHandle().playerConnection
				.sendPacket(new net.minecraft.server.v1_10_R1.PacketPlayOutMapChunk(chunk, 20));
	}

	@Override
	  public void setBlockFast(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte paramByte)
	  {
		org.bukkit.craftbukkit.v1_10_R1.CraftWorld localCraftWorld = (org.bukkit.craftbukkit.v1_10_R1.CraftWorld)paramWorld;
	    net.minecraft.server.v1_10_R1.WorldServer localWorldServer = localCraftWorld.getHandle();
	    
	    int[] arrayOfInt = LocationUtil.translateChunkXZ(paramInt1, paramInt3);
	    net.minecraft.server.v1_10_R1.Chunk localChunk = localWorldServer.getChunkAt(arrayOfInt[0], arrayOfInt[1]);
	    
	    net.minecraft.server.v1_10_R1.BlockPosition localBlockPosition = new net.minecraft.server.v1_10_R1.BlockPosition(paramInt1, paramInt2, paramInt3);
	    net.minecraft.server.v1_10_R1.IBlockData localIBlockData = net.minecraft.server.v1_10_R1.Block.getByCombinedId(BlockUtil.getCombinedID(paramInt4, paramByte));
	    
	    localChunk.a(localBlockPosition, localIBlockData);
	  }

}
