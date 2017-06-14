package de.linzn.cubit.internal.vaultMgr;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldguard.LocalPlayer;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.vaultMgr.eConomy.EconomyHook;
import net.milkbowl.vault.economy.Economy;

public class VaultManager {

	private CubitBukkitPlugin plugin;
	private Economy econ = null;
	private EconomyHook ecoMrg = null;

	public VaultManager(CubitBukkitPlugin plugin) {
		plugin.getLogger().info("Loading VaultAPIManager");
		this.plugin = plugin;
		if (setupEconomy()) {
			this.ecoMrg = new EconomyHook(plugin, econ);
		}

	}

	private boolean setupEconomy() {
		if (!CubitBukkitPlugin.inst().getYamlManager().getSettings().landEnableEconomy) {
			this.plugin.getLogger().info("Economy is in config disabled!");
			return false;
		}
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			this.plugin.getLogger().info("No valid economy plugin found. Economy is disabled.");
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public boolean hasEnougToBuy(UUID playerUUID, double value) {
		if (this.econ != null) {
			return this.ecoMrg.hasEnoughToBuy(playerUUID, Math.abs(value));
		}
		return true;

	}

	public boolean transferMoney(UUID senderUUID, UUID recieverUUID, double value) {
		if (this.econ != null) {
			try {
				ecoMrg.transferMoney(senderUUID, recieverUUID, Math.abs(value));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public double calculateLandCost(UUID uuid, World world, boolean buyTask) {
		double landBasePrice = CubitBukkitPlugin.inst().getYamlManager().getSettings().landBasePrice;
		double landTaxAddition = CubitBukkitPlugin.inst().getYamlManager().getSettings().landTaxAddition;
		double landMaxPrice = CubitBukkitPlugin.inst().getYamlManager().getSettings().landMaxPrice;
		double landSellPercent = CubitBukkitPlugin.inst().getYamlManager().getSettings().landSellPercent;
		LocalPlayer localplayer = CubitBukkitPlugin.inst().getWorldGuardPlugin()
				.wrapOfflinePlayer(Bukkit.getOfflinePlayer(uuid));
		double regionSize = CubitBukkitPlugin.inst().getWorldGuardPlugin().getRegionManager(world)
				.getRegionCountOfPlayer(localplayer);
		double price;

		if (buyTask) {
			price = landBasePrice + (landTaxAddition * regionSize);
			if (price > landMaxPrice) {
				price = landMaxPrice;
			}

		} else {
			double sellValue = landBasePrice + (landTaxAddition * regionSize);
			if (sellValue > landMaxPrice) {
				sellValue = landMaxPrice;
			}
			price = (sellValue * landSellPercent);
		}

		return price;
	}

	public String formateToEconomy(double value) {
		if (this.econ != null) {
			return ecoMrg.formateToEconomy(value);
		}
		return "0.00";
	}

}
