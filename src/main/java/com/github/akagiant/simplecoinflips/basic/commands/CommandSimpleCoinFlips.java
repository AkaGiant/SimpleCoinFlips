package com.github.akagiant.simplecoinflips.basic.commands;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class CommandSimpleCoinFlips {

	public static void register() {
		new CommandTree("simplecoinflips")
			.withAliases("scf")
			.then(new LiteralArgument("reload")
				.withPermission("simplecoinflips.reload")
				.executes((commandSender, objects) -> {
					SimpleCoinFlips.cfData.reloadConfig();
					SimpleCoinFlips.playerData.reloadConfig();
					SimpleCoinFlips.config.reloadConfig();
					SimpleCoinFlips.messages.reloadConfig();

					commandSender.sendMessage("4/4 Files Reloaded");
				})
			)
			.register();

	}

}
