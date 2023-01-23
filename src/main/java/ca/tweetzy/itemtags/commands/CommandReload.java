package ca.tweetzy.itemtags.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.settings.Translations;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:46 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandReload extends Command {

	public CommandReload() {
		super(AllowedExecutor.BOTH, "reload");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		ItemTags.getCoreConfig().init();
		Common.tell(sender, TranslationManager.string(Translations.RELOAD));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "itemtags.reload";
	}

	@Override
	public String getSyntax() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Used to reload the config.yml";
	}
}
