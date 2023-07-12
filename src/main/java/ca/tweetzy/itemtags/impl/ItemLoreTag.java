package ca.tweetzy.itemtags.impl;

import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.itemtags.api.ItemTag;
import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.inventory.ItemStack;

public final class ItemLoreTag extends ItemTag {

	public ItemLoreTag() {
		super(TagType.ITEM_LORE_TAG);
	}

	@Override
	public ItemStack getTagItem() {
		return QuickItem
				.of(Settings.ITEM_LORE_TAG_MATERIAL.getString())
				.name(Settings.ITEM_LORE_TAG_NAME.getString())
				.lore(Settings.ITEM_LORE_TAG_LORE.getStringList())
				.tag("ItemTagType", getTagType().name())
				.make();
	}
}
