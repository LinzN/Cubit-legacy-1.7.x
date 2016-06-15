package de.kekshaus.cubit.land.api.YamlConfigurationAPI.setup;

public class LanguageYaml {

	private CustomConfig configFile;

	/* Error MSG */
	public String noConsoleMode;
	public String errorInTask;
	public String errorCommand;
	public String errorNoCommand;
	public String errorNoPermission;
	public String errorNoLandPermission;
	public String errorNoLandFound;
	public String noNumberFound;

	/* Success MSG */
	public String buySuccess;
	public String sellSuccess;
	public String flagSwitchSuccess;
	public String addMemberSuccess;
	public String removeMemberSuccess;
	public String offerAddSuccess;
	public String offerRemoveSuccess;
	public String kickedInfo;
	public String kickInfo;
	/* UnSuccess MSG */
	public String buyIsAlreadyLand;
	public String notEnoughMoney;
	public String takeOwnLand;
	public String notOffered;
	public String wrongArguments;

	/* Header for Plugin */
	public String landHeader;

	/* Land Info Page */
	public String landInfoA1;
	public String landInfoA2;
	public String landInfoE1;
	public String landInfoE1A1;
	public String landInfoE2;
	public String landInfoE3;
	public String landInfoE4;
	public String landInfoE5;
	public String landInfoE6;

	/* Page 1 Help for Land Command */
	public String landHelpE1P1;
	public String landHelpE2P1;
	public String landHelpE3P1;
	public String landHelpE4P1;
	public String landHelpE5P1;
	public String landHelpE6P1;
	public String landHelpE7P1;

	/* Page 2 Help for Land Command */
	public String landHelpE1P2;
	public String landHelpE2P2;
	public String landHelpE3P2;
	public String landHelpE4P2;
	public String landHelpE5P2;
	public String landHelpE6P2;
	public String landHelpE7P2;

	/* Page 3 Help for Land Command */
	public String landHelpE1P3;
	public String landHelpE2P3;
	public String landHelpE3P3;
	public String landHelpE4P3;
	public String landHelpE5P3;

	/* Page 4 Help for Land Command */
	public String landHelpE1P4;
	public String landHelpE2P4;
	public String landHelpE3P4;
	public String landHelpE4P4;
	public String landHelpE5P4;
	public String landHelpE6P4;
	public String landHelpE7P4;

	public LanguageYaml(CustomConfig configFile) {
		this.configFile = configFile;
	}

