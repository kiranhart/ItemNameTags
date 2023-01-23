package ca.tweetzy.itemtags.settings;

import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.settings.FlightSettings;
import ca.tweetzy.itemtags.ItemTags;

import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:41 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Settings extends FlightSettings {

	public static final ConfigEntry PREFIX = create("prefix", "&8[&eItemTags&8]", "The prefix for the plugin");
	public static final ConfigEntry LANG = create("locale.lang", "en_US", "The default language file the plugin should use");

	public static final ConfigEntry CANCEL_WORD = create("cancel word", "cancel", "What should be the cancel word when renaming?");

	public static final ConfigEntry BLOCKED_WORDS = create("blocked words", Arrays.asList("fuck", "bitch"), "Words that should be blocked when using a tag");
	public static final ConfigEntry MAX_RENAME_LENGTH = create("max rename length", 16, "The maximum length of a rename");
	public static final ConfigEntry USE_MAX_RENAME_LIMIT = create("use max rename length", false, "Should item tags limit the rename length on items?");

	public static final ConfigEntry WHITE_LIST_USE = create("white list.use", false, "Should item tags use a white list system instead of the black list?");
	public static final ConfigEntry WHITE_LIST_ITEMS = create("white list.items", Arrays.asList(
			"DIAMOND_SWORD",
			"IRON_SWORD"
	), "Items that the player will be able to rename / remove lore from");

	public static final ConfigEntry BLOCKED_ITEMS = create("blocked.items", Arrays.asList(
			"ENDER_CHEST", "CHEST", "SPAWNER"
	), "A list of all the items that should be blocked in the renaming process", "These are just to get you started, you've got to add more on your own.");

	public static final ConfigEntry USE_LORE_PREFIX = create("tags.lore prefix.use", false, "Should there be a prefix next to added lore");
	public static final ConfigEntry LORE_PREFIX = create("tags.lore prefix.prefix", "&e&lLORE", "The prefix that should be added to new lore");

	public static final ConfigEntry ITEM_NAME_TAG_MATERIAL = create("tags.item name tag.item", "NAME_TAG");
	public static final ConfigEntry ITEM_NAME_TAG_NAME = create("tags.item name tag.name", "&e&lItem Name Tag");
	public static final ConfigEntry ITEM_NAME_TAG_LORE = create("tags.item name tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the naming process"));

	public static final ConfigEntry ITEM_LORE_TAG_MATERIAL = create("tags.item lore tag.item", "NAME_TAG");
	public static final ConfigEntry ITEM_LORE_TAG_NAME = create("tags.item lore tag.name", "&e&lItem Lore Tag");
	public static final ConfigEntry ITEM_LORE_TAG_LORE = create("tags.item lore tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the naming process"));

	public static final ConfigEntry ITEM_DELORE_TAG_MATERIAL = create("tags.item delore tag.item", "NAME_TAG");
	public static final ConfigEntry ITEM_DELORE_TAG_NAME = create("tags.item delore tag.name", "&e&lItem Delore Tag");
	public static final ConfigEntry ITEM_DELORE_TAG_LORE = create("tags.item delore tag.lore", Arrays.asList("&7Right-Click the tag to begin using it", "&7You can always cancel the lore removal process"));


	public static void init() {
		ItemTags.getCoreConfig().init();
	}
}
