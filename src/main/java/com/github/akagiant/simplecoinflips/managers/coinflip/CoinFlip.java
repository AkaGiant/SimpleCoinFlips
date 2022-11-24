package com.github.akagiant.simplecoinflips.managers.coinflip;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import com.github.akagiant.simplecoinflips.util.ItemManager;
import com.github.akagiant.simplecoinflips.util.Logger;
import jdk.vm.ci.code.site.Call;
import lombok.Getter;
import me.akagiant.giantapi.util.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Suite;

import javax.xml.crypto.dsig.SignedInfo;
import java.util.*;
import java.util.concurrent.Callable;

public class CoinFlip {

	@Getter
	private UUID cfId;

	public CoinFlip(Player creator, double worth, String creatorsCoinFlipSide) {

		this.cfId = UUID.randomUUID();

		if (!SimpleCoinFlips.cfData.getConfig().isSet(getCfId().toString())) {

			// Create new section
			SimpleCoinFlips.cfData.getConfig().createSection(getCfId().toString());

			// Save Creator ID
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".creator.id", creator.getUniqueId().toString());
			SimpleCoinFlips.cfData.getConfig().set(getCfId() + ".creator.name", creator.getName());
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

	/** @return Weather or not tax is enabled for this CoinFlip */
	public boolean taxIsEnabled() {
		return ConfigUtil.getBoolean(SimpleCoinFlips.cfData, getCfId() + ".tax.enabled");
	}

	/** @return The percentage of tax to apply to the CoinFlip */
	public double getTaxPercentage() {
		return ConfigUtil.getDouble(SimpleCoinFlips.cfData, getCfId() + ".tax.percentage");
	}

	/** @return The creators UUID */
	public UUID getCreatorId() {
		return UUID.fromString(ConfigUtil.getString(SimpleCoinFlips.cfData, getCfId() + ".creator.id"));
	}

	/** @return The creators name */
	public String getCreatorName() {
		return ConfigUtil.getString(SimpleCoinFlips.cfData, getCfId() + "creator.name");
	}

	/** @return The Side of the CoinFlip the Creator Chose */
	public String getCreatorSide() {
		return ConfigUtil.getString(SimpleCoinFlips.cfData, getCfId() + ".side");
	}

	/** @return The amount of money that is taken from the CF and goes to the server */
	public double getToServer() { return getWorthBeforeTax() - getWorthAfterTax() ; }


}
