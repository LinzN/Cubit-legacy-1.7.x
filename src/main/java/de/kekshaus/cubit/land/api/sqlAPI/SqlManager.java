package de.kekshaus.cubit.land.api.sqlAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.World;

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

	public OfferData getOfferData(String regionID, World world) {
		return getData.getOfferData(regionID, world.getName().toLowerCase());
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
