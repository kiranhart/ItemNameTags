package ca.tweetzy.itemtags.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.gui.Gui;
import ca.tweetzy.core.gui.GuiUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.Methods;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:11 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class LoreRemovalGUI extends Gui {

    private final Player player;
    private final ItemStack stack;
    private final List<String> lore;

    public LoreRemovalGUI(Player player, ItemStack stack) {
        setTitle(TextUtils.formatText("&8Click to remove lore"));
        setAllowDrops(false);
        setAcceptsItems(false);
        setUseLockedCells(true);
        setDefaultItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

        this.player = player;
        this.stack = stack;
        this.lore = Methods.getItemLore(stack);

        if (this.lore.size() <= 9) setRows(1);
        if (this.lore.size() >= 10 && this.lore.size() <= 18) setRows(2);
        if (this.lore.size() >= 19 && this.lore.size() <= 27) setRows(3);
        if (this.lore.size() >= 28 && this.lore.size() <= 36) setRows(4);
        if (this.lore.size() >= 37 && this.lore.size() <= 45) setRows(5);
        if (this.lore.size() >= 46 && this.lore.size() <= 54) setRows(6);

        draw();
    }

    private void draw() {
        int slot = 0;
        for (String s : this.lore) {
            int finalSlot = slot;
            setButton(slot, GuiUtils.createButtonItem(XMaterial.LIME_STAINED_GLASS_PANE, TextUtils.formatText(s), "&7Click to remove to this line from the lore"), e -> {
                List<String> tempLore = this.lore;
                tempLore.remove(finalSlot);

                String[] arr = new String[tempLore.size()];
                arr = tempLore.toArray(arr);

                Methods.setItemLore(this.stack, arr);
                ItemTags.getInstance().getPlayersUsingTag().remove(this.player.getUniqueId());
                e.gui.close();
            });

            slot++;
        }
    }
}
