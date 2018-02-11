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
import org.bukkit.ChatColor;

public class LanguageYaml {

    /* Info MSG */
    public String startBiomeChange;
    /* Error MSG */
    public String noConsoleMode;
    public String disabledCommand;
    public String errorInTask;
    public String errorCommand;
    public String errorNoCommand;
    public String errorNoPermission;
    public String errorNoLandPermission;
    public String errorNoLandFound;
    public String errorNoValidLandFound;
    public String noNumberFound;
    public String noEnabledWorld;
    public String reachLimit;
    public String notABiome;
    public String pageNotFound;
    public String noSnapshot;
    public String alreadySnapshot;
    public String noValidWEAdapter;
    public String disabledSnapshots;
    /* Success MSG */
    public String buySuccess;
    public String createShopLand;
    public String deleteShopLand;
    public String sellSuccess;
    public String flagSwitchSuccess;
    public String addMemberSuccess;
    public String removeMemberSuccess;
    public String offerAddSuccess;
    public String offerRemoveSuccess;
    public String kickedInfo;
    public String kickInfo;
    public String isFreeAndBuyable;
    public String changedBiome;
    public String savedSnapshot;
    public String restoredSnapshot;
    public String resetSnapshot;
    /* UnSuccess MSG */
    public String buyIsAlreadyLand;
    public String isAlreadyLand;
    public String notToLongOffline;
    public String notEnoughMoney;
    public String takeOwnLand;
    public String notOffered;
    public String noRegionsFound;
    public String noSnapshotsFound;
    public String wrongArguments;
    /* Header for Plugin */
    public String landHeader;
    public String landBiomeListHeader;
    public String flagStateActive;
    public String flagStateInactive;
    /* Land List Page */
    public String landListHeader;
    public String landListEntry;
    /* Land Info Page */
    public String landInfoA1;
    public String landInfoA2;
    public String landInfoA3;
    public String landInfoE1;
    public String landInfoE1A1;
    public String landInfoE2;
    public String landInfoE3;
    public String landInfoE4;
    public String landInfoE5;
    public String landInfoE6;
    /* Page 1 Help for Land Command */
    public String landHelpHeaderP1;
    public String landHelpE1P1;
    public String landHelpE2P1;
    public String landHelpE3P1;
    public String landHelpE4P1;
    public String landHelpE5P1;
    public String landHelpE6P1;
    public String landHelpE7P1;
    /* Page 2 Help for Land Command */
    public String landHelpHeaderP2;
    public String landHelpE1P2;
    public String landHelpE2P2;
    public String landHelpE3P2;
    public String landHelpE4P2;
    public String landHelpE5P2;
    public String landHelpBottomP2;
    /* Page 3 Help for Land Command */
    public String landHelpHeaderP3;
    public String landHelpE1P3;
    public String landHelpE2P3;
    public String landHelpE3P3;
    public String landHelpBottomP3;
    /* Page 4 Help for Land Command */
    public String landHelpHeaderP4;
    public String landHelpE1P4;
    public String landHelpE2P4;
    public String landHelpE3P4;
    public String landHelpE4P4;
    public String landHelpE5P4;
    public String landHelpBottomP4;
    /* Page 5 Help for Land Command */
    public String landHelpHeaderP5;
    public String landHelpE1P5;
    public String landHelpE2P5;
    public String landHelpE3P5;
    public String landHelpE4P5;
    public String landHelpE5P5;
    public String landHelpBottomP5;
    /* Page 1 Help for Shop Command */
    public String shopHelpHeaderP1;
    public String shopHelpE1P1;
    public String shopHelpE2P1;
    public String shopHelpE3P1;
    public String shopHelpE4P1;
    public String shopHelpBottomP1;
    /* Page 2 Help for Shop Command */
    public String shopHelpHeaderP2;
    public String shopHelpE1P2;
    public String shopHelpE2P2;
    public String shopHelpBottomP2;
    /* Page 3 Help for Shop Command */
    public String shopHelpHeaderP3;
    public String shopHelpE1P3;
    public String shopHelpE2P3;
    public String shopHelpE3P3;
    public String shopHelpBottomP3;
    /* Page 1 Help for Admin Command */
    public String adminHelpHeaderP1;
    public String adminHelpE1P1;
    public String adminHelpE2P1;
    public String adminHelpE3P1;
    public String adminHelpE4P1;
    public String adminHelpE5P1;
    public String adminHelpE6P1;
    /* Page 2 Help for Admin Command */
    public String adminHelpHeaderP2;
    public String adminHelpE1P2;
    public String adminHelpE2P2;
    public String adminHelpE3P2;
    public String adminHelpE4P2;
    /* Page 3 Help for Admin Command */
    public String adminHelpHeaderP3;
    public String adminHelpE1P3;
    public String adminHelpE2P3;
    public String adminHelpE3P3;
    public String adminHelpE4P3;
    public String adminHelpE5P3;
    /* Page 4 Help for Admin Command */
    public String adminHelpHeaderP4;
    public String adminHelpE1P4;
    public String adminHelpE2P4;
    public String adminHelpE3P4;
    /* Page 5 Help for Admin Command */
    public String adminHelpHeaderP5;
    public String adminHelpE1P5;
    public String adminHelpE2P5;
    public String adminHelpE3P5;
    public String adminHelpE4P5;
    /* Page 1 Help for Cubit Command */
    public String cubitHelpHeaderP1;
    public String cubitHelpE1P1;
    public String cubitHelpE2P1;
    /* Snapshots List */
    public String landListsnapshotsHeader;
    public String landListsnapshotsEntry;

