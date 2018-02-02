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

public class CommandsYaml {

    /* Land commands */
    public boolean land_buy;
    public boolean land_buyup;
    public boolean land_offer;
    public boolean land_sell;
    public boolean land_takeoffer;

    /* Shop commands */
    public boolean shop_buy;
    public boolean shop_sell;


    private CustomConfig configFile;

    public CommandsYaml(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }

    public void setup() {
        land_buy = (boolean) this.getObjectValue("land.buy", true);
        land_buyup = (boolean) this.getObjectValue("land.buyup", true);
        land_offer = (boolean) this.getObjectValue("land.offer", true);
        land_sell = (boolean) this.getObjectValue("land.sell", true);
        land_takeoffer = (boolean) this.getObjectValue("land.takeoffer", true);

        shop_buy = (boolean) this.getObjectValue("shop.buy", true);
        shop_sell = (boolean) this.getObjectValue("shop.sell", true);


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
