package de.kekshaus.cookieApi.land;

public class LanguageManager {

	public LanguageManager() {

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

}
