package de.kekshaus.cubit.land.api.database.sqlAPI;

import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.database.sqlAPI.getData.GetData;
import de.kekshaus.cubit.land.api.database.sqlAPI.handler.OfferData;
import de.kekshaus.cubit.land.api.database.sqlAPI.setData.SetData;
import de.kekshaus.cubit.land.api.database.sqlAPI.setup.SetupConnection;

public class SqlManager {

	private Landplugin plugin;
	private SetData setData;
	private GetData getData;

	public SqlManager(Landplugin plugin) {
		this.plugin = plugin;
		this.setData = new SetData();
		this.getData = new GetData();
	}

	public boolean link() {
		return SetupConnection.setup(plugin);
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
