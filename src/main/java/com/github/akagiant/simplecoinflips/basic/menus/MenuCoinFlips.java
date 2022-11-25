package com.github.akagiant.simplecoinflips.basic.menus;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlip;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlipItem;
import com.github.akagiant.simplecoinflips.managers.coinflip.CoinFlipUser;
import com.github.akagiant.simplecoinflips.util.ItemManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.C;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MenuCoinFlips {

	// TODO:
	// Action Types: Next Page, Previous Page, Close, Refresh.

	private MenuCoinFlips() {
		//no instance
	}
	static int itemIndex = 0;
	public static void open(Player player) {
		ChestGui gui = new ChestGui(6, "lol");
		PaginatedPane cfPane = new PaginatedPane(1, 1, 9, 5);
		cfPane.populateWithItemStacks(cfItems());
		cfPane.setOnClick(e -> {
			String id = getCfId(e.getCurrentItem());
			if (id == null) {
				player.sendMessage("Invalid");
				return;
			}


			CoinFlip coinFlip = new CoinFlip(id);
			UUID creatorId = coinFlip.getCreatorId();
			if (creatorId.equals(player.getUniqueId())) {
				player.sendMessage("You cannot take ur own cf");
				return;
			}

			OfflinePlayer creator = Bukkit.getOfflinePlayer(creatorId);
			if (creator.isOnline()) {
				creator.getPlayer().sendMessage("YOU HAVE BEEN CHALLENGED!!!!!!");
			}
			player.sendMessage("CHALLENGE STARTED");

			ItemStack[] items = new ItemStack[2];
			items[0] = ItemManager.createPlayerHead(creatorId);
			items[1] = ItemManager.createPlayerHead(player.getUniqueId());

			Inventory inv = Bukkit.createInventory(null, 27, "lol");

			int startIndex = ThreadLocalRandom.current().nextInt(items.length);


			for (int i = 0; i < startIndex; i++) {
				for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
					inv.setItem(itemstacks, items[(itemstacks + itemIndex) % items.length]);
				}
				itemIndex++;
			}

			player.openInventory(inv);

			Random r = new Random();
			double seconds = 5.0 + (10.0 - 5.0) * r.nextDouble();

			new BukkitRunnable() {

				double delay = 0;
				int ticks = 0;
				boolean done = false;

				@Override
				public void run() {
					if (done) return;
					ticks++;
					delay += 1 / (20 * seconds);
					if (ticks > (delay * 10) * 2) {
						ticks = 0;
						for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
							inv.setItem(itemstacks, items[(itemstacks + itemIndex) % items.length]);
						}
						itemIndex++;
						if (delay >= .5) {
							done = true;
							new BukkitRunnable() {

								@Override
								public void run() {
									ItemStack item = inv.getItem(13);
									SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
									UUID winnerId = skullMeta.getOwningPlayer().getUniqueId();

									OfflinePlayer winner = Bukkit.getOfflinePlayer(winnerId.equals(creatorId) ? creatorId : player.getUniqueId());
									OfflinePlayer loser = Bukkit.getOfflinePlayer(winnerId.equals(creatorId) ? player.getUniqueId() : creatorId);

									execute(coinFlip, winner, loser);

									player.updateInventory();
									player.closeInventory();
									cancel();
								}
							}.runTaskLater(SimpleCoinFlips.getPlugin(), 50);
						}
					}
				}
			}.runTaskTimer(SimpleCoinFlips.getPlugin(), 0, 1);
		});
		gui.addPane(cfPane);

		StaticPane navigationPane = new StaticPane(1, gui.getRows() - 1, 9, 1);
		navigationPane.addItem(new GuiItem(ItemManager.makeItem(Material.DARK_OAK_BUTTON, "&a&lNext Page", "&7&o(( Click to go to next page ))")), 2, 0);
		navigationPane.addItem(new GuiItem(ItemManager.makeItem(Material.DARK_OAK_BUTTON, "&c&lPrevious Page", "&7&o(( Click to go to next page ))")), 4, 0);
		navigationPane.addItem(new GuiItem(ItemManager.makeItem(Material.BARRIER, "&c&lCLOSE", "&7&o(( Click to close ))")), 3, 0);

		gui.addPane(navigationPane);


		gui.setOnGlobalClick(e -> e.setCancelled(true));
		gui.show(player);

	}

	private static boolean cfIsValid(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		String id = data.get(new NamespacedKey(SimpleCoinFlips.getPlugin(), "cf_id"), PersistentDataType.STRING);
		return CoinFlip.exists(id);
	}

	private static String getCfId(ItemStack itemStack) {
		if (!cfIsValid(itemStack)) return null;

		ItemMeta meta = itemStack.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		if (data.has(new NamespacedKey(SimpleCoinFlips.getPlugin(), "cf_id"), PersistentDataType.STRING)) {
			return data.get(new NamespacedKey(SimpleCoinFlips.getPlugin(), "cf_id"), PersistentDataType.STRING);
		}
		return null;
	}

	private static void execute(CoinFlip coinFlip, OfflinePlayer winner, OfflinePlayer loser) {

		if (winner.getPlayer().isOnline()) {
			winner.getPlayer().sendMessage("You Won");
		}

		if (loser.getPlayer().isOnline()) {
			loser.getPlayer().sendMessage("You Lost");
		}

		CoinFlipUser winner1 = new CoinFlipUser(winner.getUniqueId());
		CoinFlipUser loser1 = new CoinFlipUser(loser.getUniqueId());

		loser1.addLoss();
		winner1.addWin();

		if (coinFlip.taxIsEnabled()) {
			winner1.updateWinnings(coinFlip.getWorthAfterTax());
			winner1.updateTotalTaxed(coinFlip.getToServer());

			loser1.updateWinnings(-coinFlip.getWorthAfterTax());
			loser1.updateTotalTaxed(coinFlip.getToServer());

			SimpleCoinFlips.getEconomy().withdrawPlayer(loser, coinFlip.getWorthAfterTax());
			SimpleCoinFlips.getEconomy().depositPlayer(winner, coinFlip.getWorthAfterTax());
		} else {
			winner1.updateTotalTaxed(coinFlip.getWorthBeforeTax());
			loser1.updateWinnings(-coinFlip.getWorthBeforeTax());

			SimpleCoinFlips.getEconomy().withdrawPlayer(loser, coinFlip.getWorthBeforeTax());
			SimpleCoinFlips.getEconomy().depositPlayer(winner, coinFlip.getWorthBeforeTax());
		}
	}
	private static List<ItemStack> cfItems() {
		List<ItemStack> itemStackList = new ArrayList<>();
		for (String str : SimpleCoinFlips.cfData.getConfig().getKeys(false)) {
			if (!CoinFlip.exists(str)) continue;
			CoinFlip cf = new CoinFlip(str);
			itemStackList.add(new CoinFlipItem(cf).getItemStack());
		}
		return itemStackList;
	}
}

