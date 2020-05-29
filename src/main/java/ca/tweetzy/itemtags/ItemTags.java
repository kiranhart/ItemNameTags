package ca.tweetzy.itemtags;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.core.PluginID;
import ca.tweetzy.core.locale.Locale;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.itemtags.commands.CommandGive;
import ca.tweetzy.itemtags.commands.CommandGiveall;
import ca.tweetzy.itemtags.commands.CommandReload;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.listeners.PlayerListeners;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:35 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ItemTags extends TweetyPlugin {

    private static ItemTags instance;

    private CommandManager commandManager;
    private Locale locale;

    private LinkedHashMap<UUID, TagType> playersUsingTag;

    public static ItemTags getInstance() {
        return instance;
    }

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        TweetyCore.registerPlugin(this, (int) PluginID.ITEM_TAGS.getTweetzyID(), "NAME_TAG");
        TweetyCore.initEvents(this);

        Settings.setup();

        new Locale(this, "en_US");
        this.locale = Locale.getLocale(Settings.LANG.getString(), Settings.PREFIX.getString());

        this.commandManager = new CommandManager(this);
        this.commandManager.addMainCommand("itemtags").addSubCommands(new CommandGive(), new CommandGiveall(), new CommandReload());

        this.playersUsingTag = new LinkedHashMap<>();

        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);

        new HartUpdater(this, getDescription().getVersion());

        // metrics
        if (Settings.METRICS.getBoolean()) {
            new Metrics(getInstance(), (int) PluginID.ITEM_TAGS.getbStatsID());
        }

    }

    @Override
    public void onPluginDisable() {
        instance = null;
    }

    @Override
    public void onConfigReload() {

    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    public Locale getLocale() {
        return locale;
    }

    public LinkedHashMap<UUID, TagType> getPlayersUsingTag() {
        return playersUsingTag;
    }
}
