package ca.tweetzy.itemtags.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.factory.TagFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class CommandSelf extends Command {

	public CommandSelf() {
		super(AllowedExecutor.PLAYER, "self");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (!(sender instanceof Player)) return ReturnType.FAIL;

		final Player player = (Player) sender;
		PlayerUtil.giveItem(player, TagFactory.request(TagType.ITEM_NAME_TAG));


		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return null;
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}
}
