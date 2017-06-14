package de.kekshaus.cubit.api.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.World;

import de.kekshaus.cubit.api.database.engine.SQLIEngine;
import de.kekshaus.cubit.plugin.Landplugin;

public class DatabaseManager {

	private Landplugin plugin;
	private boolean useSql;
	
	private SQLIEngine engine;
	private DatabaseInputOutput databaseIO;

	public DatabaseManager(Landplugin plugin) {
		plugin.getLogger().info("Loading DatabaseManager");
		this.plugin = plugin;
		String database = Landplugin.inst().getYamlManager().getSettings().sqlDataBase;
		int port = Landplugin.inst().getYamlManager().getSettings().sqlPort;
		String host = Landplugin.inst().getYamlManager().getSettings().sqlHostname;
		String username = Landplugin.inst().getYamlManager().getSettings().sqlUser;
		String password = Landplugin.inst().getYamlManager().getSettings().sqlPassword;
		
		this.useSql = this.plugin.getYamlManager().getSettings().sqlUse;
		
		if (this.useSql){
			this.engine = new SQLIEngine("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password, this.useSql);
		} else {
			this.engine = new SQLIEngine("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/database.db", username, password, this.useSql);
		}
		this.databaseIO = new DatabaseInputOutput(this.engine);
	}

	
	public boolean useSQL(){
		return this.useSql;
	}


	public long getTimeStamp(UUID uuid) {
		long timeStamp = this.databaseIO.get_last_login_profile(uuid);
		if (timeStamp == 0) {
			timeStamp = this.plugin.getYamlManager().getSettings().cubitSetupDate;
		}
		return timeStamp;

	}

	public String getLastLoginFormated(UUID uuid) {
		return new SimpleDateFormat("dd.MM.yyyy '-' HH:mm 'Uhr'").format(new Date(getTimeStamp(uuid)));

	}

	public OfferData getOfferData(String regionID, World world) {
		return this.databaseIO.get_offer(regionID, world);

	}

	public boolean isOffered(String regionID, World world) {
		return this.databaseIO.get_is_offer(regionID, world);

	}

	public boolean setOfferData(OfferData data) {
		return this.databaseIO.set_create_offer(data);

	}

	public boolean removeOfferData(String regionID, World world) {
		return this.databaseIO.set_remove_offer(regionID, world);

	}

	public void updateProfile(UUID uuid, String player, long time) {
		this.databaseIO.set_update_profile(uuid, player, time);

	}
	
	public String getProfileName(UUID uuid) {
		return this.databaseIO.get_profile_name(uuid);

	}

}
