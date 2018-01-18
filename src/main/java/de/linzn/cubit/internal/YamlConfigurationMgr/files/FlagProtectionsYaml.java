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

package de.linzn.cubit.internal.YamlConfigurationMgr.files;

import de.linzn.cubit.internal.YamlConfigurationMgr.setup.CustomConfig;

public class FlagProtectionsYaml {

    public boolean worldRegionPacketFire;
    public boolean worldRegionPacketLock;
    public boolean worldRegionPacketMonster;
    public boolean worldRegionPacketPotion;
    public boolean worldRegionPacketPVP;
    public boolean worldRegionPacketTNT;
    public boolean shopRegionPacketFire;
    public boolean shopRegionPacketLock;
    public boolean shopRegionPacketMonster;
    public boolean shopRegionPacketPotion;
    public boolean shopRegionPacketPVP;
    public boolean shopRegionPacketTNT;
    public boolean serverRegionPacketFire;
    public boolean serverRegionPacketLock;
    public boolean serverRegionPacketMonster;
    public boolean serverRegionPacketPotion;
    public boolean serverRegionPacketPVP;
    public boolean serverRegionPacketTNT;
    private CustomConfig configFile;

    public FlagProtectionsYaml(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }

    public void setup() {
        worldRegionPacketFire = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.fire", true);
        worldRegionPacketLock = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.lock", true);
        worldRegionPacketMonster = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.monster", true);
        worldRegionPacketPotion = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.potion", true);
        worldRegionPacketPVP = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.pvp", true);
        worldRegionPacketTNT = (boolean) this.getObjectValue("flags.worldregion.defaultProtection.tnt", true);

        shopRegionPacketFire = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.fire", true);
        shopRegionPacketLock = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.lock", true);
        shopRegionPacketMonster = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.monster", true);
        shopRegionPacketPotion = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.potion", true);
        shopRegionPacketPVP = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.pvp", true);
        shopRegionPacketTNT = (boolean) this.getObjectValue("flags.shopregion.defaultProtection.tnt", true);

        serverRegionPacketFire = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.fire", true);
        serverRegionPacketLock = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.lock", true);
        serverRegionPacketMonster = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.monster", true);
        serverRegionPacketPotion = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.potion", true);
        serverRegionPacketPVP = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.pvp", true);
        serverRegionPacketTNT = (boolean) this.getObjectValue("flags.serverregion.defaultProtection.tnt", true);
    }

    public Object getObjectValue(String path, Object defaultValue) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, defaultValue);
        }
        return this.configFile.get(path);

    }

    public CustomConfig getFile() {
        return this.configFile;
    }

}
