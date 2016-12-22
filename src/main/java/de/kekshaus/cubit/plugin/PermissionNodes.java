package de.kekshaus.cubit.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PermissionNodes {

	public PermissionNodes(JavaPlugin plugin) {
		plugin.getLogger().info("Loading PermissionNodes");
	}

	/* User Perms */
	/* Land Perms */
	public String infoLand = "cubit.command.land.info";
	public String buyLand = "cubit.command.land.buy";
	public String sellLand = "cubit.command.land.sell";
	public String helpLand = "cubit.command.land.help";
	public String addMemberLand = "cubit.command.land.addmember";
	public String removeMemberLand = "cubit.command.land.removemember";
	public String flagLand = "cubit.command.land.flag.";
	public String offerLand = "cubit.command.land.offer";
	public String buyupLand = "cubit.command.land.buyup";
	public String takeOfferLand = "cubit.command.land.takeoffer";
	public String kickLand = "cubit.command.land.kick";
	public String listLand = "cubit.command.land.list";
	public String changeBiomeLand = "cubit.command.land.changebiome";
	public String listBiomesLand = "cubit.command.land.listbiomes";
	public String saveLand = "cubit.command.land.saveland";
	public String restoreLand = "cubit.command.land.restoreland";
	/* Shop Perms */
	public String buyShop = "cubit.command.shop.buy";
	public String sellShop = "cubit.command.shop.sell";
	public String helpShop = "cubit.command.shop.help";
	public String infoShop = "cubit.command.shop.info";
	public String addMemberShop = "cubit.command.shop.addmember";
	public String removeMemberShop = "cubit.command.shop.removemember";
	public String kickShop = "cubit.command.shop.kick";
	public String listShop = "cubit.command.shop.list";
	public String listBiomesShop = "cubit.command.shop.listbiomes";
	public String changeBiomeShop = "cubit.command.shop.changebiome";

	/* Admin Perms */
	public String flagAdminLand = "cubit.command.admin.flag.";
	public String sellAdminLand = "cubit.command.admin.sell";
	public String addMemberAdminLand = "cubit.command.admin.addmember";
	public String removeMemberAdminLand = "cubit.command.admin.removemember";
	public String offerAdminLand = "cubit.command.admin.offer";
	public String helpAdminLand = "cubit.command.admin.help";
	public String createServerAdminLand = "cubit.command.admin.createserver";
	public String deleteServerAdminLand = "cubit.command.admin.deleteserver";
	public String createShopAdminLand = "cubit.command.admin.createshop";
	public String changeBiomeAdminLand = "cubit.command.admin.changebiome";
	public String listAdminLand = "cubit.command.admin.list";
	public String listBiomesAdminLand = "cubit.command.admin.listbiomes";
	public String kickAdminBypass = "cubit.bypass.kick";
	public String reloadCubit = "cubit.command.admin.reloadcubit";
	public String testAdmin = "cubit.command.admin.testadmin";

}
