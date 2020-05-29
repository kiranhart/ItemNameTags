package ca.tweetzy.itemtags.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.itemtag.ItemTagBuilder;
import ca.tweetzy.itemtags.itemtag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:45 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class CommandGive extends AbstractCommand {

    public CommandGive() {
        super(CommandType.CONSOLE_OK, "give");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length < 3) return ReturnType.SYNTAX_ERROR;

        Player target = Bukkit.getPlayerExact(args[0]);
        if (!target.isOnline() || target == null) {
            ItemTags.getInstance().getLocale().getMessage("playeroffline").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (!EnumSet.allOf(TagType.class).stream().map(e -> e.name().toLowerCase()).collect(Collectors.toList()).contains(args[1].toLowerCase())) {
            ItemTags.getInstance().getLocale().getMessage("invalidtag").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (!NumberUtils.isInt(args[2])) {
            ItemTags.getInstance().getLocale().getMessage("notanumber").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        // give tags
        ItemStack tag = new ItemTagBuilder(TagType.valueOf(args[1].toUpperCase())).getTag();
        tag.setAmount(Integer.parseInt(args[2]));
        PlayerUtils.giveItem(target, tag);

        ItemTags.getInstance().getLocale().getMessage("taggive").processPlaceholder("amount", args[2]).processPlaceholder("player", args[0]).processPlaceholder("tag_type", args[1]).sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        }

        if (args.length == 2) {
            return EnumSet.allOf(TagType.class).stream().map(Enum::name).collect(Collectors.toList());
        }

        if (args.length == 3) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        }

        return null;
    }

    @Override
    public String getPermissionNode() {
        return "itemtags.give";
    }

    @Override
    public String getSyntax() {
        return "give <player> <tag> <#>";
    }

    @Override
    public String getDescription() {
        return "Used to give a player an item tag";
    }
}
