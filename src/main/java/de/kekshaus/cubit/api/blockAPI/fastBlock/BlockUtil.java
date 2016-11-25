package de.kekshaus.cubit.api.blockAPI.fastBlock;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;

public class BlockUtil
{
  public static int getCombinedID(int paramInt, byte paramByte)
  {
    return paramInt + (paramByte << 12);
  }
  
  public static int getBlockAmount(CubitBlock paramCube1, CubitBlock paramCube2)
  {
    if ((paramCube1 == null) || (paramCube2 == null)) {
      return 0;
    }
    int i = 0;
    
    int j = Math.min(paramCube1.getX(), paramCube2.getX()) - 1;
    int n = Math.max(paramCube1.getX(), paramCube2.getX()) + 1;
    
    int k = Math.min(paramCube1.getY(), paramCube2.getY()) - 1;
    int i1 = Math.max(paramCube1.getY(), paramCube2.getY()) + 1;
    
    int m = Math.min(paramCube1.getZ(), paramCube2.getZ()) - 1;
    int i2 = Math.max(paramCube1.getZ(), paramCube2.getZ()) + 1;
    for (int i3 = j; i3 < n; i3++) {
      for (int i4 = k; i4 < i1; i4++) {
        for (int i5 = m; i5 < i2; i5++) {
          i++;
        }
      }
    }
    return i;
  }
  
  public static List<Block> getBlockList(CubitBlock paramCube1, CubitBlock paramCube2)
  {
    ArrayList<Block> localArrayList = new ArrayList<Block>();
    
    int i = Math.min(paramCube1.getX(), paramCube2.getX());
    int m = Math.max(paramCube1.getX(), paramCube2.getX());
    
    int j = Math.min(paramCube1.getY(), paramCube2.getY());
    int n = Math.max(paramCube1.getY(), paramCube2.getY());
    
    int k = Math.min(paramCube1.getZ(), paramCube2.getZ());
    int i1 = Math.max(paramCube1.getZ(), paramCube2.getZ());
    for (int i2 = i; i2 < m; i2++) {
      for (int i3 = j; i3 < n; i3++) {
        for (int i4 = k; i4 < i1; i4++)
        {
          Block localBlock = paramCube2.getWorld().getBlockAt(i2, i3, i4);
          
          localArrayList.add(localBlock);
        }
      }
    }
    return localArrayList;
  }
}
