package com.github.akagiant.simplecoinflips.managers.coinflip;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import com.github.akagiant.simplecoinflips.util.ItemManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.runners.Suite;

import javax.xml.crypto.dsig.SignedInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoinFlip {

	@Getter
	private UUID cfId;

	public CoinFlip(UUID creatorId, double worth, String creatorsCoinFlipSide) {

		this.cfId = UUID.randomUUID();

		if (!SimpleCoinFlips.cfData.getConfig().isSet(getCfId().toString())) {

			// Create new section
			SimpleCoinFlips.cfData.getConfig().createSection(getCfId().toString());

			// Save Creator ID
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".creator", creatorId.toString());

			// Save worth before tax
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".worth", worth);

			// Save creator side.
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".side", creatorsCoinFlipSide);

			boolean hasTax = ConfigUtil.getBoolean(SimpleCoinFlips.cfData, "coinflips.options.tax.enabled");

			// Save weather tax is enabled or not.
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".tax.enabled", hasTax);

			// If tax is enabled for this CF
			if (hasTax) {
				// Save how much tax should be applied for the CF.
				double percentage = ConfigUtil.getDouble(SimpleCoinFlips.config, "coinflips.options.tax.amount");
				SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".tax.percentage", percentage);
			}

			// Save the file.
			SimpleCoinFlips.cfData.saveConfig();
		}
	}

	public CoinFlip(UUID cfId) {
		this.cfId = cfId;
	}

	public CoinFlip(String cfId) {
		this.cfId = UUID.fromString(cfId);
	}

	public static boolean exists(UUID uuid) {
		for (String str : SimpleCoinFlips.cfData.getConfig().getKeys(false)) {
			if (str.equals(uuid.toString())) return true;
		}
		return false;
	}

	public static boolean exists(String uuid) {
		return exists(UUID.fromString(uuid));
	}
	public double getWorthBeforeTax() {
		return ConfigUtil.getDouble(SimpleCoinFlips.cfData, getCfId() + ".worth");
	}

	public double getWorthAfterTax() {
		if (taxIsEnabled()) {
			double worth = getWorthBeforeTax();
			double percentage = getTaxPercentage();

			return 1;
		}
		return getWorthBeforeTax();
	}

	public boolean taxIsEnabled() {
		return ConfigUtil.getBoolean(SimpleCoinFlips.cfData, getCfId() + ".tax.enabled");
	}

	public double getTaxPercentage() {
		return ConfigUtil.getDouble(SimpleCoinFlips.cfData, getCfId() + ".tax.percentage");
	}

	public UUID getCreatorId() {
		return UUID.fromString(ConfigUtil.getString(SimpleCoinFlips.cfData, getCfId() + ".creator"));
	}

	public double getToServer() {
		double worth = getWorthBeforeTax();
		double after = getWorthAfterTax();
		return worth - after;
	}

	public ItemStack getItemStack() {

		// TODO: Change to creators player head
		ItemStack itemStack = new ItemStack(ItemManager.createPlayerHead(getCreatorId(), null));
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();

		lore.add("&m--------------");
		lore.add("Creator" + getCreatorId().toString());
		lore.add("Worth" + getWorthBeforeTax());
		if (taxIsEnabled()) {
			lore.add("Tax:");
			lore.add("  Enabled:" + taxIsEnabled());
			lore.add("  Percentage: " + getTaxPercentage());
			lore.add("  To Server: " + getToServer());
		} else {
			lore.add("Tax: Disabled");
		}

		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		return itemStack;
	}


}
