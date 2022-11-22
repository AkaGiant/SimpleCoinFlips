package com.github.akagiant.simplecoinflips.managers;

import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlip;
import org.bukkit.entity.Player;

public class PermissionManager {

	private PermissionManager() {
		//no instance
	}
	
	public static boolean canCreateNewCf(Player player) {

		if (player.isOp() || player.hasPermission("simplecoinflips.*")) {
			return true;
		}

		if (!player.hasPermission("simplecoinflips.create")) return false;


		if (getMaxCfs(player) < getCurrentlyCreated(player)) {
			player.sendMessage("can create");
			return true;
		}
		player.sendMessage("nope");
		return false;

	}

	public static int getMaxCfs(Player player) {

		if (player.isOp() || player.hasPermission("simplecoinflips.*")) {
			return -1;
		}

		String basePerm = "simplecoinflips.create.";

		return player.getEffectivePermissions().stream().filter(pl -> pl.getPermission().startsWith(basePerm))
			.mapToInt(pai -> Integer.parseInt(pai.getPermission().substring(basePerm.length())))
			.max().orElse(0);
	}

	public static int getCurrentlyCreated(Player player) {
		int currentlyCreated = 0;
		for (CoinFlip cf : CoinFlip.getCoinFlipList()) {
			if (cf.getCreator().equals(player.getUniqueId())) {
				currentlyCreated++;
			}
		}
		return currentlyCreated;
	}

}
