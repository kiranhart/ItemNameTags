package ca.tweetzy.itemtags.settings;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.itemtags.ItemTags;

import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:41 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Settings {

    static final Config config = ItemTags.getInstance().getCoreConfig();

    public static final ConfigSetting LANG = new ConfigSetting(config, "locale.lang", "en_US", "The default language file the plugin should use");

    public static final ConfigSetting CANCEL_WORD = new ConfigSetting(config, "cancel word", "cancel", "What should be the cancel word when renaming?");

    public static final ConfigSetting WHITE_LIST_USE = new ConfigSetting(config, "white list.use", false, "Should item tags use a white list system instead of the black list?");
    public static final ConfigSetting WHITE_LIST_ITEMS = new ConfigSetting(config, "white list.items", Arrays.asList(
            "DIAMOND_SWORD",
            "IRON_SWORD"
    ), "Items that the player will be able to rename / remove lore from");

    public static final ConfigSetting BLOCKED_ITEMS = new ConfigSetting(config, "blocked.items", Arrays.asList(
            "ENDER_CHEST", "CHEST", "SPAWNER"
    ), "A list of all the items that should be blocked in the renaming process", "These are just to get you started, you've got to add more on your own.");

    public static final ConfigSetting USE_LORE_PREFIX = new ConfigSetting(config, "tags.lore prefix.use", false, "Should there be a prefix next to added lore");
    public static final ConfigSetting LORE_PREFIX = new ConfigSetting(config, "tags.lore prefix.prefix", "&e&lLORE", "The prefix that should be added to new lore");

    public static final ConfigSetting ITEM_NAME_TAG_MATERIAL = new ConfigSetting(config, "tags.item name tag.item", "NAME_TAG");
    public static final ConfigSetting ITEM_NAME_TAG_NAME = new ConfigSetting(config, "tags.item name tag.name", "&e&lItem Name Tag");
    public static final ConfigSetting ITEM_NAME_TAG_LORE = new ConfigSetting(config, "tags.item name tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the naming process"));

    public static final ConfigSetting ITEM_LORE_TAG_MATERIAL = new ConfigSetting(config, "tags.item lore tag.item", "NAME_TAG");
    public static final ConfigSetting ITEM_LORE_TAG_NAME = new ConfigSetting(config, "tags.item lore tag.name", "&e&lItem Lore Tag");
    public static final ConfigSetting ITEM_LORE_TAG_LORE = new ConfigSetting(config, "tags.item lore tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the naming process"));

    public static final ConfigSetting ITEM_DELORE_TAG_MATERIAL = new ConfigSetting(config, "tags.item delore tag.item", "NAME_TAG");
    public static final ConfigSetting ITEM_DELORE_TAG_NAME = new ConfigSetting(config, "tags.item delore tag.name", "&e&lItem Delore Tag");
    public static final ConfigSetting ITEM_DELORE_TAG_LORE = new ConfigSetting(config, "tags.item delore tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the lore removal process"));


    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
