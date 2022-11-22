package com.github.akagiant.simplecoinflips.managers.coinflip;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import com.github.akagiant.simplecoinflips.util.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.UUID;

public class CoinFlipUser {

	@Getter
	final UUID uuid;

	public CoinFlipUser(UUID uuid) {
		this.uuid = uuid;

		if (Bukkit.getOfflinePlayer(uuid).getPlayer() == null) {
			Logger.severe(uuid + " is not a valid UUID.");
			return;
		}

		if (!SimpleCoinFlips.playerData.getConfig().isSet(getUuid().toString())) {
			SimpleCoinFlips.playerData.getConfig().createSection(getUuid().toString());
			SimpleCoinFlips.playerData.saveConfig();

			setWins(0);
			setLosses(0);
			setWinnings(0);
			setHasNotificationsToggled(true);
		}
	}

	public static boolean exists(UUID uuid) {
		for (String string : SimpleCoinFlips.playerData.getConfig().getKeys(false)) {
			if (string.equals(uuid.toString())) {
				return true;
			}
		}
		return false;
	}


	/** @return How many times this player has played CoinFlips */
	public int getTotalGames() { return getWins() + getLosses(); }

	public void setWins(int amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-wins", amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	/** @return How many times this player has won CoinFlips */
	public int getWins() { return ConfigUtil.getInt(SimpleCoinFlips.playerData, getUuid() + ".total-wins"); }

	public void setLosses(int amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-losses", amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	/** @return How many times this player has lost CoinFlips */
	public int getLosses() { return ConfigUtil.getInt(SimpleCoinFlips.playerData, getUuid() + ".total-losses"); }

	/** @return The ratio of win losses */
	public double getWinLossRatio() { return (getWins() / getTotalGames()); }

	/** @return The ratio of win losses * 100*/
	public double getWinLossRatioAsPercentage() { return (getWins() / getTotalGames()) * 100; }


	public void setHasNotificationsToggled(boolean status) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".has-notifications-toggled", status);
		SimpleCoinFlips.playerData.saveConfig();
	}

	public void toggleHasNotificationsToggled() {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".has-notifications-toggled", !getHasNotificationsToggled());
		SimpleCoinFlips.playerData.saveConfig();
	}

	public boolean getHasNotificationsToggled() { return ConfigUtil.getBoolean(SimpleCoinFlips.playerData, getUuid() + ".has-notifications-toggled"); }

	public void updateWinnings(double amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-winnings", getWinnings() + amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	public void setWinnings(double amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-winnings", amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	/** @return The total winnings the player has earned from CoinFlips, this value can be negative if they have lost more than they have won. */
	public double getWinnings() { return ConfigUtil.getInt(SimpleCoinFlips.playerData, getUuid() + ".total-losses"); }


	public void setTotalTaxed(double amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-taxed", amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	public void updateTotalTaxed(double amount) {
		SimpleCoinFlips.playerData.getConfig().set(getUuid() + ".total-winnings", getTotalTaxed() + amount);
		SimpleCoinFlips.playerData.saveConfig();
	}

	/** @return How much the player has been taxed on their CoinFlips */
	public double getTotalTaxed() {
		return ConfigUtil.getDouble(SimpleCoinFlips.playerData, getUuid() + "total-taxed");
	}

}
