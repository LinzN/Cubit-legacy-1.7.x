package de.kekshaus.cookieApi.land.api.vaultAPI.eConomy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import de.kekshaus.cookieApi.land.Landplugin;
import net.milkbowl.vault.economy.Economy;

public class EconomyManager {

	private Economy econ;
	private Landplugin plugin;

	public EconomyManager(Landplugin plugin, Economy econ) {
		this.econ = econ;
		this.plugin = plugin;

	}

	public boolean hasEnoughToBuy(UUID playerUUID, double value) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		if ((econ.getBalance(player) - value) >= 0) {
			return true;
		}
		return false;
	}

	public void transferMoney(UUID senderUUID, UUID recieverUUID, double value) {
		if (senderUUID != null) {
			OfflinePlayer sender = Bukkit.getOfflinePlayer(senderUUID);
			econ.withdrawPlayer(sender, value);
		}
		if (recieverUUID != null) {
			OfflinePlayer reciever = Bukkit.getOfflinePlayer(recieverUUID);
			econ.depositPlayer(reciever, value);
		}
	}

}
