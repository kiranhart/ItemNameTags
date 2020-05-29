package ca.tweetzy.itemtags.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.itemtag.ItemTagBuilder;
import ca.tweetzy.itemtags.itemtag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 3:30 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandGiveall extends AbstractCommand {

    public CommandGiveall() {
        super(CommandType.CONSOLE_OK, "giveall");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length < 2) return ReturnType.SYNTAX_ERROR;

        if (!EnumSet.allOf(TagType.class).stream().map(e -> e.name().toLowerCase()).collect(Collectors.toList()).contains(args[0].toLowerCase())) {
            ItemTags.getInstance().getLocale().getMessage("invalidtag").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (!NumberUtils.isInt(args[1])) {
            ItemTags.getInstance().getLocale().getMessage("notanumber").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        ItemStack tag = new ItemTagBuilder(TagType.valueOf(args[0].toUpperCase())).getTag();
        tag.setAmount(Integer.parseInt(args[1]));
        Bukkit.getOnlinePlayers().forEach(p -> PlayerUtils.giveItem(p, tag));

        ItemTags.getInstance().getLocale().getMessage("taggiveall").processPlaceholder("tag_type", args[0]).processPlaceholder("amount", args[1]).sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) {
            return EnumSet.allOf(TagType.class).stream().map(Enum::name).collect(Collectors.toList());
        }

        if (args.length == 2) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        }

        return null;
    }

    @Override
    public String getPermissionNode() {
        return "itemtags.giveall";
    }

    @Override
    public String getSyntax() {
        return "giveall <tag> <#>";
    }

    @Override
    public String getDescription() {
        return "Used to give everyone a specific tag";
    }
}
