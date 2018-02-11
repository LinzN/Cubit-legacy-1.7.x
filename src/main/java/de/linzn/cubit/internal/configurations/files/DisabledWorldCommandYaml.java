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

package de.linzn.cubit.internal.configurations.files;

import de.linzn.cubit.internal.configurations.setup.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisabledWorldCommandYaml {


    public HashMap<String, List<String>> disabledWorldCommandList;
    private CustomConfig configFile;

    public DisabledWorldCommandYaml(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }


    public void setup() {
        disabledWorldCommandList = new HashMap<>();
        for (World world : Bukkit.getWorlds()) {
            List<String> commands = new ArrayList<>();
            commands.add("testcommand");
            List<String> excluded_worlds = (List<String>) this.getStringList(world.getName(), commands);
            disabledWorldCommandList.put(world.getName(), excluded_worlds);
        }

    }

    public Object getObjectValue(String path, Object defaultValue) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, defaultValue);
        }
        return this.configFile.get(path);

    }

    public Object getStringList(String path, List<String> list) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, list);
        }
        return this.configFile.getStringList(path);

    }

    public CustomConfig getFile() {
        return this.configFile;
    }

}
