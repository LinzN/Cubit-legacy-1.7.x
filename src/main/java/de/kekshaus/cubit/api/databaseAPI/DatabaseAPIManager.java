package de.kekshaus.cubit.api.databaseAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.api.databaseAPI.sql.DatabaseProviderSQL;
import de.kekshaus.cubit.api.databaseAPI.yaml.DatabaseProviderYAML;
import de.kekshaus.cubit.plugin.Landplugin;

public class DatabaseAPIManager {

	private Landplugin plugin;
	private IDatabaseProvider databaseProvider;
	private boolean useSql;

	public DatabaseAPIManager(Landplugin plugin) {
		plugin.getLogger().info("Loading DatabaseAPIManager");
		this.plugin = plugin;
		this.useSql = this.plugin.getYamlManager().getSettings().sqlUse;
		if (this.useSql) {
			plugin.getLogger().info("Using MYSQL database");
			this.databaseProvider = new DatabaseProviderSQL(plugin);
		} else {
			plugin.getLogger().info("Using YAML database");
			this.databaseProvider = new DatabaseProviderYAML(plugin);
		}

	}

	public boolean link() {
		return this.databaseProvider.link();

	}

	public long getTimeStamp(UUID uuid) {
		long timeStamp = databaseProvider.getTimeStamp(uuid);
		if (timeStamp == 0){
			timeStamp = this.plugin.getYamlManager().getSettings().cubitSetupDate;
		}
		return timeStamp;

	}

	public String getLastLoginFormated(UUID uuid) {
		return new SimpleDateFormat("dd.MM.yyyy '-' HH:mm 'Uhr'").format(new Date(getTimeStamp(uuid)));

	}

	public OfferData getOfferData(String regionID, World world) {
		return databaseProvider.getOfferData(regionID, world);

	}

	public boolean isOffered(String regionID, World world) {
		return databaseProvider.isOffered(regionID, world);

	}

	public boolean setOfferData(OfferData data) {
		return databaseProvider.setOfferData(data);

	}

	public boolean removeOfferData(String regionID, World world) {
		return databaseProvider.removeOfferData(regionID, world);

	}

	public void updatePlayer(UUID uuid, String player, long time) {
		databaseProvider.updatePlayer(uuid, player, time);

	}

}
