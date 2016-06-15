package de.kekshaus.cubit.land.api.databaseAPI.sql;

import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.databaseAPI.OfferData;
import de.kekshaus.cubit.land.api.databaseAPI.sql.getData.DataBaseSQLGetData;
import de.kekshaus.cubit.land.api.databaseAPI.sql.setData.DataBaseSQLSetData;
import de.kekshaus.cubit.land.api.databaseAPI.sql.setup.DataBaseSQLSetup;

public class DataBaseSQLProvider {

	private Landplugin plugin;
	private DataBaseSQLSetData setData;
	private DataBaseSQLGetData getData;

	public DataBaseSQLProvider(Landplugin plugin) {
		this.plugin = plugin;
		this.setData = new DataBaseSQLSetData();
		this.getData = new DataBaseSQLGetData();
	}

	public boolean link() {
		return DataBaseSQLSetup.setup(plugin);
	}

	public long getTimeStamp(UUID uuid) {
		return getData.getTimeStamp(uuid);
	}

	public OfferData getOfferData(String regionID, World world) {
		return getData.getOfferData(regionID, world);
	}

	public boolean isOffered(String regionID, World world) {
		return getData.isOffered(regionID, world.getName().toLowerCase());
	}

	public boolean setOfferData(OfferData data) {
		setData.setOffer(data);
		return true;
	}

	public boolean removeOfferData(String regionID, World world) {
		setData.removeOffer(regionID, world.getName().toLowerCase());
		return true;
	}

	public void updatePlayer(UUID uuid, String player, long time) {
		setData.updateProfile(uuid, player, time);
	}

}
