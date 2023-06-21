package ca.tweetzy.itemtags;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.gui.GuiManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.itemtags.commands.CommandSelf;
import ca.tweetzy.itemtags.listeners.PlayerListeners;
import ca.tweetzy.itemtags.manager.TagPlayerManager;
import ca.tweetzy.itemtags.settings.Settings;
import ca.tweetzy.itemtags.settings.Translations;
import lombok.Getter;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:35 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ItemTags extends FlightPlugin {

	@Getter
	private final GuiManager guiManager = new GuiManager(this);
	private final CommandManager commandManager = new CommandManager(this);
	private final TagPlayerManager tagPlayerManager = new TagPlayerManager();



	@Override
	protected void onFlight() {
		// settings and locale setup
		Settings.init();
		Translations.init();

		Common.setPrefix(Settings.PREFIX.getString());
		Common.setPluginName("&8[&eItemTags&8]");

		this.commandManager.addMainCommand("itemtags").addSubCommands(new CommandSelf());
		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

	}


	public static ItemTags getInstance() {
		return (ItemTags) FlightPlugin.getInstance();
	}

	public static TagPlayerManager getTagPlayerManager() {
		return getInstance().tagPlayerManager;
	}


	@Override
	protected int getBStatsId() {
		return 7550;
	}
}
