package com.github.akagiant.simplecoinflips.commands;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.managers.CoinFlip;
import com.github.akagiant.simplecoinflips.managers.CoinFlipTax;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;

public class CommandCoinFlip {

	public static void register() {
		new CommandAPICommand("coinflip")
			.withAliases("cf")
			.withPermission("simplecoinflips.create")
			.withArguments(new DoubleArgument("amount"))
			.withArguments(new MultiLiteralArgument("heads", "tails"))
			.executesPlayer((player, objects) -> {

				// TODO: Permission check for if a player can create multiple CoinFlips

				double inputtedAmount = (double) objects[0];
				double currentBalance = SimpleCoinFlips.getEconomy().getBalance(player);

				if (currentBalance < inputtedAmount) {
					// TODO: Make return Message Customisable.
					player.sendMessage("Invalid Balance.");
					return;
				}

				// todo: If can create coinflip...

				new CoinFlip(player.getUniqueId(), (double) objects[0], (String) objects[1]);

			})
			.register();

		new CommandAPICommand("coinflips")
			.executesPlayer((player, objects) -> {
				for (CoinFlip coinFlip : CoinFlip.getCoinFlipList()) {
					player.sendMessage("ID: " + coinFlip.getCfId());
					player.sendMessage("Worth: " + coinFlip.getWorth());
					player.sendMessage("Creator ID: " + coinFlip.getCreator());
					player.sendMessage("Side: " + coinFlip.getCreatorsCoinFlipSide());
					player.sendMessage("Tax: ");
					player.sendMessage("Is Taxed: " + CoinFlipTax.taxIsEnabled() + " (" + CoinFlipTax.getTaxAmount() + "%)");
					player.sendMessage("Worth After Tax: " + coinFlip.getCoinFlipTax().getCfWorthAfterTax());
					player.sendMessage("Taxed Amount " + coinFlip.getCoinFlipTax().getAmountToServer());
				}
			})
			.register();
	}

}
