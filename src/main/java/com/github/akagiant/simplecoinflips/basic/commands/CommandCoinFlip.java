package com.github.akagiant.simplecoinflips.basic.commands;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.basic.menus.MenuCoinFlips;
import com.github.akagiant.simplecoinflips.managers.PermissionManager;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlip;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlipTax;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlipUser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;

public class CommandCoinFlip {

	private CommandCoinFlip() {
		//no instance
	}
	
	public static void register() {
		new CommandAPICommand("coinflip")
			.withAliases("cf")
			.withPermission("simplecoinflips.create")
			.withArguments(new DoubleArgument("amount"))
			.withArguments(new MultiLiteralArgument("heads", "tails"))
			.executesPlayer((player, objects) -> {
				double inputtedAmount = (double) objects[0];
				double currentBalance = SimpleCoinFlips.getEconomy().getBalance(player);

				if (currentBalance < inputtedAmount) {
					// TODO: Make return Message Customisable.
					player.sendMessage("Invalid Balance.");
					return;
				}


				// todo: If can create coinflip...



				if (PermissionManager.canCreateNewCf(player)) {
					player.sendMessage("New CF Created");
					SimpleCoinFlips.getEconomy().withdrawPlayer(player, inputtedAmount);
					new CoinFlip(player, (double) objects[0], (String) objects[1]);
				} else {
					int maxCfs = PermissionManager.getMaxCfs(player);
					player.sendMessage("Max Reached: " + maxCfs + "/" + maxCfs);
				}
			})
			.register();

		new CommandAPICommand("coinflips")
			.executesPlayer((player, objects) -> {

				MenuCoinFlips.open(player);
			})
			.register();

		new CommandAPICommand("cftoggle")
			.withAliases("coinfliptoggle")
			.withPermission("simplecoinflips.toggle")
			.executesPlayer((player, objects) -> {
				CoinFlipUser user = new CoinFlipUser(player.getUniqueId());
				user.toggleHasNotificationsToggled();
				player.sendMessage(String.valueOf(user.getHasNotificationsToggled()));
			})
			.register();

	}

}
