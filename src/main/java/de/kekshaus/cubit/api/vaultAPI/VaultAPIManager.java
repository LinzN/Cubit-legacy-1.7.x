package de.kekshaus.cubit.api.vaultAPI;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sk89q.worldguard.LocalPlayer;

import de.kekshaus.cubit.api.vaultAPI.eConomy.EconomyHook;
import de.kekshaus.cubit.plugin.Landplugin;
import net.milkbowl.vault.economy.Economy;

public class VaultAPIManager {

	private Landplugin plugin;
	private Economy econ = null;
	private EconomyHook ecoMrg;

	public VaultAPIManager(Landplugin plugin) {
		this.plugin = plugin;
		setupEconomy();
		this.ecoMrg = new EconomyHook(plugin, econ);

	}

	private boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public boolean hasEnougToBuy(UUID playerUUID, double value) {
		return this.ecoMrg.hasEnoughToBuy(playerUUID, Math.abs(value));

	}

	public boolean transferMoney(UUID senderUUID, UUID recieverUUID, double value) {
		try {
			ecoMrg.transferMoney(senderUUID, recieverUUID, Math.abs(value));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public double calculateLandCost(UUID uuid, World world, boolean buyTask) {
		double landBasePrice = Landplugin.inst().getYamlManager().getSettings().landBasePrice;
		double landTaxAddition = Landplugin.inst().getYamlManager().getSettings().landTaxAddition;
		double landMaxPrice = Landplugin.inst().getYamlManager().getSettings().landMaxPrice;
		double landSellPercent = Landplugin.inst().getYamlManager().getSettings().landSellPercent;
		LocalPlayer localplayer = Landplugin.inst().getWorldGuardPlugin()
				.wrapOfflinePlayer(Bukkit.getOfflinePlayer(uuid));
		double regionSize = Landplugin.inst().getWorldGuardPlugin().getRegionManager(world)
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
		return ecoMrg.formateToEconomy(value);
	}

}
