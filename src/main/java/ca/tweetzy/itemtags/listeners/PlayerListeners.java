package ca.tweetzy.itemtags.listeners;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.comp.enums.ServerVersion;
import ca.tweetzy.flight.nbtapi.NBT;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.factory.TagFactory;
import ca.tweetzy.itemtags.settings.Settings;
import ca.tweetzy.itemtags.settings.Translations;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 3:31 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PlayerListeners implements Listener {

	@EventHandler
	public void onTagRedeem(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();

		if (event.getAction() != null && event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (ServerVersion.isServerVersionAtLeast(ServerVersion.V1_9)) {
				if (event.getHand() == EquipmentSlot.OFF_HAND) return;
			}

			ItemStack is = event.getItem();

			if (is == null || is.getType() == CompMaterial.AIR.parseMaterial() || is.getAmount() == 0)
				return;


//			if (!ItemTags.getTagPlayerManager().contains(player.getUniqueId())) {
//				return;
//			}
//
//			final TagType tagTypeBeingUsed = ItemTags.getTagPlayerManager().get(player.getUniqueId());
//
//
//			// special shit for delore tag
//			if (tagTypeBeingUsed == TagType.ITEM_DELORE_TAG) {
//				if (Settings.WHITE_LIST_USE.getBoolean() && Settings.WHITE_LIST_ITEMS.getStringList().stream().noneMatch(allowed -> allowed.equalsIgnoreCase(is.getType().name()))) return;
//				if (!Settings.WHITE_LIST_USE.getBoolean() && Settings.BLOCKED_ITEMS.getStringList().stream().anyMatch(blocked -> blocked.equalsIgnoreCase(is.getType().name()))) return;
//
////				ItemTags.getInstance().getGuiManager().showGUI(p, new LoreRemovalGUI(p, is));
//				return;
//			}

			if (!NBT.get(is, nbt -> nbt.hasTag("ItemTagType"))) {
				return;
			}

			if (ItemTags.getTagPlayerManager().contains(player.getUniqueId())) {
				Common.tell(player, TranslationManager.string(Translations.USING_TAG));
				return;
			}

			event.setCancelled(true);

			// add them to the using list
			final TagType type = NBT.get(is, nbt -> nbt.getEnum("ItemTagType", TagType.class));
			ItemTags.getTagPlayerManager().add(player.getUniqueId(), type);

			// take a tag
			if (is.getAmount() >= 2) {
				is.setAmount(is.getAmount() - 1);
			} else {
				if (ServerVersion.isServerVersionAbove(ServerVersion.V1_8)) {
					player.getInventory().setItemInMainHand(CompMaterial.AIR.parseItem());
				} else {
					player.getInventory().setItemInHand(CompMaterial.AIR.parseItem());
				}
			}

			// send messages
			Common.tell(player, TranslationManager.string(
					type == TagType.ITEM_NAME_TAG ? Translations.TAG_ITEM_NAME_RENAME : type == TagType.ITEM_LORE_TAG ? Translations.TAG_ITEM_LORE_RENAME : Translations.TAG_ITEM_DELORE_RENAME
			));

			Common.tell(player, TranslationManager.string(Translations.TAG_CANCEL_MSG, "cancel_word", Settings.CANCEL_WORD.getString()));
		}
	}

	/*
	Additional Checks for the cancel word, in case they don't type it inside
	the activated chat prompt, or they're using a de lore tag
	 */
	@EventHandler
	public void onPlayerSayCancelWord(AsyncPlayerChatEvent e) {
		final Player player = e.getPlayer();

		if (ItemTags.getTagPlayerManager().contains(player.getUniqueId())) {
			final String msg = ChatColor.stripColor(e.getMessage());

			if (msg.equalsIgnoreCase(Settings.CANCEL_WORD.getString())) {
				PlayerUtil.giveItem(player, TagFactory.request(ItemTags.getTagPlayerManager().get(player.getUniqueId())));
				Common.tell(player, TranslationManager.string(Translations.CANCEL));
				ItemTags.getTagPlayerManager().remove(player.getUniqueId());
				e.setCancelled(true);
				return;
			}

			ItemStack heldItem = Methods.getHand(player);

			if (heldItem.getType() == CompMaterial.AIR.parseMaterial()) {
				Common.tell(player, TranslationManager.string(Translations.AIR));

				e.setCancelled(true);
				return;
			}

			if (Settings.WHITE_LIST_USE.getBoolean() && Settings.WHITE_LIST_ITEMS.getStringList().stream().noneMatch(allowed -> allowed.equalsIgnoreCase(heldItem.getType().name())) || !Settings.WHITE_LIST_USE.getBoolean() && Settings.BLOCKED_ITEMS.getStringList().stream().anyMatch(blocked -> blocked.equalsIgnoreCase(heldItem.getType().name()))) {
				Common.tell(player, TranslationManager.string(Translations.BLOCKED_ITEM));
				e.setCancelled(true);
				return;
			}

			for (String s : Settings.BLOCKED_WORDS.getStringList()) {
				if (Methods.match(s, e.getMessage())) {
					Common.tell(player, TranslationManager.string(Translations.BLOCKED_WORD));

					e.setCancelled(true);
					return;
				}
			}

			if (Settings.USE_MAX_RENAME_LIMIT.getBoolean()) {
				if (Common.colorize(e.getMessage()).length() > Settings.MAX_RENAME_LENGTH.getInt()) {
					Common.tell(player, TranslationManager.string(Translations.MAX_RENAME_LENGTH));
					e.setCancelled(true);
					return;
				}
			}

			TagType tagType = ItemTags.getInstance().getPlayersUsingTag().get(player.getUniqueId());
			switch (tagType) {
				case ITEM_NAME_TAG:
					Methods.updateItemName(heldItem, e.getMessage());
					ItemTags.getInstance().getPlayersUsingTag().remove(player.getUniqueId());
					player.updateInventory();
					e.setCancelled(true);
					break;
				case ITEM_LORE_TAG:
					Methods.updateItemLore(heldItem, e.getMessage());
					ItemTags.getInstance().getPlayersUsingTag().remove(player.getUniqueId());
					player.updateInventory();
					e.setCancelled(true);
					break;
			}
		}
	}

	/*
	If the player ends up leaving without completing their tag
	redeem, give it back to them
	 */
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		final Player player = e.getPlayer();

		if (ItemTags.getTagPlayerManager().contains(player.getUniqueId())) {
			PlayerUtil.giveItem(player, TagFactory.request(ItemTags.getTagPlayerManager().get(player.getUniqueId())));
			ItemTags.getTagPlayerManager().remove(player.getUniqueId());
		}
	}
}