/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package org.inventivetalent.update.spiget;

public interface UpdateCallback {

    /**
     * Called when a new version was found
     * <p>
     * Use {@link SpigetUpdateAbstract#getLatestResourceInfo()} to get all
     * resource details
     *
     * @param newVersion      the new version's name
     * @param downloadUrl     URL to download the update
     * @param canAutoDownload whether this update can be downloaded automatically
     */
    void updateAvailable(String newVersion, String downloadUrl, boolean canAutoDownload);

    /**
     * Called when no update was found
     */
    void upToDate();

}
