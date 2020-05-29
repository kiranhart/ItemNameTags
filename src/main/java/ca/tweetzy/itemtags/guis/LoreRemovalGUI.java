package ca.tweetzy.itemtags.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:11 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class LoreRemovalGUI extends TInventory {

    private Player p;
    private ItemStack stack;
    private List<String> lore;

    public LoreRemovalGUI(Player p, ItemStack stack) {
        setTitle("&8Click to remove lore");
        setPage(1);

        this.p = p;
        this.stack = stack;
        this.lore = Methods.getItemLore(stack);

        if (this.lore.size() <= 9) setRows(1);
        if (this.lore.size() >= 10 && this.lore.size() <= 18) setRows(2);
        if (this.lore.size() >= 19 && this.lore.size() <= 27) setRows(3);
        if (this.lore.size() >= 28 && this.lore.size() <= 36) setRows(4);
        if (this.lore.size() >= 37 && this.lore.size() <= 45) setRows(5);
        if (this.lore.size() >= 46 && this.lore.size() <= 54) setRows(6);

    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());

        // load items
        this.lore.forEach(line -> {
            inventory.setItem(inventory.firstEmpty(), new TItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial())
                    .setName(TextUtils.formatText(line))
                    .setLore(TextUtils.formatText("&7Click to remove this line from the lore"))
                    .toItemStack()
            );
        });
        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        List<String> nLore = this.lore;
        if (slot >= 0 && slot <= 53) {
            nLore.remove(slot);
        }

        String[] arr = new String[nLore.size()];
        arr = nLore.toArray(arr);

        Methods.setItemLore(this.stack, arr);
        ItemTags.getInstance().getPlayersUsingTag().remove(p.getUniqueId());
        e.getViewers().forEach(HumanEntity::closeInventory);
    }
}
