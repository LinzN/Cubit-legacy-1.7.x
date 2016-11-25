package de.kekshaus.cubit.api.classes.interfaces;

import org.bukkit.Chunk;
import org.bukkit.World;

public interface INMSMask {

	public abstract void refreshChunk(Chunk chunk);
	
	public abstract void setBlockFast(World bukkitWorld, int cordX, int cordY, int CordZ, int blockID, byte blockData);

}
