package ca.tweetzy.itemtags;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.comp.enums.ServerVersion;
import ca.tweetzy.flight.gui.GuiManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.itemtags.commands.CommandGive;
import ca.tweetzy.itemtags.commands.CommandGiveall;
import ca.tweetzy.itemtags.commands.CommandReload;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.listeners.PlayerListeners;
import ca.tweetzy.itemtags.settings.Settings;
import ca.tweetzy.itemtags.settings.Translations;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:35 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ItemTags extends FlightPlugin {

	@Getter
	private CommandManager commandManager;

	@Getter
	private final GuiManager guiManager = new GuiManager(this);

	@Getter
	private LinkedHashMap<UUID, TagType> playersUsingTag;


	@Override
	protected void onFlight() {
		// Shutdown plugin if server version is not 1.8+
		if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_7)) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		Settings.init();
		Translations.init();

		Common.setPrefix(Settings.PREFIX.getStringOr("&8[&eItemTags&8]"));

		this.guiManager.init();
		this.commandManager = new CommandManager(this);
		this.commandManager.addMainCommand("itemtags").addSubCommands(new CommandGive(), new CommandGiveall(), new CommandReload());

		this.playersUsingTag = new LinkedHashMap<>();

		Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);

		// Perform the update check
		getServer().getScheduler().runTaskLaterAsynchronously(this, () -> new UpdateChecker(this, 29641, Bukkit.getConsoleSender()).check(), 1L);
	}


	public static ItemTags getInstance() {
		return (ItemTags) FlightPlugin.getInstance();
	}

	@Override
	protected int getBStatsId() {
		return 7550;
	}
}
