package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.interfaces.INMSMask;

public class NMS_v0_R1 implements INMSMask {

	@SuppressWarnings("deprecation")
	@Override
	public void refreshChunk(Chunk chunk) {
		// TODO Auto-generated method stub
		chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());

	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendTitle(Player paramPlayer, String paramString) {
		// TODO Auto-generated method stub
		paramPlayer.sendTitle(paramString, "");

	}

}
