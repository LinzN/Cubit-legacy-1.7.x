package de.linzn.cubit.internal.databaseMgr;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.databaseMgr.types.DatabaseType;
import de.linzn.cubit.internal.databaseMgr.types.MySqlType;
import de.linzn.cubit.internal.databaseMgr.types.SqliteType;

public class DataManager {
	
	private CubitBukkitPlugin plugin;
	public DatabaseType databaseType;
	private boolean useMysql;
	
	public DataManager(CubitBukkitPlugin plugin){
		plugin.getLogger().info("Loading DataManager");
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
