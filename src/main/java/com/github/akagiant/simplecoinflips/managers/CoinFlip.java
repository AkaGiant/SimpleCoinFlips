package com.github.akagiant.simplecoinflips.managers;

import com.github.akagiant.simplecoinflips.SimpleCoinFlips;
import com.github.akagiant.simplecoinflips.util.ConfigUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoinFlip {

	// The UUID of the creator of the CF
	@Getter
	private final UUID creator;

	// The UUID of the challenger of the CF
	@Getter
	private UUID challenger;

	// The UUID of the CF itself.
	@Getter
	private final UUID cfId = UUID.randomUUID();

	@Getter
	private final double worth;

	@Getter
	private final CoinFlipTax coinFlipTax;

	@Getter
	private final String creatorsCoinFlipSide;

	@Getter
	public static List<CoinFlip> coinFlipList = new ArrayList<>();

	public CoinFlip(UUID creator, double worth, String creatorsCoinFlipSide) {
		this.creator = creator;
		this.worth = worth;
		this.creatorsCoinFlipSide = creatorsCoinFlipSide;

		coinFlipTax = new CoinFlipTax(worth);

		coinFlipList.add(this);

		// Todo: Inform creator that it has been created
		// Todo: Inform server that it has been created.
	}

}
