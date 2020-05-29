package ca.tweetzy.itemtags.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.itemtags.ItemTags;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:46 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandReload extends AbstractCommand {

    public CommandReload() {
        super(CommandType.CONSOLE_OK, "reload");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        ItemTags.getInstance().getCoreConfig().load();
        ItemTags.getInstance().getLocale().getMessage("reload").sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
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
