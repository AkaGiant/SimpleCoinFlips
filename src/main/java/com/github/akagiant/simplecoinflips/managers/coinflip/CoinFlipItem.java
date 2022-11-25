package com.github.akagiant.simplecoinflips.managers.coinflip;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import com.github.akagiant.simplecoinflips.util.ItemManager;
import lombok.Getter;
import me.akagiant.giantapi.util.ColorManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinFlipItem {

	@Getter
	private final CoinFlip coinFlip;

	public CoinFlipItem(CoinFlip coinFlip) {
		this.coinFlip = coinFlip;
	}

	public ItemStack getItemStack() {
		String path;

		if (coinFlip.taxIsEnabled()) { path = "coinflips.options.menu.has-tax"; }
		else { path = "coinflips.options.menu.does-not-have-tax"; }

		CoinFlipUser user = new CoinFlipUser(coinFlip.getCreatorId());

		ItemStack itemStack = getBaseItem(path);
		ItemMeta meta = itemStack.getItemMeta();

		meta.setDisplayName(ColorManager.formatColours(getDisplayName(user, path)));
		meta.setLore(ColorManager.formatColours(getLore(user, path)));

		boolean hasEnchantmentEffect = ConfigUtil.getBoolean(SimpleCoinFlips.config, path + ".has-enchantment-glow");
		if (hasEnchantmentEffect) {
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		meta.getPersistentDataContainer().set(new NamespacedKey(SimpleCoinFlips.getPlugin(), "cf_id"), PersistentDataType.STRING, getCoinFlip().getCfId().toString());

		itemStack.setItemMeta(meta);
		return itemStack;
	}

	private @NotNull ItemStack getBaseItem(String path) {
		String stringMaterial = ConfigUtil.getString(SimpleCoinFlips.config, path + ".material");

		ItemStack itemStack;

		if (stringMaterial.equalsIgnoreCase("creators_player_head")) {
			itemStack = ItemManager.createPlayerHead(coinFlip.getCreatorId());
		} else if (Material.matchMaterial(stringMaterial) != null) {
			itemStack = new ItemStack(Material.valueOf(stringMaterial));
		} else {
			ConfigUtil.logError(SimpleCoinFlips.config, path + ".material", "Value is not valid", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html", "Valid material or CREATORS_PLAYER_HEAD");
			return new ItemStack(Material.BARRIER);
		}

		return itemStack;
	}

	private String getDisplayName(CoinFlipUser user, String path) {
		String displayName = ConfigUtil.getString(SimpleCoinFlips.config, path + ".display-name");

		for (Map.Entry<String, Object> entry : getReplaceableMap(user).entrySet()) {
			displayName = displayName.replace(entry.getKey(), String.valueOf(entry.getValue()));
		}

		return displayName;
	}

	private List<String> getLore(CoinFlipUser user, String path) {
		List<String> lore = ConfigUtil.getStringList(SimpleCoinFlips.config, path + ".lore");
		List<String> formattedLore = new ArrayList<>();

		Map<String, Object> replaceables = getReplaceableMap(user);

		for (String str : lore) {
			for (Map.Entry<String, Object> entry : replaceables.entrySet()) {
				str = str.replace(entry.getKey(), String.valueOf(entry.getValue()));
			}
			formattedLore.add(str);
		}
		return ColorManager.formatColours(formattedLore);
	}

	private Map<String, Object> getReplaceableMap(CoinFlipUser user) {
		Map<String, Object> replaceableValues = new HashMap<>();
		replaceableValues.put("[creators id]", coinFlip.getCreatorId());

		replaceableValues.put("[creators name]", coinFlip.getCreatorName());
		replaceableValues.put("[creators player name]", coinFlip.getCreatorName());
		replaceableValues.put("[cf creator name]", coinFlip.getCreatorName());
		replaceableValues.put("[cf creators name]", coinFlip.getCreatorName());

		replaceableValues.put("[cf worth]", coinFlip.getWorthBeforeTax());
		replaceableValues.put("[cf side]", coinFlip.getCreatorSide());

		replaceableValues.put("[tax percentage]", coinFlip.getTaxPercentage());
		replaceableValues.put("[tax amount]", coinFlip.getToServer());

		replaceableValues.put("[total cfs played]", user.getTotalGames());
		replaceableValues.put("[cfs won]", user.getWins());
		replaceableValues.put("[cfs lost]", user.getLosses());
		replaceableValues.put("[cf ratio]", user.getWinLossRatio());
		replaceableValues.put("[cf ratio as percentage]", user.getWinLossRatioAsPercentage());

		return replaceableValues;
	}
}
