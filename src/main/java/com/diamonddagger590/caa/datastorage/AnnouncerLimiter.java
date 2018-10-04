package com.diamonddagger590.caa.datastorage;

import com.diamonddagger590.caa.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnouncerLimiter {

	private static int limiterTask = 0;
	private static int amountSinceLastClear = 0;
	
	@SuppressWarnings("deprecation")
	public static void startTimer(Plugin p) {
		if(limiterTask != 0) {
			Bukkit.getScheduler().cancelTask(limiterTask);
		}
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(p, new BukkitRunnable() {
			public void run() {
				amountSinceLastClear = 0;
			}
		}, 0, Main.getListHandler().getConfig().getInt("Settings.LimiterRefreshTime") * 1200);
		limiterTask = id;
	}
	
	public static boolean canAnnounce() {
		if(amountSinceLastClear + 1 > Main.getListHandler().getConfig().getInt("Settings.AnnouncementAmountLimit")) {
			return false;
		}
		else {
			amountSinceLastClear += 1;
			return true;
		}
	}
	
	public static int getAmount() {
		return amountSinceLastClear;
	}
	
}
