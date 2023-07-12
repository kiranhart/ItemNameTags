package ca.tweetzy.itemtags.model;

import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.itemtags.settings.Settings;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class ItemHelper {

	public ItemStack updateItemName(@NonNull final ItemStack item, @NonNull final String title) {
		final ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(Common.colorize(title));
			item.setItemMeta(meta);
		}
		return item;
	}

	public ItemStack updateItemLore(@NonNull final ItemStack item, @NonNull final String... lore) {
		final ItemMeta meta = item.getItemMeta();
		final List<String> ogLore = (meta.hasLore()) ? meta.getLore() : new ArrayList<>();

		if (meta != null) {
			if (lore != null && lore.length != 0) {
				for (String s : lore) {
					ogLore.add(Common.colorize(Settings.USE_LORE_PREFIX.getBoolean() ? Settings.LORE_PREFIX.getString() + " " + s : s));
				}
				meta.setLore(Common.colorize(ogLore));
			}
			item.setItemMeta(meta);
		}
		return item;
	}

	public ItemStack setItemLore(@NonNull final ItemStack item, @NonNull final String... lore) {
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			if (lore != null && lore.length != 0) {
				meta.setLore(Common.colorize(Arrays.asList((lore))));
			} else {
				meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			}
			item.setItemMeta(meta);
		}
		return item;
	}

	public List<String> getItemLore(@NonNull final ItemStack item) {
		final ItemMeta meta = item.getItemMeta();
		final List<String> ogLore = (meta.hasLore()) ? meta.getLore() : new ArrayList<>();
		return ogLore;
	}
}
