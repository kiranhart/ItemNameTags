package ca.tweetzy.itemtags.settings;

import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.itemtags.ItemTags;

import java.util.HashMap;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 18 2021
 * Time Created: 1:44 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class LocaleSettings {

    static final HashMap<String, String> languageNodes = new HashMap<>();

    static {
        languageNodes.put("general.prefix", "&8[&eItemTags&8]");
        languageNodes.put("playeroffline", "&cThat player is currently offline!");
        languageNodes.put("invalidtag", "&cThat is not a valid tag type!");
        languageNodes.put("notanumber", "&cThat is not a valid number!");
        languageNodes.put("taggive", "&aGave &fx&e%amount% &e%tag_type%&a to the player&f: &e%player%");
        languageNodes.put("taggiveall", "&aGave &fx&e%amount% &e%tag_type%&a to everyone");
        languageNodes.put("usingtag", "&cPlease finish using your current redeemed tag, before using another!");
        languageNodes.put("blockeditem", "&cYou're not allowed to rename that item!");
        languageNodes.put("cancel", "&cYou cancelled the tag usage, your item tag has been given back");
        languageNodes.put("reload", "&aReloaded the configuration file successfully");
        languageNodes.put("air", "&cYou cannot rename air.");
        languageNodes.put("blockedword", "&cYou cannot use that word in your rename");
        languageNodes.put("maxrenamelength", "&cThat phrase is too long, cannot rename");

        languageNodes.put("redeem.cancelmsg", "&eYou can always use &F'&b%cancel_word%&f'&e to cancel");
        languageNodes.put("redeem.itemnametag", "&ePlease enter the new item name in chat!");
        languageNodes.put("redeem.itemloretag", "&ePlease enter new lore addition in chat!");
        languageNodes.put("redeem.itemdeloretag", "&aPlease right click the item you wish to remove lore from!");
    }

    public static void setup() {
        Config config = ItemTags.getInstance().getLocale().getConfig();

        languageNodes.keySet().forEach(key -> {
            config.setDefault(key, languageNodes.get(key));
        });

        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
