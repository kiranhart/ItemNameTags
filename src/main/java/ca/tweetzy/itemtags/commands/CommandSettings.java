package ca.tweetzy.itemtags.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.configuration.editor.PluginConfigGui;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.itemtags.ItemTags;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: June 22 2021
 * Time Created: 2:42 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class CommandSettings extends AbstractCommand {

    public CommandSettings() {
        super(CommandType.PLAYER_ONLY, "settings");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player player = (Player) sender;
        ItemTags.getInstance().getGuiManager().showGUI(player, new PluginConfigGui(ItemTags.getInstance(), TextUtils.formatText(ItemTags.getInstance().getLocale().getMessage("general.prefix").getMessage())));
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "itemtags.settings";
    }

    @Override
    public String getSyntax() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return "Open the in game config editor";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
