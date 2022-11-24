package com.github.akagiant.simplecoinflips.dependencies;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.managers.PermissionManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InternalPlaceholderManager {

	private InternalPlaceholderManager() {
		//no instance
	}
	
	// Should handle all internal placeholders.
	public static String format(Player player, String str) {

		String formattedString = str;

		Map<String, Object> replaceableValues = new HashMap<>();
		int created = PermissionManager.getCurrentlyCreated(player);
		int max = PermissionManager.getMaxCfs(player);
		boolean canCreate = PermissionManager.canCreateNewCf(player);
		double bal = SimpleCoinFlips.getEconomy().getBalance(player);

		replaceableValues.put("[created coinflips]",  created);
		replaceableValues.put("[current coinflips]", created);
		replaceableValues.put("[max coinflips]", max == -1 ? "Unlimited" : max);
		replaceableValues.put("[can create a coinflip]", canCreate);
		replaceableValues.put("[can create coinflip]", canCreate);
		replaceableValues.put("[player bal]", bal);
		replaceableValues.put("[player balance]", bal);
		replaceableValues.put("[players balance]", bal);

		for (Map.Entry<String, Object> entry : replaceableValues.entrySet()) {
			formattedString = formattedString.replace(entry.getKey(), String.valueOf(entry.getValue()));
		}

		return formattedString;
	}
}
