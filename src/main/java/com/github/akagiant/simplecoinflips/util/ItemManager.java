package com.github.akagiant.simplecoinflips.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import me.akagiant.giantapi.util.ColorManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemManager {

	private ItemManager() {
		//no instance
	}

	public static ItemStack makeItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ColorManager.formatColours(displayName));
		meta.setLore(Arrays.stream(lore).map(ColorManager::formatColours).collect(Collectors.toList()));
		item.setItemMeta(meta);
		return item;
	}

	// Uses the NBT API (https://www.spigotmc.org/resources/nbt-api.7939/) for handling custom Skulls
	public static ItemStack createCustomSkull(@NotNull String textureValue, @NotNull String displayName, @Nullable String... lore) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
		NBTItem nbti = new NBTItem(head);

		NBTCompound skull = nbti.addCompound("SkullOwner");
		skull.setString("Id", String.valueOf(UUID.randomUUID()));

		NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
		texture.setString("Value",  textureValue);

		head = nbti.getItem();

		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName(ColorManager.formatColours(displayName));
		meta.setLore(Arrays.stream(lore).filter(Objects::nonNull).map(ColorManager::formatColours).collect(Collectors.toList()));
		head.setItemMeta(meta);

		return head;
	}

	// Todo, make compatible with old skulls.
	public static ItemStack createPlayerHead(@NotNull String playerName, @Nullable String overrideDisplayName, @Nullable String... lore) {
		ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

		if (overrideDisplayName != null) { meta.setDisplayName(ColorManager.formatColours(overrideDisplayName)); }
		else { meta.setDisplayName(ColorManager.formatColours(playerName)); }

		meta.setLore(Arrays.stream(lore).filter(Objects::nonNull).map(ColorManager::formatColours).collect(Collectors.toList()));
		meta.setOwner(playerName);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack createPlayerHead(@NotNull UUID playerId, @Nullable String overrideDisplayName, @Nullable String... lore) {
		ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

		OfflinePlayer player = Bukkit.getOfflinePlayer(playerId);

		if (overrideDisplayName != null) { meta.setDisplayName(ColorManager.formatColours(overrideDisplayName)); }
		else { meta.setDisplayName(ColorManager.formatColours(player.getName())); }

		meta.setLore(Arrays.stream(lore).filter(Objects::nonNull).map(ColorManager::formatColours).collect(Collectors.toList()));
		meta.setOwner(player.getName());
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack createPlayerHead(@NotNull UUID playerId) {
		ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

		OfflinePlayer player = Bukkit.getOfflinePlayer(playerId);

		meta.setDisplayName(ColorManager.formatColours(player.getName()));
		meta.setOwner(player.getName());
		itemStack.setItemMeta(meta);
		return itemStack;
	}


}