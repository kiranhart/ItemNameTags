package ca.tweetzy.itemtags;

import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/27/2020
 * Time Created: 11:20 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Methods {

    public static ItemStack updateItemName(ItemStack item, String title) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(title);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack updateItemLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> ogLore = (meta.hasLore()) ? meta.getLore() : new ArrayList<>();
        if (meta != null) {
            if (lore != null && lore.length != 0) {
                for (String s : lore) {
                    ogLore.add(TextUtils.formatText(Settings.USE_LORE_PREFIX.getBoolean() ? Settings.LORE_PREFIX.getString() + " " + s : s));
                }
                meta.setLore(ogLore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack setItemLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (lore != null && lore.length != 0) {
                meta.setLore(new ArrayList<>(Arrays.asList(lore)));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static List<String> getItemLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> ogLore = (meta.hasLore()) ? meta.getLore() : new ArrayList<>();
        return ogLore;
    }
}
