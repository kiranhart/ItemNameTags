package ca.tweetzy.itemtags.itemtag;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 3:07 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ItemTagBuilder {

	private final TagType type;
	private CompMaterial material;
	private String name;
	private List<String> lore;

	public ItemTagBuilder(TagType type) {
		this.type = type;
		switch (this.type) {
			case ITEM_NAME_TAG:
				this.material = CompMaterial.matchCompMaterial(Settings.ITEM_NAME_TAG_MATERIAL.getString()).get();
				this.name = Settings.ITEM_NAME_TAG_NAME.getString();
				this.lore = Settings.ITEM_NAME_TAG_LORE.getStringList();
				break;
			case ITEM_LORE_TAG:
				this.material = CompMaterial.matchCompMaterial(Settings.ITEM_LORE_TAG_MATERIAL.getString()).get();
				this.name = Settings.ITEM_LORE_TAG_NAME.getString();
				this.lore = Settings.ITEM_LORE_TAG_LORE.getStringList();
				break;
			case ITEM_DELORE_TAG:
				this.material = CompMaterial.matchCompMaterial(Settings.ITEM_DELORE_TAG_MATERIAL.getString()).get();
				this.name = Settings.ITEM_DELORE_TAG_NAME.getString();
				this.lore = Settings.ITEM_DELORE_TAG_LORE.getStringList();
				break;
		}
	}

	public ItemStack getTag() {
		return QuickItem.of(this.material).name(this.name).lore(this.lore).tag("ItemTagType", type.name()).make();
	}
}
