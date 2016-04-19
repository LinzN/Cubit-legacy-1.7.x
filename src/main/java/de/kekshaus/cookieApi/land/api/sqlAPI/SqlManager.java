package de.kekshaus.cookieApi.land.api.sqlAPI;

import java.util.UUID;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.api.sqlAPI.getData.GetData;
import de.kekshaus.cookieApi.land.api.sqlAPI.handler.OfferData;
import de.kekshaus.cookieApi.land.api.sqlAPI.setData.SetData;
import de.kekshaus.cookieApi.land.api.sqlAPI.setup.SetupConnection;

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

	public OfferData getOfferData(OfferData data) {
		return getData.getOfferData(data);
	}

	public boolean hasOfferData(OfferData data) {
		return getData.hasOfferData(data);
	}

	public void setOfferData(OfferData data) {
		setData.setOffer(data);
	}

	public void removeOfferData(OfferData data) {
		setData.removeOffer(data);
	}

}
