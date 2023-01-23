package ca.tweetzy.itemtags.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.itemtags.itemtag.ItemTagBuilder;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.settings.Translations;
import org.apache.commons.lang3.math.NumberUtils;
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
public class CommandGiveall extends Command {

	public CommandGiveall() {
		super(AllowedExecutor.BOTH, "giveall");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (args.length < 2) return ReturnType.INVALID_SYNTAX;

		if (!EnumSet.allOf(TagType.class).stream().map(e -> e.name().toLowerCase()).collect(Collectors.toList()).contains(args[0].toLowerCase())) {
			Common.tell(sender, TranslationManager.string(Translations.INVALID_TAG));
			return ReturnType.FAIL;
		}

		if (!NumberUtils.isNumber(args[1])) {
			Common.tell(sender, TranslationManager.string(Translations.NOT_A_NUMBER, "value", args[1]));
			return ReturnType.FAIL;
		}

		final ItemStack tag = new ItemTagBuilder(TagType.valueOf(args[0].toUpperCase())).getTag();
		tag.setAmount(Integer.parseInt(args[1]));

		Bukkit.getOnlinePlayers().forEach(p -> PlayerUtil.giveItem(p, tag));
		Common.tell(sender, TranslationManager.string(Translations.TAG_GIVE_ALL, "tag_type", args[0], "amount", args[1]));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
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
