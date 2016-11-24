package de.kekshaus.cubi.api.classes.interfaces;

import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.api.databaseAPI.OfferData;

public interface IDatabaseConnector {

	public boolean link();

	public long getTimeStamp(UUID uuid);

	public OfferData getOfferData(String regionID, World world);

	public boolean isOffered(String regionID, World world);

	public boolean setOfferData(OfferData data);

	public boolean removeOfferData(String regionID, World world);

	public void updatePlayer(UUID uuid, String player, long time);

}
