package com.github.akagiant.simplecoinflips.util;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import me.akagiant.giantapi.util.ColorManager;
import org.bukkit.Bukkit;

public class Logger {

	private Logger() {
		//no instance
	}

	public static void info(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&b" + SimpleCoinFlips.getPlugin().getName() + " &b&lINFO&8] &f" + msg)
		);
	}

	public static void warn(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&6" + SimpleCoinFlips.getPlugin().getName() + " &6&lWARN&8] &f" + msg)
		);
	}

	public static void severe(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&c" + SimpleCoinFlips.getPlugin().getName() + " &c&lSEVERE&8] &f" + msg)
		);
	}

	public static void toConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&b" + SimpleCoinFlips.getPlugin().getName() + "&8] &f" + msg)
		);
	}

	public static void dev(String msg) {
		Bukkit.getConsoleSender().sendMessage(
			ColorManager.formatColours("&8[&d" + SimpleCoinFlips.getPlugin().getName() + " &lDEV&8] &f" + msg)
		);
	}

}
