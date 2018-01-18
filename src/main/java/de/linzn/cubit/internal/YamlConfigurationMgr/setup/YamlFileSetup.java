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

package de.linzn.cubit.internal.YamlConfigurationMgr.setup;

import de.linzn.cubit.internal.YamlConfigurationMgr.files.FlagProtectionsYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.LanguageYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.LimitYaml;
import de.linzn.cubit.internal.YamlConfigurationMgr.files.SettingsYaml;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class YamlFileSetup {

    public SettingsYaml settings;
    public LanguageYaml language;
    public LimitYaml limit;
    public FlagProtectionsYaml flag;
    private Plugin plugin;

    public YamlFileSetup(Plugin plugin) {
        this.plugin = plugin;

        File configDirectory = new File(this.plugin.getDataFolder(), "configs");
        if (!configDirectory.exists()) {
            try {
                boolean limits = configDirectory.mkdirs();
                if (limits) {
                    this.plugin.getLogger().info("Created config directory");
                } else {
                    this.plugin.getLogger().severe("Error while creating config directory");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File languageDirectory = new File(this.plugin.getDataFolder(), "languages");
        if (!languageDirectory.exists()) {
            try {
                boolean language = languageDirectory.mkdirs();
                if (language) {
                    this.plugin.getLogger().info("Created languages directory");
                } else {
                    this.plugin.getLogger().severe("Error while creating languages directory");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CustomConfig languageConfig = new CustomConfig(this.plugin, languageDirectory, "language.yml");
        this.language = new LanguageYaml(languageConfig);

        CustomConfig limitConfig = new CustomConfig(this.plugin, configDirectory, "entityLimiter.yml");
        this.limit = new LimitYaml(limitConfig);

        CustomConfig flagConfig = new CustomConfig(this.plugin, configDirectory, "defaultProtections.yml");
        this.flag = new FlagProtectionsYaml(flagConfig);

        CustomConfig settingsConfig = new CustomConfig(this.plugin, this.plugin.getDataFolder(), "settings.yml");
        this.settings = new SettingsYaml(settingsConfig);

    }
}
