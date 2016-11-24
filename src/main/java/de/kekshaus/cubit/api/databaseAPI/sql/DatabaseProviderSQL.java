package de.kekshaus.cubit.api.databaseAPI.sql;

import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubi.api.classes.interfaces.IDatabaseConnector;
import de.kekshaus.cubit.api.databaseAPI.OfferData;
import de.kekshaus.cubit.api.databaseAPI.sql.getData.DataBaseSQLGetData;
import de.kekshaus.cubit.api.databaseAPI.sql.setData.DataBaseSQLSetData;
import de.kekshaus.cubit.api.databaseAPI.sql.setup.DataBaseSQLSetup;
import de.kekshaus.cubit.plugin.Landplugin;

public class DatabaseProviderSQL implements IDatabaseConnector {

	private Landplugin plugin;
	private DataBaseSQLSetData setData;
	private DataBaseSQLGetData getData;

	public DatabaseProviderSQL(Landplugin plugin) {
		this.plugin = plugin;
		this.setData = new DataBaseSQLSetData();
		this.getData = new DataBaseSQLGetData();
	}

	@Override
	public boolean link() {
		return DataBaseSQLSetup.setup(plugin);
	}

	@Override
	public long getTimeStamp(UUID uuid) {
		return getData.getTimeStamp(uuid);
	}

	@Override
	public OfferData getOfferData(String regionID, World world) {
		return getData.getOfferData(regionID, world);
	}

	@Override
	public boolean isOffered(String regionID, World world) {
		return getData.isOffered(regionID, world.getName().toLowerCase());
	}

	@Override
	public boolean setOfferData(OfferData data) {
		setData.setOffer(data);
		return true;
	}

	@Override
	public boolean removeOfferData(String regionID, World world) {
		setData.removeOffer(regionID, world.getName().toLowerCase());
		return true;
	}

	@Override
	public void updatePlayer(UUID uuid, String player, long time) {
		setData.updateProfile(uuid, player, time);
	}

}
