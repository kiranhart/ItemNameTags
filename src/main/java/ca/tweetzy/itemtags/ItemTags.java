package ca.tweetzy.itemtags;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.gui.GuiManager;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.itemtags.commands.CommandGive;
import ca.tweetzy.itemtags.commands.CommandGiveall;
import ca.tweetzy.itemtags.commands.CommandReload;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.listeners.PlayerListeners;
import ca.tweetzy.itemtags.settings.LocaleSettings;
import ca.tweetzy.itemtags.settings.Settings;
import lombok.Getter;
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

    @Getter
    private CommandManager commandManager;

    @Getter
    private final GuiManager guiManager = new GuiManager(this);

    @Getter
    private LinkedHashMap<UUID, TagType> playersUsingTag;

    public static ItemTags getInstance() {
        return instance;
    }

    protected Metrics metrics;

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        TweetyCore.registerPlugin(this, 4, XMaterial.NAME_TAG.name());

        // Shutdown plugin if server version is not 1.8+
        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_7)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Settings.setup();

        // Locale
        setLocale(Settings.LANG.getString());
        LocaleSettings.setup();

        this.guiManager.init();
        this.commandManager = new CommandManager(this);
        this.commandManager.addMainCommand("itemtags").addSubCommands(new CommandGive(), new CommandGiveall(), new CommandReload());
        this.playersUsingTag = new LinkedHashMap<>();

        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);

        // Perform the update check
        getServer().getScheduler().runTaskLaterAsynchronously(this, () -> new UpdateChecker(this, 29641, getConsole()).check(), 1L);

        // metrics
        this.metrics = new Metrics(getInstance(), 7550);

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
}
