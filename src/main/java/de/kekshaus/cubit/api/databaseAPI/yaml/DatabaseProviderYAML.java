package de.kekshaus.cubit.api.databaseAPI.yaml;

import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.api.databaseAPI.IDatabaseProvider;
import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.plugin.Landplugin;

public class DatabaseProviderYAML implements IDatabaseProvider {

	private Landplugin plugin;

	public DatabaseProviderYAML(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean link() {
		return false;
	}

	@Override
	public long getTimeStamp(UUID uuid) {
		return plugin.getServer().getOfflinePlayer(uuid).getLastPlayed();
	}

	@Override
	public OfferData getOfferData(String regionID, World world) {
		return null;
	}

	@Override
	public boolean isOffered(String regionID, World world) {
		return false;
	}

	@Override
	public boolean setOfferData(OfferData data) {
		return true;
	}

	@Override
	public boolean removeOfferData(String regionID, World world) {
		return true;
	}

	@Override
	public void updatePlayer(UUID uuid, String player, long time) {
		// TODO Auto-generated method stub

	}

}
