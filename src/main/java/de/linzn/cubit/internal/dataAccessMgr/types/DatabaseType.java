package de.linzn.cubit.internal.dataAccessMgr.types;

import java.sql.Connection;
import java.util.UUID;

import org.bukkit.World;

import de.linzn.cubit.internal.dataAccessMgr.OfferData;

public interface DatabaseType {

	public boolean setupDatabase();

	public Connection createConnection();

	public boolean releaseConnection(Connection con);

	// Input

	public boolean set_create_offer(OfferData data);

	public boolean set_remove_offer(String regionID, World world);

	public boolean set_update_profile(UUID uuid, String player, long time);

	// Output

	public long get_last_login_profile(UUID uuid);

	public String get_profile_name(UUID uuid);

	public long get_last_login_profile(String p);

	public OfferData get_offer(String regionID, World world);

	public boolean get_is_offer(String regionID, World world);

	// Other
	public String get_formate_date(long date);

}
