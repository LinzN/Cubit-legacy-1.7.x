package de.kekshaus.cubit.api.blockAPI.nmsPackets;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.kekshaus.cubit.api.classes.interfaces.INMSMask;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;

public class NMS_v1_8_R2 implements INMSMask {


	public void refreshChunk(org.bukkit.Chunk paramChunk) {
		PacketPlayOutMapChunk localPacketPlayOutMapChunk = new PacketPlayOutMapChunk(
				((CraftChunk) paramChunk).getHandle(), true, 65535);
		for (Player localPlayer : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) localPlayer).getHandle().playerConnection.sendPacket(localPacketPlayOutMapChunk);
		}
	}

	public void sendTitle(Player paramPlayer, String paramString) {
		PacketPlayOutTitle localPacketPlayOutTitle1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				null, 0, 40, 0);

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
