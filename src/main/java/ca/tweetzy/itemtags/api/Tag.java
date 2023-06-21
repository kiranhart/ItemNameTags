package ca.tweetzy.itemtags.api;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public interface Tag {

	TagType getTagType();

	void process(@NonNull final ItemStack itemToProcess);

}
