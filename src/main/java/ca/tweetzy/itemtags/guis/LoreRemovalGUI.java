package ca.tweetzy.itemtags.guis;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.template.BaseGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.itemtags.ItemTags;
import ca.tweetzy.itemtags.model.ItemHelper;
import ca.tweetzy.itemtags.settings.Translations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/28/2020
 * Time Created: 9:11 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class LoreRemovalGUI extends BaseGUI {

	private final Player player;
	private final ItemStack stack;
	private final List<String> lore;

	public LoreRemovalGUI(Player player, ItemStack stack) {
		super(TranslationManager.string(player, Translations.GUI_LORE_REMOVAL_TITLE));

		this.player = player;
		this.stack = stack;
		this.lore = ItemHelper.getItemLore(stack);

		if (this.lore.size() <= 9) setRows(1);
		if (this.lore.size() >= 10 && this.lore.size() <= 18) setRows(2);
		if (this.lore.size() >= 19 && this.lore.size() <= 27) setRows(3);
		if (this.lore.size() >= 28 && this.lore.size() <= 36) setRows(4);
		if (this.lore.size() >= 37 && this.lore.size() <= 45) setRows(5);
		if (this.lore.size() >= 46 && this.lore.size() <= 54) setRows(6);

		draw();
	}

	@Override
	protected void draw() {
		int slot = 0;
		for (String s : this.lore) {
			int finalSlot = slot;
			setButton(slot, QuickItem.of(CompMaterial.LIME_STAINED_GLASS_PANE).name(s).lore(TranslationManager.list(Translations.GUI_LORE_REMOVAL_REMOVE_LINE)).make(), e -> {
				List<String> tempLore = this.lore;
				tempLore.remove(finalSlot);

				String[] arr = new String[tempLore.size()];
				arr = tempLore.toArray(arr);

				ItemHelper.setItemLore(this.stack, arr);
				ItemTags.getTagPlayerManager().remove(this.player.getUniqueId());
				this.player.updateInventory();
				e.gui.close();
			});

			slot++;
		}
	}

	@Override
	protected ItemStack getBackButton() {
		return QuickItem
				.of(CompMaterial.DARK_OAK_DOOR)
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_BACK_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_BACK_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getExitButton() {
		return QuickItem
				.of(CompMaterial.BARRIER)
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_EXIT_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_EXIT_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getPreviousButton() {
		return QuickItem
				.of(CompMaterial.ARROW)
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_PREVIOUS_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_PREVIOUS_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getNextButton() {
		return QuickItem
				.of(CompMaterial.ARROW)
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_NEXT_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_NEXT_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected int getPreviousButtonSlot() {
		return 48;
	}

	@Override
	protected int getNextButtonSlot() {
		return 50;
	}
}
