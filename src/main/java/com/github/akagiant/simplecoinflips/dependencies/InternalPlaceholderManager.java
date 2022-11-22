package com.github.akagiant.simplecoinflips.dependencies;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.managers.PermissionManager;
import org.bukkit.entity.Player;

public class InternalPlaceholderManager {

	// Should handle all internal placeholders.
	public static String format(Player player, String str) {

		String formattedString = str;

		if (formattedString.contains("[current coinflips]") || formattedString.contains("created coinflips")) {
			String amount = String.valueOf(PermissionManager.getCurrentlyCreated(player));

			formattedString = formattedString.replace("[created coinflips", amount);
			formattedString = formattedString.replace("[current coinflips", amount);
		}

		if (formattedString.contains("[max coinflips")) {
			String max = String.valueOf(PermissionManager.getMaxCfs(player));
			if (max.equals("-1")) {
				formattedString = formattedString.replace("[max coinflips]", "Unlimited");
			} else {
				formattedString = formattedString.replace("[max coinflips]", max);
			}
		}

		if (formattedString.contains("[can a create coinflip]") || formattedString.contains("can create coinflip")) {
			String canCreate = String.valueOf(PermissionManager.canCreateNewCf(player));

			formattedString = formattedString.replace("[can create a coinflip]", canCreate);
			formattedString = formattedString.replace("[can create coinflip]", canCreate);
		}

		if (formattedString.contains("[player balance]") || formattedString.contains("player bal")) {

			String bal = String.valueOf(SimpleCoinFlips.getEconomy().getBalance(player));

			formattedString = formattedString.replace("[player bal]", bal);
			formattedString = formattedString.replace("[player balance]", bal);
		}

		return formattedString;
	}

}
