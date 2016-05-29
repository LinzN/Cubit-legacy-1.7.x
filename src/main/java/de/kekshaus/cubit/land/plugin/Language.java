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

	/* UnSuccess MSG */
	public String buyIsAlreadyLand = "§cDieses Grundstück §e{regionID}§c ist nicht mehr frei!";
	public String notEnoughMoney = "§cDas kannst du dir leider nicht leisten. Kosten: §e{cost} Mines§c!";

	/* Header for Plugin */
	public String landHeader = "§9<<<<<<<<<<<<<<<<<<<<<§e§l|GrundStück|§9>>>>>>>>>>>>>>>>>>>>>";

	/* Land Info Page */
	public String landInfoA1 = "§2Dieses Grundstück [§e{regionID}§2] ist noch unbewohnt. \n§2Kaufe es mit §e/land buy §2für §e{cost} Mines§2!";

	public String landInfoE1 = "§2Grundstück: §9{regionID}";
	public String landInfoE1A1 = "§2Gilde: §e{guild}";
	public String landInfoE2 = "§2Besitzer: §5{owner}";
	public String landInfoE3 = "§2Grenzen: [§e{min}§2] bis [§e{max}§2]";
	public String landInfoE4 = "§2Zuletzt online: §e{time}";
	public String landInfoE5 = "§2Schutz-Systeme: {lock}, {monster}, {fire}, {pvp}, {tnt}";

	/* Page 1 Help for Land Command */
	public String landHelpE1P1 = "";
	public String landHelpE2P1 = "";
	public String landHelpE3P1 = "";
	public String landHelpE4P1 = "";
	public String landHelpE5P1 = "";

}
