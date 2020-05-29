package ca.tweetzy.itemtags.listeners;

import ca.tweetzy.core.compatibility.CompatibleHand;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.input.ChatPrompt;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.Methods;
import ca.tweetzy.itemtags.guis.LoreRemovalGUI;
import ca.tweetzy.itemtags.itemtag.ItemTagBuilder;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.settings.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 3:31 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class PlayerListeners implements Listener {

    @EventHandler
    public void onTagRedeem(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() != null && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {

            ItemStack is = e.getItem();

            if (is != null && !is.getType().equals(Material.AIR)) {
                // check if item is tag
                if (NBTEditor.contains(is, "ItemTagType")) {
                    // check if player isn't using a tag already
                    if (!ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {

                        // tag type
                        TagType type = TagType.valueOf(NBTEditor.getString(is, "ItemTagType").toUpperCase());

                        // add them to the using list
                        ItemTags.getInstance().getPlayersUsingTag().put(p.getUniqueId(), type);
                        // take a tag
                        PlayerUtils.takeActiveItem(p, CompatibleHand.MAIN_HAND, 1);

                        // send messages
                        ItemTags.getInstance().getLocale().getMessage(type == TagType.ITEM_NAME_TAG ? "redeem.itemnametag" : type == TagType.ITEM_LORE_TAG ? "redeem.itemloretag" : "redeem.itemdeloretag").sendPrefixedMessage(p);
                        ItemTags.getInstance().getLocale().getMessage("redeem.cancelmsg").processPlaceholder("cancel_word", Settings.CANCEL_WORD.getString()).sendPrefixedMessage(p);

                    } else {
                        // send them a no no message
                        ItemTags.getInstance().getLocale().getMessage("usingtag").sendPrefixedMessage(p);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemClickWhileTagActivated(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() != null && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {

            ItemStack is = e.getItem();

            if (is != null && !is.getType().equals(Material.AIR)) {

                // check if player is using a tag
                if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {

                    // Check if the item is part of the blocked list
                    if (XMaterial.matchXMaterial(is).isOneOf(Settings.BLOCKED_ITEMS.getStringList())) {
                        ItemTags.getInstance().getLocale().getMessage("blockeditem").sendPrefixedMessage(p);
                        return;
                    }

                    TagType type = ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId());

                    if (type == TagType.ITEM_NAME_TAG) {
                        ChatPrompt.showPrompt(ItemTags.getInstance(), p, TextUtils.formatText(ItemTags.getInstance().getLocale().getMessage("prompt.asktoentername").getMessage()), handle -> {
                            if (!handle.getMessage().equalsIgnoreCase(Settings.CANCEL_WORD.getString())) {
                                Methods.updateItemName(is, TextUtils.formatText(handle.getMessage()));
                                ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
                            } else {
                                PlayerUtils.giveItem(p, new ItemTagBuilder(ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId())).getTag());
                                ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
                                ItemTags.getInstance().getLocale().getMessage("cancel").sendPrefixedMessage(p);
                            }
                        }).setOnClose(() -> ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId()));
                    } else if (type == TagType.ITEM_LORE_TAG) {
                        ChatPrompt.showPrompt(ItemTags.getInstance(), p, TextUtils.formatText(ItemTags.getInstance().getLocale().getMessage("prompt.asktoenterlore").getMessage()), handle -> {
                            if (!handle.getMessage().equalsIgnoreCase(Settings.CANCEL_WORD.getString())) {
                                Methods.updateItemLore(is, TextUtils.formatText(handle.getMessage()));
                                ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
                            } else {
                                PlayerUtils.giveItem(p, new ItemTagBuilder(ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId())).getTag());
                                ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
                                ItemTags.getInstance().getLocale().getMessage("cancel").sendPrefixedMessage(p);
                            }
                        }).setOnClose(() -> ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId()));
                    } else if (type == TagType.ITEM_DELORE_TAG) {
                        p.openInventory(new LoreRemovalGUI(p, is).getInventory());
                    }
                }
            }
        }
    }

    /*
    Additional Checks for the cancel word, in case they don't type it inside
    the activated chat prompt, or they're using a de lore tag
     */
    @EventHandler
    public void onPlayerSayCancelWord(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {
            if (e.getMessage().equalsIgnoreCase(Settings.CANCEL_WORD.getString())) {
                PlayerUtils.giveItem(p, new ItemTagBuilder(ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId())).getTag());
                ItemTags.getInstance().getLocale().getMessage("cancel").sendPrefixedMessage(p);
                ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
            }
        }
    }

    /*
    If the player ends up leaving without completing their tag
    redeem, give it back to them
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {
            PlayerUtils.giveItem(p, new ItemTagBuilder(ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId())).getTag());
            ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
        }
    }
}