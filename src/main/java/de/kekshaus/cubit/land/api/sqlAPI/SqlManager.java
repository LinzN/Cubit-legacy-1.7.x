package de.kekshaus.cubit.land.api.sqlAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.sqlAPI.getData.GetData;
import de.kekshaus.cubit.land.api.sqlAPI.handler.OfferData;
import de.kekshaus.cubit.land.api.sqlAPI.setData.SetData;
import de.kekshaus.cubit.land.api.sqlAPI.setup.SetupConnection;

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

	public String getLastLoginFormated(UUID uuid) {
		return new SimpleDateFormat("dd.MM.yyyy '-' HH:mm 'Uhr'").format(new Date(getTimeStamp(uuid)));

	}

	public OfferData getOfferData(String regionID, String world) {
		return getData.getOfferData(regionID, world);
	}

	public boolean isOffered(String regionID, String world) {
		return getData.isOffered(regionID, world);
	}

	public boolean setOfferData(OfferData data) {
		setData.setOffer(data);
		return true;
	}

	public boolean removeOfferData(String regionID, String world) {
		setData.removeOffer(regionID, world);
		return true;
	}

	public void updatePlayer(UUID uuid, String player, long time) {
		setData.updateProfile(uuid, player, time);
	}

}
