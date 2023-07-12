package ca.tweetzy.itemtags.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.factory.TagFactory;
import ca.tweetzy.itemtags.settings.Translations;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class CommandGive extends Command {

	public CommandGive() {
		super(AllowedExecutor.BOTH, "give");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		// <player / *> <tag> <#>

		final boolean isGivingAll = args[0].equals("*");
		final Player target = Bukkit.getPlayerExact(args[0]);

		if (!isGivingAll && target == null) {
			Common.tell(sender, TranslationManager.string(Translations.PLAYER_NOT_FOUND, "value", args[0]));
			return ReturnType.FAIL;
		}

		TagType tagType = null;
		for (TagType tag : TagType.values())
			if (tag.getType().equalsIgnoreCase(args[1]))
				tagType = tag;

		if (tagType == null) {
			Common.tell(sender, TranslationManager.string(Translations.INVALID_TAG));
			return ReturnType.FAIL;
		}

		final int amount = NumberUtils.isNumber(args[2]) ? Integer.parseInt(args[2]) : 1;

		for (int i = 0; i < amount; i++)
			if (isGivingAll)
				for (Player player : Bukkit.getOnlinePlayers()) {
					PlayerUtil.giveItem(player, TagFactory.request(tagType));
					Common.tell(sender, TranslationManager.string(Translations.TAG_GIVE_ALL, "amount", amount, "tag_type", ChatUtil.capitalizeFully(tagType)));
				}
			else {
				PlayerUtil.giveItem(target, TagFactory.request(tagType));
				Common.tell(sender, TranslationManager.string(Translations.TAG_GIVE, "amount", amount, "player", args[1], "tag_type", ChatUtil.capitalizeFully(tagType)));
			}

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		if (args.length == 1)
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		if (args.length == 2)
			return Arrays.stream(TagType.values()).map(TagType::getType).collect(Collectors.toList());
		if (args.length == 3)
			return Arrays.asList("1", "2", "3", "4", "5");
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "itemtags.command.give";
	}

	@Override
	public String getSyntax() {
		return "itemtags give <player/*> <tag> <#>";
	}

	@Override
	public String getDescription() {
		return "Gives player(s) a # of tags";
	}
}
