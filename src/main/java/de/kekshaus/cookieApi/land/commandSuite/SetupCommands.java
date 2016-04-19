package de.kekshaus.cookieApi.land.commandSuite;

import de.kekshaus.cookieApi.land.Landplugin;
import de.kekshaus.cookieApi.land.commandSuite.adminCommands.CommandAdmin;
import de.kekshaus.cookieApi.land.commandSuite.landCommands.CommandLand;
import de.kekshaus.cookieApi.land.commandSuite.shopCommands.CommandShop;

public class SetupCommands {

	private Landplugin plugin;
	private String commandLand = "land";
	private String commandShop = "shop";
	private String commandAdmin = "ladmin";

	public SetupCommands(Landplugin plugin) {
		this.plugin = plugin;
		registerCommands();
	}

	private void registerCommands() {
		/* Command setup for /land */
		CommandLand landClass = new CommandLand(this.plugin);
		if (!landClass.isLoaded())
			landClass.loadCmd();
		plugin.getCommand(commandLand).setExecutor(landClass);

		/* Command setup for /shop */
		CommandShop shopClass = new CommandShop(this.plugin);
		if (!shopClass.isLoaded())
			shopClass.loadCmd();
		plugin.getCommand(commandShop).setExecutor(shopClass);

		/* Command setup for /ladmin */
		CommandAdmin adminClass = new CommandAdmin(this.plugin);
		if (!adminClass.isLoaded())
			adminClass.loadCmd();
		plugin.getCommand(commandAdmin).setExecutor(adminClass);
	}

}