    /* Land confirm Task like biome */
    public String landEditConfirmTask;
    public String landEditConfirmTaskCancel;
    public String landEditConfirmInfoBiome;
    public String landEditConfirmInfoReset;


    private CustomConfig configFile;

    public LanguageYaml(CustomConfig configFile) {
        this.configFile = configFile;
        setup();
        this.configFile.saveAndReload();
    }

    private void setup() {

        /* Info MSG */
        startBiomeChange = this.getLanguageString("startBiomeChange", "&2Starting biome change for region {regionID}!");
        /* Error MSG */
        noConsoleMode = this.getLanguageString("noConsoleMode", "&4Sorry this is not available in console mode!");
        disabledCommand = this.getLanguageString("disabledCommand", "&4Sorry this command is disabled!");
        noEnabledWorld = this.getLanguageString("noEnabledWorld",
                "&4Sorry but this command is not available in this world!");
        errorInTask = this.getLanguageString("errorInTask", "&4An internal error occurred during: [&e{error}&4]");
        errorCommand = this.getLanguageString("errorCommand",
                "&4An error occurred during execution command [&e{command}&4]!");
        errorNoCommand = this.getLanguageString("errorNoCommand", "&cUnknown command! Use &e{command}&c for help!");
        errorNoPermission = this.getLanguageString("errorNoPermission", "&4You don have access to that command! :(");
        errorNoLandPermission = this.getLanguageString("errorNoLandPermission",
                "&cYou don have access for region {regionID}! :(");
        errorNoLandFound = this.getLanguageString("errorNoLandFound", "&cNo region found here! Sorry :(");
        errorNoValidLandFound = this.getLanguageString("errorNoLandFound",
                "&cNo valid {type} region found here! Sorry :(");
        noNumberFound = this.getLanguageString("noNumberFound", "&cThis is not a valid number");
        reachLimit = this.getLanguageString("reachLimit", "&cSorry you reached the limit for this regionType!");
        notABiome = this.getLanguageString("notABiome", "&cThis is not a valid biomename: {biome}!");
        pageNotFound = this.getLanguageString("pageNotfound", "&cThis is not a valid page");
        noSnapshot = this.getLanguageString("noSnapshot", "&cThere is no snapshot with this name!");
        alreadySnapshot = this.getLanguageString("alreadySnapshot", "&cThis snapshot exist already!");
        noValidWEAdapter = this.getLanguageString("noValidWEAdapter",
                "&cWorldEdit has no valid bukkit adapter for this server. Please update WorldEdit!");
        disabledSnapshots = this.getLanguageString("disabledSnapshots", "&cSnapshots are disabled in settings!");
        /* Success MSG */
        buySuccess = this.getLanguageString("buySuccess", "&2You bought the region &e{regionID}&2!");
        isFreeAndBuyable = this.getLanguageString("isFreeAndBuyable",
                "&2This region &e{regionID} &2is buyable for &6{price}&2!");
        sellSuccess = this.getLanguageString("sellSuccess", "&2You sold the region &e{regionID}&2 to the server!");
        flagSwitchSuccess = this.getLanguageString("flagSwitchSuccess",
                "&2The system &9&l{flag}-Protection &2switched to &9&l{value}&2!");
        addMemberSuccess = this.getLanguageString("addMemberSuccess",
                "&2You added {member} to the region &e{regionID}&2!");
        removeMemberSuccess = this.getLanguageString("removeMemberSuccess",
                "&2You removed {member} from the region &e{regionID}&2!");
        offerAddSuccess = this.getLanguageString("offerAddSuccess",
                "&2You offer the region &e{regionID}&2 for &e{value}&2!");
        offerRemoveSuccess = this.getLanguageString("offerRemoveSuccess",
                "&2You offer the region &e{regionID}&2 no more!");
        kickedInfo = this.getLanguageString("kickedInfo",
                "&6You kicked from the region {regionID} by the region owner!");
        kickInfo = this.getLanguageString("kickInfo", "&2All non-member kicked from the region &e{regionID}&2!");
        changedBiome = this.getLanguageString("changedBiome",
                "&2You changed the biome from region &e{regionID} &2to &e{biome}&2!");

        savedSnapshot = this.getLanguageString("savedSnapshot",
                "&2You created a new snapshot from the region &e{regionID}&2!");
        restoredSnapshot = this.getLanguageString("restoredSnapshot",
                "&2You restored a snapshot named &e{snapshotName}&2!");
        resetSnapshot = this.getLanguageString("resetSnapshot", "&2You reseted the region &e{regionID}&2!");

        /* UnSuccess MSG */
        buyIsAlreadyLand = this.getLanguageString("buyIsAlreadyLand",
                "&cSorry. This region is already &e{regionID}&c bought by someone!");

        createShopLand = this.getLanguageString("createShop", "&a You created region {regionID}!");
        deleteShopLand = this.getLanguageString("deleteShop", "&a You deleted region {regionID}!");

        isAlreadyLand = this.getLanguageString("isAlreadyLand", "&cHere is already the region &e{regionID}&c!");
        notEnoughMoney = this.getLanguageString("notEnoughMoney", "&cYou can not afford it. Price: &e{cost}&c!");
        takeOwnLand = this.getLanguageString("takeOwnLand", "&cYou can not buy your own region!");
        notOffered = this.getLanguageString("notOffered", "&cThe region {regionID} is not offered!");
        wrongArguments = this.getLanguageString("wrongArguments", "&cWrong arguments: Use {usage}!");
        noRegionsFound = this.getLanguageString("noRegionsFound", "&cNo regions found. Sry!");
        noSnapshotsFound = this.getLanguageString("noSnapshotsFound", "&cNo snapshots found. Sry!");
        notToLongOffline = this.getLanguageString("notToLongOffline", "&cThis player is not long enough offline!");
        /* Header for Plugin */
        landHeader = this.getLanguageString("theme.general.header",
                "&6<<<<<<<<<<<<<<<<<<<<<&2&l|Region Info|&6>>>>>>>>>>>>>>>>>>>>>");

        flagStateActive = this.getLanguageString("theme.general.flagStateActive", "ACTIVE");
        flagStateInactive = this.getLanguageString("theme.general.flagStateInactive", "INACTIVE");

        /* Land Info Page */
        landInfoA1 = this.getLanguageString("theme.landinfo.empty",
                "&2This region [&e{regionID}&2] is buyable. \n&2Buy it with &e/land buy&2. Price &e{cost}&2!");
        landInfoA2 = this.getLanguageString("theme.landinfo.offered",
                "&2This region is offered for &e{value}&2. Type &e/land takeoffer &2if you want to buy it!");
        landInfoA3 = this.getLanguageString("theme.landinfo.buyupable",
                "&2The owner of this region is to long offline. Type &e/land buyup &2if you want to buy it!");
        landInfoE1 = this.getLanguageString("theme.landinfo.regionID", "&2Region: &9{regionID}");
        landInfoE2 = this.getLanguageString("theme.landinfo.landOwner", "&2Owner: &9{owner}");
        landInfoE3 = this.getLanguageString("theme.landinfo.landMember", "&2Members: &5{members}");
        landInfoE4 = this.getLanguageString("theme.landinfo.landArea",
                "&2Area-corner: From [&e{min}&2] to [&e{max}&2]");
        landInfoE5 = this.getLanguageString("theme.landinfo.lastLogin", "&2Last login: &e{time}");
        landInfoE6 = this.getLanguageString("theme.landinfo.flagPackets",
                "&2Security-Systems: {lock}, {monster}, {fire}, {pvp}, {tnt}, {potion}");

        /* Page 1 Help for Land Command */
        landHelpHeaderP1 = this.getLanguageString("theme.helpPage1.header",
                "&6<<<<<<<<<<<<<<<<<&2&l|Region Help|&6>>>>>>>>>>>>>>>>>");
        landHelpE1P1 = this.getLanguageString("theme.helpPage1.help1", "&2 Region informations: &e/land info");
        landHelpE2P1 = this.getLanguageString("theme.helpPage1.help2", "&2 List all your regions: &e/land list (Page)");
        landHelpE3P1 = this.getLanguageString("theme.helpPage1.help3", "&6&lMore commands on the following pages:");
        landHelpE4P1 = this.getLanguageString("theme.helpPage1.help4", "&2 Page 2: Buy - Sell &a/land help 2");
        landHelpE5P1 = this.getLanguageString("theme.helpPage1.help5", "&2 Page 3: Manage Members &a/land help 3");
        landHelpE6P1 = this.getLanguageString("theme.helpPage1.help6", "&2 Page 4: Region flags &a/land help 4");
        landHelpE7P1 = this.getLanguageString("theme.helpPage1.help7", "&2 Page 5: Land edit &a/land help 5");

        /* Page 2 Help for Land Command */
        landHelpHeaderP2 = this.getLanguageString("theme.helpPage2.header", "&6&lBuy - Sell: [Page 2]");
        landHelpE1P2 = this.getLanguageString("theme.helpPage2.help1", "&2 Buy a region: &e/land buy");
        landHelpE2P2 = this.getLanguageString("theme.helpPage2.help2", "&2 Sell a region: &e/land sell");
        landHelpE3P2 = this.getLanguageString("theme.helpPage2.help3", "&2 Take a offered region: &e/land takeoffer");
        landHelpE4P2 = this.getLanguageString("theme.helpPage2.help4", "&2 Offer a region: &e/land offer [Price]");
        landHelpE5P2 = this.getLanguageString("theme.helpPage2.help5", "&2 Buyup outdated regions: &e/land buyup");
        landHelpBottomP2 = this.getLanguageString("theme.helpPage2.bottom", "&a&lMore on page 3 with &6/land help 3");

        /* Page 3 Help for Land Command */
        landHelpHeaderP3 = this.getLanguageString("theme.helpPage3.header", "&6&lManage Members: [Page 3]");
        landHelpE1P3 = this.getLanguageString("theme.helpPage3.help1",
                "&2 Set player build-rights : &e/land add (-all) [player]");
        landHelpE2P3 = this.getLanguageString("theme.helpPage3.help2",
                "&2 Unset player build-rights: &e/land remove (-all) [player]");
        landHelpE3P3 = this.getLanguageString("theme.helpPage3.help3",
                "&2 Kick non-members from a region (teleporting): &e/land kick");
        landHelpBottomP3 = this.getLanguageString("theme.helpPage3.bottom", "&a&lMore on page 4 with &6/land help 4");

        /* Page 4 Help for Land Command */
        landHelpHeaderP4 = this.getLanguageString("theme.helpPage4.header", "&6&lRegion Protection-systems: [Page 4]");
        landHelpE1P4 = this.getLanguageString("theme.helpPage4.help1",
                "&2 Fire Protection: &e/land fire (&aON&e/&cOFF&e)");
        landHelpE2P4 = this.getLanguageString("theme.helpPage4.help2",
                "&2 Access Protection: &e/land lock (&aON&e/&cOFF&e)");
        landHelpE3P4 = this.getLanguageString("theme.helpPage4.help3",
                "&2 PvP Protection: &e/land pvp (&aON&e/&cOFF&e)");
        landHelpE4P4 = this.getLanguageString("theme.helpPage4.help4",
                "&2 TnT Protection: &e/land tnt (&aON&e/&cOFF&e)");
        landHelpE5P4 = this.getLanguageString("theme.helpPage4.help5",
                "&2 Monster Protection: &e/land monster (&aON&e/&cOFF&e)");
        landHelpBottomP4 = this.getLanguageString("theme.helpPage4.bottom", "&a&lMore on page 5 with &6/land help 5");

        /* Page 5 Help for Land Command */
        landHelpHeaderP5 = this.getLanguageString("theme.helpPage5.header", "&6&lLand edit: [Page 5]");
        landHelpE1P5 = this.getLanguageString("theme.helpPage5.help1", "&2 Change biome: &e/land changebiome [Biome]");
        landHelpE2P5 = this.getLanguageString("theme.helpPage5.help2", "&2 List biomes: &e/land listbiomes");
        landHelpE3P5 = this.getLanguageString("theme.helpPage5.help3", "&2 Save snapshot: &e/land save");
        landHelpE4P5 = this.getLanguageString("theme.helpPage5.help4",
                "&2 Restore snapshot: &e/land restore [Snapshot]");
        landHelpE5P5 = this.getLanguageString("theme.helpPage5.help5", "&2 List snapshots: &e/land listsaves");
        landHelpBottomP5 = this.getLanguageString("theme.helpPage5.bottom", "&a&lBack to page 4 &6/land help 4");

        landBiomeListHeader = this.getLanguageString("theme.biomeList.header", "&6&lAll available biomes:");

        /* Page 1 Help for Shop Command */
        shopHelpHeaderP1 = this.getLanguageString("theme.shopHelpPage1.header",
                "&6<<<<<<<<<<<<<<<<<&2&l|Shop Help|&6>>>>>>>>>>>>>>>>>");
        shopHelpE1P1 = this.getLanguageString("theme.shopHelpPage1.help1", "&2 Shop informations: &e/shop info");
        shopHelpE2P1 = this.getLanguageString("theme.shopHelpPage1.help2",
                "&2 List all your shops: &e/shop list [Page]");
        shopHelpE3P1 = this.getLanguageString("theme.shopHelpPage1.help3", "&6&lMore commands on the following pages:");
        shopHelpE4P1 = this.getLanguageString("theme.shopHelpPage1.help4", "&2 Page 2: Buy - Sell &a/shop help 2");
        shopHelpBottomP1 = this.getLanguageString("theme.shopHelpPage1.bottom",
                "&2 Page 3: Manage Members &a/shop help 3");

        /* Page 2 Help for Shop Command */
        shopHelpHeaderP2 = this.getLanguageString("theme.shopHelpPage2.header", "&6&lBuy - Sell: [Page 2]");
        shopHelpE1P2 = this.getLanguageString("theme.shopHelpPage2.help1", "&2 Buy a shop: &e/shop buy");
        shopHelpE2P2 = this.getLanguageString("theme.shopHelpPage2.help2", "&2 Sell a shop: &e/shop sell");
        shopHelpBottomP2 = this.getLanguageString("theme.shopHelpPage2.bottom",
                "&a&lMore on page 3 with &6/shop help 3");

        /* Page 3 Help for Land Command */
        shopHelpHeaderP3 = this.getLanguageString("theme.shopHelpPage3.header", "&6&lManage Members: [Page 3]");
        shopHelpE1P3 = this.getLanguageString("theme.shopHelpPage3.help1",
                "&2 Set player build-rights : &e/shop add (-all) [player]");
        shopHelpE2P3 = this.getLanguageString("theme.shopHelpPage3.help2",
                "&2 Unset player build-rights: &e/shop remove (-all) [player]");
        shopHelpE3P3 = this.getLanguageString("theme.shopHelpPage3.help3",
                "&2 Kick non-members from a shop (teleporting): &4/shop kick");
        shopHelpBottomP3 = this.getLanguageString("theme.shopHelpPage3.bottom", "&a&lBack to page 2 &6/shop help 2");

        /* Land List Command */
        landListHeader = this.getLanguageString("theme.landList.header",
                "&6&lRegion Count: &r&2{count} &6&l- Entries &r&2{entryMin} &6&lfrom &r&2{entryMax}");
        landListEntry = this.getLanguageString("theme.landList.entry",
                "&a{counter}. &6{regionID} &a(&efrom &a- &eto&a) [&e{minPoints} &a- &e{maxPoints}&a]");

        /* List snapshots Command */
        landListsnapshotsHeader = this.getLanguageString("theme.listsnapshots.header",
                "&6&lSnapshots Count: &r&2{count} &6&l- Entries &r&2{entryMin} &6&lfrom &r&2{entryMax}");
        landListsnapshotsEntry = this.getLanguageString("theme.listsnapshots.entry",
                "&a{counter}. &6{snapshotName} &a [&e{biome}&a]");

        /* Page 1 Help for Admin Command */
        adminHelpHeaderP1 = this.getLanguageString("theme.adminHelpPage1.header",
                "&6<<<<<<<<<<<<<<<<<&2&l|Admin Help|&6>>>>>>>>>>>>>>>>>");
        adminHelpE1P1 = this.getLanguageString("theme.adminHelpPage1.help1",
                "&2 List player regions: &e/cadmin list [Player] (page)");
        adminHelpE2P1 = this.getLanguageString("theme.adminHelpPage1.help2",
                "&6&lMore commands on the following pages:");
        adminHelpE3P1 = this.getLanguageString("theme.adminHelpPage1.help3",
                "&2 Page 2: Manage region &a/cadmin help 2");
        adminHelpE4P1 = this.getLanguageString("theme.adminHelpPage1.help4",
                "&2 Page 3: Manage flags &a/cadmin help 3");
        adminHelpE5P1 = this.getLanguageString("theme.adminHelpPage1.help5", "&2 Page 4: Land edit &a/cadmin help 4");
        adminHelpE6P1 = this.getLanguageString("theme.adminHelpPage1.help6",
                "&2 Page 5: Admin regions &a/cadmin help 5");

        /* Page 2 Help for Admin Command */
        adminHelpHeaderP2 = this.getLanguageString("theme.adminHelpPage2.header", "&6&lManage region: [Page 2]");
        adminHelpE1P2 = this.getLanguageString("theme.adminHelpPage2.help1", "&2 Remove region: &e/cadmin remove");
        adminHelpE2P2 = this.getLanguageString("theme.adminHelpPage2.help2",
                "&2 Offer region: &e/cadmin setoffer (offervalue)");
        adminHelpE3P2 = this.getLanguageString("theme.adminHelpPage2.help3",
                "&2 Add player: &e/cadmin addplayer (player)");
        adminHelpE4P2 = this.getLanguageString("theme.adminHelpPage2.help4",
                "&2 Remove player: &e/cadmin removeplayer (player)");

        /* Page 3 Help for Admin Command */
        adminHelpHeaderP3 = this.getLanguageString("theme.adminHelpPage3.header", "&6&lManage flags: [Page 3]");
        adminHelpE1P3 = this.getLanguageString("theme.adminHelpPage3.help1",
                "&2 Fire Protection: &e/cadmin setfire (&aON&e/&cOFF&e)");
        adminHelpE2P3 = this.getLanguageString("theme.adminHelpPage3.help2",
                "&2 Access Protection: &e/cadmin setlock (&aON&e/&cOFF&e)");
        adminHelpE3P3 = this.getLanguageString("theme.adminHelpPage3.help3",
                "&2 PvP Protection: &e/cadmin setpvp (&aON&e/&cOFF&e)");
        adminHelpE4P3 = this.getLanguageString("theme.adminHelpPage3.help4",
                "&2 TnT Protection: &e/cadmin settnt (&aON&e/&cOFF&e)");
        adminHelpE5P3 = this.getLanguageString("theme.adminHelpPage3.help5",
                "&2 Monster Protection: &e/cadmin setmonster (&aON&e/&cOFF&e)");

        /* Page 4 Help for Admin Command */
        adminHelpHeaderP4 = this.getLanguageString("theme.adminHelpPage4.header", "&6&lLand edit: [Page 4]");
        adminHelpE1P4 = this.getLanguageString("theme.adminHelpPage4.help1",
                "&2 Change biome: &e/cadmin changebiome (biome)");
        adminHelpE2P4 = this.getLanguageString("theme.adminHelpPage4.help2",
                "&2 List available biomes: &e/cadmin listbiomes");
        adminHelpE3P4 = this.getLanguageString("theme.adminHelpPage4.help3",
                "&2 List snapshots: &e/cadmin listsaves [Player] (page)");

        /* Page 5 Help for Admin Command */
        adminHelpHeaderP5 = this.getLanguageString("theme.adminHelpPage5.header", "&6&lAdmin regions: [Page 5]");
        adminHelpE1P5 = this.getLanguageString("theme.adminHelpPage5.help1",
                "&2 Create Serverregion: &e/cadmin createserver");
        adminHelpE2P5 = this.getLanguageString("theme.adminHelpPage5.help2",
                "&2 Delete Serverregion: &e/cadmin deleteserver");
        adminHelpE3P5 = this.getLanguageString("theme.adminHelpPage5.help3", "&2 Create Shop: &e/cadmin createshop");
        adminHelpE4P5 = this.getLanguageString("theme.adminHelpPage5.help4", "&2 Delete Shop: &e/cadmin deleteshop");

        cubitHelpHeaderP1 = this.getLanguageString("theme.cubitHelpPage1.header",
                "&6<<<<<<<<<<<<<<<<<<<<&2&l|Cubit|&6>>>>>>>>>>>>>>>>>>>>");
        cubitHelpE1P1 = this.getLanguageString("theme.cubitHelpPage1.help1", "&2 Cubit version: &e/cubit version");
        cubitHelpE2P1 = this.getLanguageString("theme.cubitHelpPage1.help2", "&2 Reload cubit: &e/cubit reload");

        landEditConfirmTask = this.getLanguageString("landEditConfirmTask.info", "&eBestätige mit &b/{command} {subcommand} OK \n&eDu hast 20 Sekunden Zeit.");
        landEditConfirmTaskCancel = this.getLanguageString("landEditConfirmTask.cancel", "&cAktion wurde abgebrochen!");
        landEditConfirmInfoBiome = this.getLanguageString("landEditConfirmTask.biome", "&eBist du dir sicher dass du das Biome ändern möchtest?");
        landEditConfirmInfoReset = this.getLanguageString("landEditConfirmTask.reset", "&eBist du dir sicher dass du alle Blöcke auf dem Land löschen willst?");
    }

    public String getLanguageString(String path, String defaultValue) {
        if (!this.configFile.contains(path)) {
            this.configFile.set(path, defaultValue.replace("§", "&"));
        }
        return ChatColor.translateAlternateColorCodes('&', this.configFile.getString(path));

    }

    public CustomConfig getFile() {
        return this.configFile;
    }
}
