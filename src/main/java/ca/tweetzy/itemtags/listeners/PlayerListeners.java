package ca.tweetzy.itemtags.listeners;

import ca.tweetzy.core.compatibility.CompatibleHand;
import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.Methods;
import ca.tweetzy.itemtags.guis.LoreRemovalGUI;
import ca.tweetzy.itemtags.itemtag.ItemTagBuilder;
import ca.tweetzy.itemtags.itemtag.TagType;
import ca.tweetzy.itemtags.settings.Settings;
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
    public void onTagRedeem(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() != null && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getHand() == EquipmentSlot.OFF_HAND && ServerVersion.isServerVersionAtLeast(ServerVersion.V1_9)) {
                return;
            }

            ItemStack is = e.getItem();

            if (is == null || is.getType() == XMaterial.AIR.parseMaterial()) {
                return;
            }

            if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId()) && ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId()) == TagType.ITEM_DELORE_TAG) {
                ItemTags.getInstance().getGuiManager().showGUI(p, new LoreRemovalGUI(p, is));
                return;
            }

            if (!NBTEditor.contains(is, "ItemTagType")) {
                return;
            }

            if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {
                ItemTags.getInstance().getLocale().getMessage("usingtag").sendPrefixedMessage(p);
                return;
            }

            // tag type
            TagType type = TagType.valueOf(NBTEditor.getString(is, "ItemTagType").toUpperCase());

            // add them to the using list
            ItemTags.getInstance().getPlayersUsingTag().put(p.getUniqueId(), type);
            // take a tag
            PlayerUtils.takeActiveItem(p, CompatibleHand.MAIN_HAND, 1);

            // send messages
            ItemTags.getInstance().getLocale().getMessage(type == TagType.ITEM_NAME_TAG ? "redeem.itemnametag" : type == TagType.ITEM_LORE_TAG ? "redeem.itemloretag" : "redeem.itemdeloretag").sendPrefixedMessage(p);
            ItemTags.getInstance().getLocale().getMessage("redeem.cancelmsg").processPlaceholder("cancel_word", Settings.CANCEL_WORD.getString()).sendPrefixedMessage(p);
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
                e.setCancelled(true);
                return;
            }

            ItemStack heldItem = Methods.getHand(p);
            if (heldItem.getType() == XMaterial.AIR.parseMaterial()) {
                ItemTags.getInstance().getLocale().getMessage("air").sendPrefixedMessage(p);
                e.setCancelled(true);
                return;
            }

            TagType tagType = ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId());
            switch (tagType) {
                case ITEM_NAME_TAG:
                    Methods.updateItemName(heldItem, e.getMessage());
                    ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
                    e.setCancelled(true);
                    break;
                case ITEM_LORE_TAG:
                    Methods.updateItemLore(heldItem, e.getMessage());
                    ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
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
        Player p = e.getPlayer();
        if (ItemTags.getInstance().getPlayersUsingTag().containsKey(p.getUniqueId())) {
            PlayerUtils.giveItem(p, new ItemTagBuilder(ItemTags.getInstance().getPlayersUsingTag().get(p.getUniqueId())).getTag());
            ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
        }
    }
}