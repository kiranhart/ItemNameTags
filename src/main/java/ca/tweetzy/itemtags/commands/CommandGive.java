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
public class CommandGive extends Command {

	public CommandGive() {
		super(AllowedExecutor.BOTH, "give");

	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (args.length < 3) return ReturnType.INVALID_SYNTAX;

		final Player target = Bukkit.getPlayerExact(args[0]);

		if (target == null || !target.isOnline()) {
			Common.tell(sender, TranslationManager.string(Translations.PLAYER_OFFLINE));
			return ReturnType.FAIL;
		}

		if (!EnumSet.allOf(TagType.class).stream().map(e -> e.name().toLowerCase()).collect(Collectors.toList()).contains(args[1].toLowerCase())) {
			Common.tell(sender, TranslationManager.string(Translations.INVALID_TAG));
			return ReturnType.FAIL;
		}

		if (!NumberUtils.isNumber(args[2])) {
			Common.tell(sender, TranslationManager.string(Translations.NOT_A_NUMBER, "value", args[2]));
			return ReturnType.FAIL;
		}

		// give tags
		final ItemStack tag = new ItemTagBuilder(TagType.valueOf(args[1].toUpperCase())).getTag();
		tag.setAmount(Integer.parseInt(args[2]));
		PlayerUtil.giveItem(target, tag);

		Common.tell(sender, TranslationManager.string(Translations.TAG_GIVE, "amount", args[2], "player", args[0], "tag_type", args[1]));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
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
