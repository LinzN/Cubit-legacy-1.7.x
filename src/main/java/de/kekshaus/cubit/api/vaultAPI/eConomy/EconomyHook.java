package de.kekshaus.cubit.api.vaultAPI.eConomy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.kekshaus.cubit.plugin.Landplugin;
import net.milkbowl.vault.economy.Economy;

public class EconomyHook {

	private Economy econ;
	private Landplugin plugin;

	public EconomyHook(Landplugin plugin, Economy econ) {
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

	@SuppressWarnings("deprecation")
	public void transferMoney(UUID senderUUID, UUID recieverUUID, double value) {

		if (senderUUID != null) {
			OfflinePlayer sender = Bukkit.getOfflinePlayer(senderUUID);
			if (sender.getName() != null){
				econ.withdrawPlayer(sender, value);
			} else {
				econ.withdrawPlayer(this.plugin.getRegionManager().getPlayerName(senderUUID), value);
			}
		}
		if (recieverUUID != null) {
			OfflinePlayer reciever = Bukkit.getOfflinePlayer(recieverUUID);
			if (reciever.getName() != null){
				econ.depositPlayer(reciever, value);
			} else {
				econ.depositPlayer(this.plugin.getRegionManager().getPlayerName(recieverUUID), value);
			}

		}

	}

	public String formateToEconomy(double value) {

		return econ.format(value);

	}

}
