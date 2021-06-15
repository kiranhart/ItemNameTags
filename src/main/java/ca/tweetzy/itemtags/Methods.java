package ca.tweetzy.itemtags;

import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/27/2020
 * Time Created: 11:20 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Methods {

    public static ItemStack getHand(Player player) {
        return ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_8) ? player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
    }

    public static ItemStack updateItemName(ItemStack item, String title) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(TextUtils.formatText(title));
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
                meta.setLore(TextUtils.formatText(ogLore));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack setItemLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (lore != null && lore.length != 0) {
                meta.setLore(TextUtils.formatText(lore));
            } else {
                meta = Bukkit.getItemFactory().getItemMeta(item.getType());
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