	public void setup() {
		/* Error MSG */
		noConsoleMode = this.configFile.getLanguageString("noConsoleMode", "§4Dies ist nicht von der Konsole möglich!");
		errorInTask = this.configFile.getLanguageString("errorInTask",
				"§4Es ist ein Fehler in diesem Task aufgetreten: [§e{error}§4]");
		errorCommand = this.configFile.getLanguageString("errorCommand",
				"§4Es ist ein Fehler bei dem Befehl [§e{command}§4] aufgetreten!");
		errorNoCommand = this.configFile.getLanguageString("errorNoCommand",
				"§cDiesen Befehl gibt es nicht! Gib §e{command}§c ein!");
		errorNoPermission = this.configFile.getLanguageString("errorNoPermission",
				"§4Du hast dafür leider keine Berechtigungen! :(");
		errorNoLandPermission = this.configFile.getLanguageString("errorNoLandPermission",
				"§cDu hast für das Land {regionID} keine Berechtigungen! :(");
		errorNoLandFound = this.configFile.getLanguageString("errorNoLandFound",
				"§cEs wurde hier kein Land gefunden! :(");
		noNumberFound = this.configFile.getLanguageString("noNumberFound", "§cDies ist keine Gültige Nummer");

		/* Success MSG */
		buySuccess = this.configFile.getLanguageString("buySuccess",
				"§2Du hast das Grundstück §e{regionID}§2 gekauft!");
		sellSuccess = this.configFile.getLanguageString("sellSuccess",
				"§2Du hast das Grundstück §e{regionID}§2 an den Server verkauft!");
		flagSwitchSuccess = this.configFile.getLanguageString("flagSwitchSuccess",
				"§2Das System §9§l{flag}-Schutz §2wurde zu §9§l{value} §2gewechselt!");
		addMemberSuccess = this.configFile.getLanguageString("addMemberSuccess",
				"§2Du hat {member} dem Grundstück §e{regionID}§2 hinzugefügt!");
		removeMemberSuccess = this.configFile.getLanguageString("removeMemberSuccess",
				"§2Du hat {member} vom Grundstück §e{regionID}§2 entfernt!");
		offerAddSuccess = this.configFile.getLanguageString("offerAddSuccess",
				"§2Du bietest das Grundstück §e{regionID}§2 für §e{value}§2 an!");
		offerRemoveSuccess = this.configFile.getLanguageString("offerRemoveSuccess",
				"§2Du bietest das Grundstück §e{regionID}§2 nicht mehr an!");
		kickedInfo = this.configFile.getLanguageString("kickedInfo",
				"§6Du wurdest von dem Besitzer des Landes {regionID} verwiesen!");
		kickInfo = this.configFile.getLanguageString("kickInfo",
				"§2Alle nicht-Member wurden dem Land {regionID} verwiesen!");
		/* UnSuccess MSG */
		buyIsAlreadyLand = this.configFile.getLanguageString("buyIsAlreadyLand",
				"§cDieses Grundstück §e{regionID}§c ist nicht mehr frei!");
		notEnoughMoney = this.configFile.getLanguageString("notEnoughMoney",
				"§cDas kannst du dir leider nicht leisten. Kosten: §e{cost}§c!");
		takeOwnLand = this.configFile.getLanguageString("takeOwnLand", "§cDu kannst nicht dein eigenes Land kaufen!");
		notOffered = this.configFile.getLanguageString("notOffered",
				"§cDas Land {regionID} wird nicht zum Verkauf angeboten!");
		wrongArguments = this.configFile.getLanguageString("wrongArguments", "§cFehler im Befehl: Benutze {usage}!");

		/* Header for Plugin */
		landHeader = this.configFile.getLanguageString("theme.general.header",
				"§6<<<<<<<<<<<<<<<<<<<<<§2§l|GrundStück|§6>>>>>>>>>>>>>>>>>>>>>");

		/* Land Info Page */
		landInfoA1 = this.configFile.getLanguageString("theme.landinfo.empty",
				"§2Dieses Grundstück [§e{regionID}§2] ist noch unbewohnt. \n§2Kaufe es mit §e/land buy §2für §e{cost}§2!");
		landInfoA2 = this.configFile.getLanguageString("showOffer",
				"§2Dieses Grundstück wird für §e{value}§2 angeboten. Gib §e/land takeoffer §2ein um es zu kaufen!");
		landInfoE1 = this.configFile.getLanguageString("theme.landinfo.regionID", "§2Grundstück: §9{regionID}");
		landInfoE1A1 = this.configFile.getLanguageString("theme.landinfo.guildInfo", "§2Gilde: §e{guild}");
		landInfoE2 = this.configFile.getLanguageString("theme.landinfo.landOwner", "§2Besitzer: §9{owner}");
		landInfoE3 = this.configFile.getLanguageString("theme.landinfo.landMember", "§2Mitgleider: §5{members}");
		landInfoE4 = this.configFile.getLanguageString("theme.landinfo.landArea",
				"§2Grenzen: [§e{min}§2] bis [§e{max}§2]");
		landInfoE5 = this.configFile.getLanguageString("theme.landinfo.lastLogin", "§2Zuletzt online: §e{time}");
		landInfoE6 = this.configFile.getLanguageString("theme.landinfo.flagPackets",
				"§2Schutz-Systeme: {lock}, {monster}, {fire}, {pvp}, {tnt}");

		/* Page 1 Help for Land Command */
		landHelpE1P1 = this.configFile.getLanguageString("theme.helpPage1.header",
				"§6<<<<<<<<<<<<<<<<<§2§l|GrundStück Hilfe|§6>>>>>>>>>>>>>>>>>");
		landHelpE2P1 = this.configFile.getLanguageString("theme.helpPage1.help1",
				"§2 Infos zum Grundstück: §e/land info");
		landHelpE3P1 = this.configFile.getLanguageString("theme.helpPage1.help2",
				"§2 Liste deiner Grundstücke: §4/land list [SEITE]");
		landHelpE4P1 = this.configFile.getLanguageString("theme.helpPage1.help3",
				"§6§lMehr Befehle auf den folgenden Seiten:");
		landHelpE5P1 = this.configFile.getLanguageString("theme.helpPage1.help4",
				"§2 Seite 2: Kaufen - Verkaufen §a/land help 2");
		landHelpE6P1 = this.configFile.getLanguageString("theme.helpPage1.help5",
				"§2 Seite 3: Mitspieler verwalten §a/land help 3");
		landHelpE7P1 = this.configFile.getLanguageString("theme.helpPage1.help6",
				"§2 Seite 4: Grundstücks Einstellungen §a/land help 4");

		/* Page 2 Help for Land Command */
		landHelpE1P2 = this.configFile.getLanguageString("theme.helpPage2.header", "§6§lKaufen - Verkaufen: [Seite 2]");
		landHelpE2P2 = this.configFile.getLanguageString("theme.helpPage2.help1",
				"§2 Grundstück erwerben: §e/land buy");
		landHelpE3P2 = this.configFile.getLanguageString("theme.helpPage2.help2",
				"§2 Grundstück verkaufen: §e/land sell");
		landHelpE4P2 = this.configFile.getLanguageString("theme.helpPage2.help3",
				"§2 Angebotenes Grundstück abkaufen: §4/land takeoffer");
		landHelpE5P2 = this.configFile.getLanguageString("theme.helpPage2.help4",
				"§2 Grundstück anbieten: §4/land offer [Preis]");
		landHelpE6P2 = this.configFile.getLanguageString("theme.helpPage2.help5",
				"§2 Inaktives Grundstück aufkaufen: §4/land buyup");
		landHelpE7P2 = this.configFile.getLanguageString("theme.helpPage2.help6",
				"§a§lMehr auf Seite 3 mit §6/land help 3");

		/* Page 3 Help for Land Command */
		landHelpE1P3 = this.configFile.getLanguageString("theme.helpPage3.header",
				"§6§lMitspieler verwalten: [Seite 3]");
		landHelpE2P3 = this.configFile.getLanguageString("theme.helpPage3.help1",
				"§2 Mitspieler Bau-Rechte erlauben: §e/land add [Mitspieler]");
		landHelpE3P3 = this.configFile.getLanguageString("theme.helpPage3.help2",
				"§2 Mitspieler Bau-Rechte entfernen: §e/land remove [Mitspieler]");
		landHelpE4P3 = this.configFile.getLanguageString("theme.helpPage3.help3",
				"§2 Mitspieler vom Grundstück entfernen: §4/land kick");
		landHelpE5P3 = this.configFile.getLanguageString("theme.helpPage3.help4",
				"§a§lMehr auf Seite 4 mit §6/land help 4");

		/* Page 4 Help for Land Command */
		landHelpE1P4 = this.configFile.getLanguageString("theme.helpPage4.header",
				"§6§lGrundstücks Sicherungen: [Seite 4]");
		landHelpE2P4 = this.configFile.getLanguageString("theme.helpPage4.help1",
				"§2 Feuer Schutz: §e/land fire (§aON§e/§cOFF§e)");
		landHelpE3P4 = this.configFile.getLanguageString("theme.helpPage4.help2",
				"§2 Zugriff Schutz: §e/land lock (§aON§e/§cOFF§e)");
		landHelpE4P4 = this.configFile.getLanguageString("theme.helpPage4.help3",
				"§2 PvP Schutz: §e/land pvp (§aON§e/§cOFF§e)");
		landHelpE5P4 = this.configFile.getLanguageString("theme.helpPage4.help4",
				"§2 TnT Schutz: §e/land tnt (§aON§e/§cOFF§e)");
		landHelpE6P4 = this.configFile.getLanguageString("theme.helpPage4.help5",
				"§2 Monster Schutz: §e/land monster (§aON§e/§cOFF§e)");
		landHelpE7P4 = this.configFile.getLanguageString("theme.helpPage4.help6",
				"§a§lZurück auf Seite 3 mit §6/land help 3");
	}
}
