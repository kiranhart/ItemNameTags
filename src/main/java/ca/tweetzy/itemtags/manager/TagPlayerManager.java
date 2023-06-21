package ca.tweetzy.itemtags.manager;

import ca.tweetzy.itemtags.api.TagType;
import ca.tweetzy.itemtags.api.manager.KeyValueManager;

import java.util.UUID;

public final class TagPlayerManager extends KeyValueManager<UUID, TagType> {

	public TagPlayerManager() {
		super("Tag Redeem Manager");
	}

	@Override
	public void load() {
		// no need to load since its stored temp
	}
}
