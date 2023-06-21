package ca.tweetzy.itemtags.api;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public abstract class ItemTag implements Tag {

	private final TagType tagType;

	@Override
	public TagType getTagType() {
		return this.tagType;
	}

	public abstract ItemStack getTagItem();
}
