/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.cubit.internal.dataBase;

import de.linzn.cubit.bukkit.plugin.CubitBukkitPlugin;
import de.linzn.cubit.internal.dataBase.types.DatabaseType;
import de.linzn.cubit.internal.dataBase.types.MySqlType;
import de.linzn.cubit.internal.dataBase.types.SqliteType;

public class DatabaseManager {

    public DatabaseType databaseType;
    private CubitBukkitPlugin plugin;
    private boolean useMysql;

    public DatabaseManager(CubitBukkitPlugin plugin) {
        plugin.getLogger().info("[Setup] DatabaseManager");
        this.plugin = plugin;
        this.useMysql = this.plugin.getYamlManager().getSettings().sqlUse;

        if (this.useMysql) {
            this.databaseType = new MySqlType();
            if (!this.databaseType.setupDatabase()) {
                System.out.println("Failed to load mysql! Falling back to sqlite!");
                this.databaseType = new SqliteType();
                if (!this.databaseType.setupDatabase()) {
                    System.out.println("Failed to fallback to sqlite. Something went extremely wrong!");
                }
            }
        } else {
            this.databaseType = new SqliteType();
            if (!this.databaseType.setupDatabase()) {
                System.out.println("Failed to load sqlite! . Something went wrong!");
            }
        }
    }

}
