package ca.tweetzy.itemtags.factory;

import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.impl.ItemDeLoreTag;
import ca.tweetzy.itemtags.impl.ItemLoreTag;
import ca.tweetzy.itemtags.impl.ItemNameTag;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class TagFactory {

	public ItemStack request(@NonNull final TagType tagType) {
		switch (tagType) {
			case ITEM_LORE_TAG:
				return new ItemLoreTag().getTagItem();
			case ITEM_DELORE_TAG:
				return new ItemDeLoreTag().getTagItem();
			default:
				return new ItemNameTag().getTagItem();
		}
	}
}
