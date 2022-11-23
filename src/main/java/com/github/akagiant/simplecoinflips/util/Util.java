package com.github.akagiant.simplecoinflips.util;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.dependencies.PlaceholderManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.RegisteredCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Util {

	private Util() {
		//no instance
	}
	
	public static int getPermissionsCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.permission() != null) { amount += 1; }
		}
		return amount;
	}

	public static int getCommandAliasesCount() {
		int amount = 0;
		for (RegisteredCommand cmd : CommandAPI.getRegisteredCommands()) {
			if (cmd.aliases() != null) { amount += cmd.aliases().length; }
		}
		return amount;
	}

	public static void findDepends(Plugin plugin) {
		if (plugin.getDescription().getDepend().isEmpty()) { return; }
		Logger.toConsole("&m——————————&r &fDependencies &m&8————————");
		Logger.toConsole("&fLooking for &a" + plugin.getDescription().getSoftDepend().size() + " &fSoft Dependencies");

		List<String> found = new ArrayList<>();
		List<String> missing = new ArrayList<>();
		for (String dependency : plugin.getDescription().getDepend()) {
			if (getServer().getPluginManager().getPlugin(dependency) == null) missing.add(dependency);
			else found.add(dependency);
		}

		if (!found.isEmpty()) Logger.toConsole("&fFound &8| &a" + String.join("&8, &a", found));
		if (!missing.isEmpty()) Logger.toConsole("&fMissing &8| &c" + String.join("&8, &c", missing));

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			Logger.severe(SimpleCoinFlips.getPlugin().getDescription().getName() + " Disabled Version " + SimpleCoinFlips.getPlugin().getDescription().getVersion() + " | Vault Hook Not Found");
			getServer().getPluginManager().disablePlugin(plugin);
			return;
		}
		SimpleCoinFlips.economy = rsp.getProvider();
		Logger.toConsole("Vault Hooked to &b" + rsp.getProvider().getName());

	}

	public static void findSoftDepends(Plugin plugin) {
		if (plugin.getDescription().getSoftDepend().isEmpty()) { return; }
		Logger.toConsole("&m——————————&r &fSoft Dependencies &m&8————————");
		Logger.toConsole("&fLooking for &a" + plugin.getDescription().getSoftDepend().size() + " &fSoft Dependencies");

		List<String> found = new ArrayList<>();
		List<String> missing = new ArrayList<>();
		for (String dependency : plugin.getDescription().getSoftDepend()) {
			if (getServer().getPluginManager().getPlugin(dependency) == null) missing.add(dependency);
			else found.add(dependency);
		}

		if (!found.isEmpty()) Logger.toConsole("&fFound &8| &a" + String.join("&8, &a", found));
		if (!missing.isEmpty()) Logger.toConsole("&fMissing &8| &c" + String.join("&8, &c", missing));

		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			Logger.severe(SimpleCoinFlips.getPlugin().getDescription().getName() + " Disabled Version " + SimpleCoinFlips.getPlugin().getDescription().getVersion() + " | Vault Not Found");
			getServer().getPluginManager().disablePlugin(plugin);
		}

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceholderManager().register();
			SimpleCoinFlips.hasPAPI = true;
		}

	}
	
}
