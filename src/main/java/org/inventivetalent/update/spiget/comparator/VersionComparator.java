/*
 * Copyright (C) 2017. MineGaming - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 */

package org.inventivetalent.update.spiget.comparator;

public abstract class VersionComparator {

    /**
     * Compares versions by checking if the version strings are equal
     */
    public static final VersionComparator EQUAL = new VersionComparator() {
        @Override
        public boolean isNewer(String currentVersion, String checkVersion) {
            return !currentVersion.equals(checkVersion);
        }
    };

    /**
     * Compares versions by their Sematic Version
     * (<code>Major.Minor.Patch</code>,
     * <a href="http://semver.org/">semver.org</a>). Removes dots and compares
     * the resulting Integer values
     */
    public static final VersionComparator SEM_VER = new VersionComparator() {
        @Override
        public boolean isNewer(String currentVersion, String checkVersion) {
            String currentVersionCut = getOnlyNumerics(currentVersion);
            String checkVersionCut = getOnlyNumerics(checkVersion);

            try {
                int current = Integer.parseInt(currentVersionCut);
                int check = Integer.parseInt(checkVersionCut);

                return check > current;
            } catch (NumberFormatException e) {
                System.err.println("[SpigetUpdate] Invalid SemVer versions specified [" + currentVersionCut + "] ["
                        + checkVersionCut + "]");
            }
            return false;
        }
    };

    /**
     * Called to check if a version is newer
     *
     * @param currentVersion Current version of the plugin
     * @param checkVersion   Version to check
     * @return <code>true</code> if the checked version is newer
     */
    public abstract boolean isNewer(String currentVersion, String checkVersion);

    public String getOnlyNumerics(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder strBuff = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

}
