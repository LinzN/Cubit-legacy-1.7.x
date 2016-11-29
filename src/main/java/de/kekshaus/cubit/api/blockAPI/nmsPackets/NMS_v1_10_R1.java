package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.CraftChunk;

public class NMS_v1_10_R1 implements INMSMask {


	public void refreshChunk(org.bukkit.Chunk paramChunk) {
		PacketPlayOutMapChunk localPacketPlayOutMapChunk = new PacketPlayOutMapChunk(
				((CraftChunk) paramChunk).getHandle(), 65535);
		for (Player localPlayer : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) localPlayer).getHandle().playerConnection.sendPacket(localPacketPlayOutMapChunk);
		}
	}

	public void sendTitle(Player paramPlayer, String paramString) {
		PacketPlayOutTitle localPacketPlayOutTitle1 = new PacketPlayOutTitle(0, 40, 0);

		PacketPlayOutTitle localPacketPlayOutTitle2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"\"}"));

		PacketPlayOutTitle localPacketPlayOutTitle3 = new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + paramString + "\"}"));

		((CraftPlayer) paramPlayer).getHandle().playerConnection.sendPacket(localPacketPlayOutTitle1);
		((CraftPlayer) paramPlayer).getHandle().playerConnection.sendPacket(localPacketPlayOutTitle3);
		((CraftPlayer) paramPlayer).getHandle().playerConnection.sendPacket(localPacketPlayOutTitle2);
	}


}
