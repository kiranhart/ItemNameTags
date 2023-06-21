package ca.tweetzy.itemtags.guis;

import ca.tweetzy.flight.gui.template.BaseGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:11 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class LoreRemovalGUI extends BaseGUI {

	private final Player player;
	private final ItemStack stack;
//	private final List<String> lore;

	public LoreRemovalGUI(Player player, ItemStack stack) {
		super("&8Click to remove lore");

		this.player = player;
		this.stack = stack;
//		this.lore = Methods.getItemLore(stack);

//		if (this.lore.size() <= 9) setRows(1);
//		if (this.lore.size() >= 10 && this.lore.size() <= 18) setRows(2);
//		if (this.lore.size() >= 19 && this.lore.size() <= 27) setRows(3);
//		if (this.lore.size() >= 28 && this.lore.size() <= 36) setRows(4);
//		if (this.lore.size() >= 37 && this.lore.size() <= 45) setRows(5);
//		if (this.lore.size() >= 46 && this.lore.size() <= 54) setRows(6);

		draw();
	}


	@Override
	protected void draw() {
		int slot = 0;
//		for (String s : this.lore) {
//			int finalSlot = slot;
//			setButton(slot, QuickItem.of(CompMaterial.LIME_STAINED_GLASS_PANE).name(s).lore("&7Click to remove to this line from the lore").make(), e -> {
//				List<String> tempLore = this.lore;
//				tempLore.remove(finalSlot);
//
//				String[] arr = new String[tempLore.size()];
//				arr = tempLore.toArray(arr);
//
////				Methods.setItemLore(this.stack, arr);
//				ItemTags.getInstance().getPlayersUsingTag().remove(this.player.getUniqueId());
//				this.player.updateInventory();
//				e.gui.close();
//			});
//
//			slot++;
//		}
	}
}
