package de.kekshaus.cubit.api.blockAPI.fastBlock;

import org.bukkit.World;

public class CubitBlock
{
  private int locX;
  private int locY;
  private int locZ;
  private World w;
  
  public CubitBlock(int paramInt1, int paramInt2, int paramInt3, World paramWorld)
  {
    this.locX = paramInt1;
    this.locY = paramInt2;
    this.locZ = paramInt3;
    this.w = paramWorld;
  }
  
  public int getX()
  {
    return this.locX;
  }
  
  public int getY()
  {
    return this.locY;
  }
  
  public int getZ()
  {
    return this.locZ;
  }
  
  public World getWorld()
  {
    return this.w;
  }
}
