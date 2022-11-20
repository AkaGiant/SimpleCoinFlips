package com.github.akagiant.simplecoinflips;

import com.github.akagiant.simplecoinflips.util.Logger;
import com.github.akagiant.simplecoinflips.util.Util;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.Getter;
import lombok.Setter;
import me.akagiant.giantapi.util.Config;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleCoinFlips extends JavaPlugin {

	@Getter
	private static Plugin plugin;
	public static Config config;
	public static boolean hasPAPI = false;

	@Setter
	private static Economy economy = null;

	@Override
	public void onLoad() {
		CommandAPI.onLoad(new CommandAPIConfig().silentLogs(true));
	}

	@Override
	public void onEnable() {
		plugin = this;

		// Plugin startup logic
		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&fPlugin is loading...");
		Logger.toConsole("&m——————————————&r &fCore &m&8————————————————");

		CommandAPI.onEnable(this);

		registerConfigurations();
		registerCommands();
		registerEvents();

		Logger.toConsole("&fCommands Loaded (&a" + (CommandAPI.getRegisteredCommands().size())+ "&f) &8| &fAliases: (&a" + Util.getCommandAliasesCount() + "&f)");
		Logger.toConsole("&fPermissions Loaded (&a" + (Util.getPermissionsCount())+ "&f)");

		Util.findDepends(plugin);
		Util.findSoftDepends(plugin);
		Util.registerDependencies(plugin);

		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&ahas been Enabled");
		Logger.toConsole("&m————————————————————————————————————");
		Logger.toConsole("&fDeveloped by &aAkaGiant");
		Logger.toConsole("&fVersion: &a" + getPlugin().getDescription().getVersion());
		Logger.toConsole("&m————————————————————————————————————");
	}


	@Override
	public void onDisable() {
		// Plugin shutdown logic
		CommandAPI.onDisable();
	}

	private void registerConfigurations() {
		config = new Config(this, "config");
	}

	private void registerCommands() {
	}

	private void registerEvents() {
	}


}
