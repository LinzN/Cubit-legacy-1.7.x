package de.linzn.cubit.internal.dataAccessMgr.types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.World;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.databaseMgr.OfferData;

public class MySqlType implements DatabaseType{
	private String host;
	private int port;
	private String database;
	private String username;
	private String password;

	@Override
	public boolean setupDatabase() {
		 this.database = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlDataBase;
		 this.port = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlPort;
		 this.host = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlHostname;
		 this.username = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlUser;
		 this.password = CubitBukkitPlugin.inst().getYamlManager().getSettings().sqlPassword;
		// TODO Auto-generated method stub
		 
		 Connection con = this.createConnection();
		try {
			Statement state = con.createStatement();
			state.executeUpdate("CREATE TABLE IF NOT EXISTS offerManager (Id int NOT NULL AUTO_INCREMENT, regionID text, world text, uuid text, value double, PRIMARY KEY (Id));");
			state.executeUpdate("CREATE TABLE IF NOT EXISTS uuidcache (Id int NOT NULL AUTO_INCREMENT, UUID text, NAME text, TIMESTAMP bigint, PRIMARY KEY (id));");
			state.close();
			return this.releaseConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Connection createConnection() {
		Connection con;
		// TODO Auto-generated method stub
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + database + "?autoReconnect=true&useSSL=false", this.username, this.password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return con;
	}

	@Override
	public boolean releaseConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean set_create_offer(OfferData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean set_remove_offer(String regionID, World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean set_update_profile(UUID uuid, String player, long time) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long get_last_login_profile(UUID uuid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String get_profile_name(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long get_last_login_profile(String p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OfferData get_offer(String regionID, World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean get_is_offer(String regionID, World world) {
		// TODO Auto-generated method stub
		return false;
	}

}
