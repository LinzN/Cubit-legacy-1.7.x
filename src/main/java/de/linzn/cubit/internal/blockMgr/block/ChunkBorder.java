package de.linzn.cubit.internal.blockMgr.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class ChunkBorder implements Runnable {

	private Plugin plugin;
	private Chunk chunk;
	private Material torchMaterial;

	private Worker work;

	public ChunkBorder(Plugin plugin, Chunk chunk, Material torchMaterial) {
		this.plugin = plugin;
		this.chunk = chunk;
		this.torchMaterial = torchMaterial;
		work = new Worker();
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this);
	}

	@Override
	public void run() {
		if (work.isTaskComplete()) {
			return;
		}

		for (int count = 0; count < 4 && !work.isTaskComplete(); work.i++, count++) {
			Location corner_1 = chunk.getBlock(work.i, 0, 0).getLocation();
			corner_1.setY(getValidBlockHight(chunk.getWorld(), corner_1.getBlockX(), corner_1.getBlockZ()));

			Location corner_2 = chunk.getBlock(15, 0, work.i).getLocation();
			corner_2.setY(getValidBlockHight(chunk.getWorld(), corner_2.getBlockX(), corner_2.getBlockZ()));

			Location corner_3 = chunk.getBlock((15 - work.i), 0, 15).getLocation();
			corner_3.setY(getValidBlockHight(chunk.getWorld(), corner_3.getBlockX(), corner_3.getBlockZ()));

			Location corner_4 = chunk.getBlock(0, 0, (15 - work.i)).getLocation();
			corner_4.setY(getValidBlockHight(chunk.getWorld(), corner_4.getBlockX(), corner_4.getBlockZ()));

			setBlockSync(torchMaterial, corner_1, corner_2, corner_3, corner_4);
		}
		if (!work.isTaskComplete()) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this);
		}
	}

	public class Worker {
		public int i = 0;

		public boolean isTaskComplete() {
			return i == 15;
		}
	}

	public int getValidBlockHight(World world, int x, int z) {
		int y;
		for (y = 255; y > 0; y--) {
			if ((world.getBlockAt(x, y, z).getType() != Material.AIR)
					&& (world.getBlockAt(x, y, z).getType() != Material.ENDER_PORTAL_FRAME)
					&& (world.getBlockAt(x, y, z).getType() != Material.BEDROCK)) {
				if ((world.getBlockAt(x, y, z).getType() != Material.TORCH)
						&& (world.getBlockAt(x, y, z).getType() != Material.REDSTONE_TORCH_ON)
						&& (world.getBlockAt(x, y, z).getType() != Material.DEAD_BUSH)
						&& (world.getBlockAt(x, y, z).getType() != Material.DOUBLE_PLANT)
						&& (world.getBlockAt(x, y, z).getType() != Material.SNOW)) {
					break;
				}
				y--;
				break;
			}
		}
		return y + 1;
	}

	private void setBlockSync(final Material material, final Location corner_1, final Location corner_2,
			final Location corner_3, final Location corner_4) {
		plugin.getServer().getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				chunk.getWorld().getBlockAt(corner_1).setType(material);
				chunk.getWorld().getBlockAt(corner_2).setType(material);
				chunk.getWorld().getBlockAt(corner_3).setType(material);
				chunk.getWorld().getBlockAt(corner_4).setType(material);
			}

		});
	}
}
