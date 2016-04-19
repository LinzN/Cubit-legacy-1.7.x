package de.kekshaus.cookieApi.land.api.vaultAPI;

import java.util.UUID;

import org.bukkit.plugin.RegisteredServiceProvider;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.vaultAPI.eConomy.EconomyManager;
import net.milkbowl.vault.economy.Economy;

public class VaultManager {

	private Landplugin plugin;
	private Economy econ = null;
	private EconomyManager ecoMrg;

	public VaultManager(Landplugin plugin) {
		this.plugin = plugin;
		setupEconomy();
		this.ecoMrg = new EconomyManager(plugin, econ);

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
		return this.ecoMrg.hasEnoughToBuy(playerUUID, value);

	}

}
