package de.kekshaus.cubit.api.blockAPI.fastBlock;

public class LocationUtil
{
  public static int[] translateChunkXZ(int paramInt1, int paramInt2)
  {
    return new int[] { paramInt1 >> 4, paramInt2 >> 4 };
  }
}
