package de.kekshaus.cubit.commandSuite;

import de.kekshaus.cubit.commandSuite.adminCommands.CommandAdmin;
import de.kekshaus.cubit.commandSuite.landCommands.CommandLand;
import de.kekshaus.cubit.commandSuite.shopCommands.CommandShop;
import de.kekshaus.cubit.plugin.CubitBukkitPlugin;

public class SetupCommands {

	private CubitBukkitPlugin plugin;
	private String commandLand = "land";
	private String commandShop = "shop";
	private String commandAdmin = "cadmin";

	public SetupCommands(CubitBukkitPlugin plugin) {
		plugin.getLogger().info("Loading CommandSuite");
		this.plugin = plugin;
		registerCommands();
	}

	private void registerCommands() {
		/* Command setup for /land */
		CommandLand landClass = new CommandLand(this.plugin);
		if (!landClass.isLoaded())
			landClass.loadCmd();
		plugin.getLogger().info("Register command /land");
		plugin.getCommand(commandLand).setExecutor(landClass);

		/* Command setup for /shop */
		CommandShop shopClass = new CommandShop(this.plugin);
		if (!shopClass.isLoaded())
			shopClass.loadCmd();
		plugin.getLogger().info("Register command /shop");
		plugin.getCommand(commandShop).setExecutor(shopClass);

		/* Command setup for /cadmin */
		CommandAdmin adminClass = new CommandAdmin(this.plugin);
		if (!adminClass.isLoaded())
			adminClass.loadCmd();
		plugin.getLogger().info("Register command /cadmin");
		plugin.getCommand(commandAdmin).setExecutor(adminClass);
	}

}
