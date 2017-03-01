package de.kekshaus.cubit.api.databaseAPI.flatfile;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.kekshaus.cubit.api.classes.interfaces.IDatabaseConnector;
import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.plugin.Landplugin;

public class DatabaseProviderFLATFILE implements IDatabaseConnector {

	private Landplugin plugin;

	public DatabaseProviderFLATFILE(Landplugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean link() {
		return true;
	}

	@Override
	public long getTimeStamp(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid).getLastPlayed();
	}

	@Override
	public OfferData getOfferData(String regionID, World world) {
		return plugin.getYamlManager().getFlatfile().getOfferdata(regionID, world);
	}

	@Override
	public boolean isOffered(String regionID, World world) {
		return plugin.getYamlManager().getFlatfile().isOffered(regionID, world);
	}

	@Override
	public boolean setOfferData(OfferData data) {
		return plugin.getYamlManager().getFlatfile().setOfferdata(data);
	}

	@Override
	public boolean removeOfferData(String regionID, World world) {
		return plugin.getYamlManager().getFlatfile().removeOfferdata(regionID, world);
	}

	@Override
	public void updatePlayer(UUID uuid, String player, long time) {
		// TODO Auto-generated method stub

	}

}
