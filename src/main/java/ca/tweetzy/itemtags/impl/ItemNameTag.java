package ca.tweetzy.itemtags.impl;

import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.itemtags.api.ItemTag;
import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.settings.Settings;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public final class ItemNameTag extends ItemTag {

	public ItemNameTag() {
		super(TagType.ITEM_NAME_TAG);
	}

	@Override
	public void process(@NonNull ItemStack itemToProcess) {

	}

	@Override
	public ItemStack getTagItem() {
		return QuickItem
				.of(Settings.ITEM_NAME_TAG_MATERIAL.getString())
				.name(Settings.ITEM_NAME_TAG_NAME.getString())
				.lore(Settings.ITEM_NAME_TAG_LORE.getStringList())
				.tag("ItemTagType", getTagType().name())
				.make();
	}
}
