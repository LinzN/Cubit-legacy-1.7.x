package org.inventivetalent.update.spiget;

import org.inventivetalent.update.spiget.comparator.VersionComparator;

import de.kekshaus.cubit.plugin.Landplugin;

public class SpigetCheck {
	private Landplugin plugin;
	public boolean isAvailable;
	public String version;

	public SpigetCheck(Landplugin plugin) {
		this.plugin = plugin;

		this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable() {

			@Override
			public void run() {
				Landplugin.inst().getLogger().info("Run spiget update checker");
				checker();
			}
		}, 20 * 30, 20 * 60 * 30);
	}

	private void checker() {

		SpigetUpdate updater = new SpigetUpdate(this.plugin, 31850);

		// This converts a semantic version to an integer and checks if the
		// updated version is greater
		updater.setVersionComparator(VersionComparator.SEM_VER);

		updater.checkForUpdate(new UpdateCallback() {
			@Override
			public void updateAvailable(String newVersion, String downloadUrl, boolean hasDirectDownload) {
				isAvailable = true;
				version = newVersion;
				plugin.getLogger().info("A new update is available. Version: " + newVersion);

			}

			@Override
			public void upToDate() {
				isAvailable = false;
			}
		});
	}

}
