package de.linzn.cubit.internal.blockMgr;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public interface INMSMask {

	public abstract void refreshChunk(Chunk chunk);

	public abstract void sendTitle(Player paramPlayer, String paramString);

}
