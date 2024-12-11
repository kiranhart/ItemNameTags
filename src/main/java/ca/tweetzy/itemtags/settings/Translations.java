package ca.tweetzy.itemtags.settings;

import ca.tweetzy.flight.settings.TranslationEntry;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.itemtags.ItemTags;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

public final class Translations extends TranslationManager {

	public Translations(@NonNull JavaPlugin plugin) {
		super(plugin);
	}

	public static void init() {
		new Translations(ItemTags.getInstance()).setup(ItemTags.getInstance());
	}

	public static TranslationEntry INVALID_TAG = create("error.invalid tag", "&cThat is not a valid tag type!");
	public static TranslationEntry USING_TAG = create("error.using tag", "&cPlease finish using your current redeemed tag, before using another!");
	public static TranslationEntry BLOCKED_ITEM = create("error.blocked item", "&cYou're not allowed to rename that item!");
	public static TranslationEntry BLOCKED_WORD = create("error.blocked word", "&cYou cannot use that word in your rename");
	public static TranslationEntry MAX_RENAME_LENGTH = create("error.max rename length", "&cThat phrase is too long, cannot rename");
	public static TranslationEntry AIR = create("error.air", "&cYou cannot rename air.");


	public static TranslationEntry TAG_GIVE_ALL = create("success.give all tag", "&aGave &fx&e%amount% &e%tag_type%&a to everyone");
	public static TranslationEntry TAG_GIVE = create("success.give tag", "&aGave &fx&e%amount% &e%tag_type%&a to the player&f: &e%player%");
	public static TranslationEntry CANCEL = create("success.canceled rename", "&cYou cancelled the tag usage, your item tag has been given back");
	public static TranslationEntry RELOAD = create("success.reload", "&aReloaded the configuration file successfully");

	public static TranslationEntry TAG_CANCEL_MSG = create("redeem.cancel message", "&eYou can always use &F'&b%cancel_word%&f'&e to cancel");
	public static TranslationEntry TAG_ITEM_NAME_RENAME = create("redeem.item name tag", "&ePlease enter the new item name in chat!.");
	public static TranslationEntry TAG_ITEM_LORE_RENAME = create("redeem.item lore tag", "&ePlease enter new lore addition in chat!");
	public static TranslationEntry TAG_ITEM_DELORE_RENAME = create("redeem.item delore tag", "&aPlease right click the item you wish to remove lore from!\"");

	public static TranslationEntry GUI_LORE_REMOVAL_TITLE = create("gui.lore removal.title", "&8Click to remove lore");
	public static TranslationEntry GUI_LORE_REMOVAL_REMOVE_LINE = create("gui.lore removal.remove line", "&7Click to remove to this line from the lore");

	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_NAME = create("gui.shared buttons.back button.name", "<GRADIENT:65B1B4>&LGo Back</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_LORE = create("gui.shared buttons.back button.lore",
			"&e&l%left_click% &7to go back"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_NAME = create("gui.shared buttons.exit button.name", "<GRADIENT:65B1B4>&LExit</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_LORE = create("gui.shared buttons.exit button.lore",
			"&e&l%left_click% &7to exit menu"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_NAME = create("gui.shared buttons.previous button.name", "<GRADIENT:65B1B4>&lPrevious Page</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_LORE = create("gui.shared buttons.previous button.lore",
			"&e&l%left_click% &7to go back a page"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_NAME = create("gui.shared buttons.next button.name", "<GRADIENT:65B1B4>&lNext Page</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_LORE = create("gui.shared buttons.next button.lore",
			"&e&l%left_click% &7to go to next page"
	);
}
