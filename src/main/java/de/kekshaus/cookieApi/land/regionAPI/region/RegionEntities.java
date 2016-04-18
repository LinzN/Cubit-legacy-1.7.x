package de.kekshaus.cookieApi.land.regionAPI.region;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.kekshaus.cookieApi.land.Landplugin;

public class RegionEntities {

	public static ProtectedRegion setOwner(ProtectedRegion region, World world, Player player) {
		final LocalPlayer localplayer = Landplugin.inst().getWorldGuardPlugin().wrapPlayer(player);
		region.getOwners().clear();
		DefaultDomain domain = new DefaultDomain();
		domain.addPlayer(localplayer);
		region.setOwners(domain);
		return region;
	}

	public static ProtectedRegion addMember(ProtectedRegion region, World world, Player player) {
		final LocalPlayer localplayer = Landplugin.inst().getWorldGuardPlugin().wrapPlayer(player);
		region.getMembers().addPlayer(localplayer);
		return region;
	}

	public static ProtectedRegion removeMember(ProtectedRegion region, World world, Player player) {
		final LocalPlayer localplayer = Landplugin.inst().getWorldGuardPlugin().wrapPlayer(player);
		region.getMembers().removePlayer(localplayer);
		return region;
	}

	public static ProtectedRegion clearMember(ProtectedRegion region, World world) {
		region.getMembers().clear();
		return region;
	}

}
