package com.diamonddagger590.caa.datastorage;

import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AnnouncerLimiter {

	private static BukkitTask limiterTask;
	@Getter
	private static int amountSinceLastClear = 0;
	
	@SuppressWarnings("deprecation")
	public static void startTimer(Plugin p) {
		if(limiterTask != null) {
			limiterTask.cancel();
		}
		limiterTask = new BukkitRunnable() {
			public void run() {
				amountSinceLastClear = 0;
			}
		}.runTaskTimer(p,0L, CrazyAuctionsAnnouncer.getConfigFile().getInt("Settings.LimiterRefreshTime") * 1200);
	}
	
	public static boolean canAnnounce() {
		if(amountSinceLastClear + 1 > CrazyAuctionsAnnouncer.getConfigFile().getInt("Settings.AnnouncementAmountLimit")) {
			return false;
		}
		else {
			amountSinceLastClear += 1;
			return true;
		}
	}
}