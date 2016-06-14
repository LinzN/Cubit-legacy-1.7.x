package de.kekshaus.cubit.land.api.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.kekshaus.cubit.land.Landplugin;
import de.kekshaus.cubit.land.api.database.sqlAPI.SqlManager;
import de.kekshaus.cubit.land.api.database.sqlAPI.handler.OfferData;
import de.kekshaus.cubit.land.api.database.yamlAPI.YamlManager;

public class DatabaseManager {

	private Landplugin plugin;
	private SqlManager sqlMrg;
	private YamlManager yamlMrg;
	private boolean useSql;

	public DatabaseManager(Landplugin plugin) {
		this.plugin = plugin;
		this.useSql = this.plugin.getLandConfig().sqlUse;

		if (this.useSql) {
			sqlMrg = new SqlManager(this.plugin);
		} else {
			yamlMrg = new YamlManager(this.plugin);
		}

	}

	public boolean link() {
		if (this.useSql) {
			return this.sqlMrg.link();
		} else {
			return this.yamlMrg.link();
		}
	}

	public long getTimeStamp(UUID uuid) {
		if (this.useSql) {
			return sqlMrg.getTimeStamp(uuid);
		} else {
			return Bukkit.getOfflinePlayer(uuid).getLastPlayed();
		}
	}

	public String getLastLoginFormated(UUID uuid) {
		return new SimpleDateFormat("dd.MM.yyyy '-' HH:mm 'Uhr'").format(new Date(getTimeStamp(uuid)));

	}

	public OfferData getOfferData(String regionID, World world) {
		if (this.useSql) {
			return sqlMrg.getOfferData(regionID, world);
		} else {
			return yamlMrg.getOfferData(regionID, world);
		}
	}

	public boolean isOffered(String regionID, World world) {
		if (this.useSql) {
			return sqlMrg.isOffered(regionID, world);
		} else {
			return yamlMrg.isOffered(regionID, world);
		}
	}

	public boolean setOfferData(OfferData data) {
		if (this.useSql) {
			return sqlMrg.setOfferData(data);
		} else {
			return yamlMrg.setOfferData(data);
		}
	}

	public boolean removeOfferData(String regionID, World world) {
		if (this.useSql) {
			return sqlMrg.removeOfferData(regionID, world);
		} else {
			return yamlMrg.removeOfferData(regionID, world);
		}
	}

	public void updatePlayer(UUID uuid, String player, long time) {
		if (this.useSql) {
			sqlMrg.updatePlayer(uuid, player, time);
		}
	}

}
