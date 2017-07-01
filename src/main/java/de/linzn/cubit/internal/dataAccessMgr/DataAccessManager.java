package de.linzn.cubit.internal.dataAccessMgr;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dataAccessMgr.types.DatabaseType;
import de.linzn.cubit.internal.dataAccessMgr.types.MySqlType;
import de.linzn.cubit.internal.dataAccessMgr.types.SqliteType;

public class DataAccessManager {
	
	private CubitBukkitPlugin plugin;
	public DatabaseType databaseType;
	private boolean useMysql;
	
	public DataAccessManager(CubitBukkitPlugin plugin){
		plugin.getLogger().info("Loading DataAccessManager");
		this.plugin = plugin;
		this.useMysql = this.plugin.getYamlManager().getSettings().sqlUse;
		
		if (this.useMysql){
			this.databaseType = new MySqlType();
			if (!this.databaseType.setupDatabase()){
				System.out.println("Failed to load mysql! Falling back to sqlite!");
				this.databaseType = new SqliteType();
				if(!this.databaseType.setupDatabase()){
					System.out.println("Failed to fallback to sqlite. Something went extremely wrong!");
				}
			}
		} else {
			this.databaseType = new SqliteType();
			if(!this.databaseType.setupDatabase()){
				System.out.println("Failed to load sqlite! . Something went wrong!");
			}
		}
	}

}
