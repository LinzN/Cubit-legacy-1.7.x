package de.kekshaus.cubit.land.plugin;

public class Language {

	/* Error MSG */
	public String noConsoleMode = "§4Dies ist nicht von der Konsole möglich!";
	public String errorInTask = "§4Es ist ein Fehler in diesem Task aufgetreten: [§e{error}§4]";
	public String errorCommand = "§4Es ist ein Fehler bei dem Befehl [§e{command}§4] aufgetreten!";
	public String errorNoCommand = "§cDiesen Befehl gibt es nicht! Gib §e{command}§c ein!";
	public String errorNoPermission = "§4Du hast dafür leider keine Berechtigungen! :(";
	public String errorNoLandPermission = "§cDu hast für das Land {regionID} keine Berechtigungen! :(";
	public String errorNoLandFound = "§cEs wurde hier kein Land gefunden! :(";

	/* Success MSG */
	public String buySuccess = "§2Du hast das Grundstück §e{regionID}§2 gekauft!";
	public String sellSuccess = "§2Du hast das Grundstück §e{regionID}§2 an den Server verkauft!";
	public String flagSwitchSuccess = "§2Das System §9§l{flag}-Schutz §2wurde zu §9§l{value} §2gewechselt!";
	public String addMemberSuccess = "§2Du hat {member} dem Grundstück §e{regionID}§2 hinzugefügt!";
	public String removeMemberSuccess = "§2Du hat {member} vom Grundstück §e{regionID}§2 entfernt!";
	public String offerAddSuccess = "§2Du bietest das Grundstück §e{regionID}§2 für §e{value} §2 an!";
	public String offerRemoveSuccess = "§2Du bietest das Grundstück §e{regionID}§2 nicht mehr an!";
	public String showOffer = "§2Dieses Grundstück wird für §e{value} Mines§2 angeboten. Gib §e/land takeoffer §2ein um es zu kaufen!";
	/* UnSuccess MSG */
	public String buyIsAlreadyLand = "§cDieses Grundstück §e{regionID}§c ist nicht mehr frei!";
	public String notEnoughMoney = "§cDas kannst du dir leider nicht leisten. Kosten: §e{cost} Mines§c!";
	public String takeOwnLand = "§cDu kannst nicht dein eigenes Land kaufen!";
	public String notOffered = "§cDas Land {regionID} wird nicht zum Verkauf angeboten!";

	/* Header for Plugin */
	public String landHeader = "§6<<<<<<<<<<<<<<<<<<<<<§2§l|GrundStück|§6>>>>>>>>>>>>>>>>>>>>>";

	/* Land Info Page */
	public String landInfoA1 = "§2Dieses Grundstück [§e{regionID}§2] ist noch unbewohnt. \n§2Kaufe es mit §e/land buy §2für §e{cost} Mines§2!";

	public String landInfoE1 = "§2Grundstück: §9{regionID}";
	public String landInfoE1A1 = "§2Gilde: §e{guild}";
	public String landInfoE2 = "§2Besitzer: §9{owner}";
	public String landInfoE3 = "§2Mitgleider: §5{members}";
	public String landInfoE4 = "§2Grenzen: [§e{min}§2] bis [§e{max}§2]";
	public String landInfoE5 = "§2Zuletzt online: §e{time}";
	public String landInfoE6 = "§2Schutz-Systeme: {lock}, {monster}, {fire}, {pvp}, {tnt}";

	/* Page 1 Help for Land Command */
	public String landHelpE1P1 = "§6<<<<<<<<<<<<<<<<<§2§l|GrundStück Hilfe|§6>>>>>>>>>>>>>>>>>";
	public String landHelpE2P1 = "&2 Infos zum Grundstück: &e/land info";
	public String landHelpE3P1 = "&2 Liste deiner Grundstücke: &e/land list [SEITE]";
	public String landHelpE4P1 = "&6&lMehr Befehle auf den folgenden Seiten:";
	public String landHelpE5P1 = "&2 Seite 2: Kaufen - Verkaufen &a/land help 2";
	public String landHelpE6P1 = "&2 Seite 3: Mitspieler verwalten &a/land help 3";
	public String landHelpE7P1 = "&2 Seite 4: Grundstücks Einstellungen &a/land help 4";

	/* Page 2 Help for Land Command */
	public String landHelpE1P2 = "&6&lKaufen - Verkaufen: [Seite 2]";
	public String landHelpE2P2 = "&2 Grundstück erwerben: &e/land buy";
	public String landHelpE3P2 = "&2 Grundstück verkaufen: &e/land sell";
	public String landHelpE4P2 = "&2 Angebotenes Grundstück abkaufen: &4/land takeoffer";
	public String landHelpE5P2 = "&2 Grundstück anbieten: &4/land offer [Preis]";
	public String landHelpE6P2 = "&2 Inaktives Grundstück aufkaufen: &4/land buyup";
	public String landHelpE7P2 = "&a&lMehr auf Seite 3 mit &6/land help 3";

	/* Page 3 Help for Land Command */
	public String landHelpE1P3 = "&6&lMitspieler verwalten: [Seite 3]";
	public String landHelpE2P3 = "&2 Mitspieler Bau-Rechte erlauben: &e/land add [Mitspieler]";
	public String landHelpE3P3 = "&2 Mitspieler Bau-Rechte entfernen: &e/land remove [Mitspieler]";
	public String landHelpE4P3 = "&2 Mitspieler vom Grundstück entfernen: &4/land kick";
	public String landHelpE5P3 = "&a&lMehr auf Seite 4 mit &6/land help 4";

	/* Page 4 Help for Land Command */
	public String landHelpE1P4 = "&6&lGrundstücks Sicherungen: [Seite 4]";
	public String landHelpE2P4 = "&2 Feuer Schutz: &e/land fire (&aON&e/&cOFF&e)";
	public String landHelpE3P4 = "&2 Zugriff Schutz: &e/land lock (&aON&e/&cOFF&e)";
	public String landHelpE4P4 = "&2 PvP Schutz: &e/land pvp (&aON&e/&cOFF&e)";
	public String landHelpE4P5 = "&2 TnT Schutz: &e/land tnt (&aON&e/&cOFF&e)";
	public String landHelpE4P6 = "&2 Monster Schutz: &e/land monster (&aON&e/&cOFF&e)";
	public String landHelpE5P7 = "&a&lZurück auf Seite 3 mit &6/land help 3";

}
