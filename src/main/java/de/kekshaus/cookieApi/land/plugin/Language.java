package de.kekshaus.cookieApi.land.plugin;

public class Language {

	public Language() {

	}

	/* Error MSG */
	public String noConsoleMode = "Dies ist nicht von der Konsole möglich!";
	public String errorInTask = "Es ist ein Fehler in diesem Task aufgetreten: [{error}]";
	public String errorCommand = "Es ist ein Fehler bei dem Befehl [{command}] aufgetreten!";
	public String errorNoCommand = "Diesen Befehl gibt es nicht! Gib {command} ein!";

	/* Success MSG */
	public String buySuccess = "Du hast das Grundstück {regionID} gekauft!";

	/* UnSuccess MSG */
	public String buyIsAlreadyLand = "Dieses Grundstück {regionID} ist nicht mehr frei!";
	public String notEnoughMoney = "Das kannst du dir leider nicht leisten. Kosten: {cost}!";

	/* Header for Plugin */
	public String landHeader = "§9<<<<<<<<<<<<<<<<<<<<<§e§l|GrundStück|§9>>>>>>>>>>>>>>>>>>>>>";

	/* Land Info Page */
	public String landInfoA1 = "Dieses Grundstück [{regionID}] ist noch unbewohnt. Kaufe es mit /land buy für {cost} Mines!";

	public String landInfoE1 = "Grundstück: {regionID}";
	public String landInfoE1A1 = "Gilde: {guild}";
	public String landInfoE2 = "Besitzer: {owner}";
	public String landInfoE3 = "Grenzen: {min} bis {max}";
	public String landInfoE4 = "Zuletzt online: {time}";

	/* Page 1 Help for Land Command */
	public String landHelpE1P1 = "";
	public String landHelpE2P1 = "";
	public String landHelpE3P1 = "";
	public String landHelpE4P1 = "";
	public String landHelpE5P1 = "";
}
