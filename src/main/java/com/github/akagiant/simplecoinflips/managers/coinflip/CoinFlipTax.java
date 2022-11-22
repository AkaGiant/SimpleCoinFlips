package com.github.akagiant.simplecoinflips.managers.coinflip;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CoinFlipTax {

	// The worth of the CF before tax.
	double cfWorthBeforeTax;

	// The worth of the CF after tax, if tax is enabled.
	double cfWorthAfterTax;

	// The amount of money that goes back into the server.
	double amountToServer;

	public CoinFlipTax(double cfWorthBeforeTax) {
		this.cfWorthBeforeTax = cfWorthBeforeTax;

		if (taxIsEnabled()) {
			double percentage = getTaxAmount();
			setAmountToServer((percentage * getCfWorthBeforeTax()) / 100);
			setCfWorthAfterTax(getCfWorthBeforeTax() - getAmountToServer());
		} else {
			setAmountToServer(0);
			setCfWorthAfterTax(getCfWorthBeforeTax());
		}
	}

	public static boolean taxIsEnabled() {
		return ConfigUtil.getBoolean(SimpleCoinFlips.config, "coinflips.options.tax.enabled");
	}

	public static double getTaxAmount() {
		return ConfigUtil.getDouble(SimpleCoinFlips.config, "coinflips.options.tax.amount");
	}

}
