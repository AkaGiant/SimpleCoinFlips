package com.github.akagiant.simplecoinflips.dependencies;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "HorusEconomy";
	}

	@Override
	public @NotNull String getAuthor() {
		return "AkaGiant";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
		return null;
	}
}
