package ca.tweetzy.itemtags.itemtag;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.TItemBuilder;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.itemtags.settings.Settings;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 3:07 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class ItemTagBuilder {

    private TagType type;
    private XMaterial material;
    private String name;
    private List<String> lore;

    public ItemTagBuilder(TagType type) {
        this.type = type;
        switch (this.type) {
            case ITEM_NAME_TAG:
                this.material = XMaterial.matchXMaterial(Settings.ITEM_NAME_TAG_MATERIAL.getString()).get();
                this.name = Settings.ITEM_NAME_TAG_NAME.getString();
                this.lore = Settings.ITEM_NAME_TAG_LORE.getStringList();
                break;
            case ITEM_LORE_TAG:
                this.material = XMaterial.matchXMaterial(Settings.ITEM_LORE_TAG_MATERIAL.getString()).get();
                this.name = Settings.ITEM_LORE_TAG_NAME.getString();
                this.lore = Settings.ITEM_LORE_TAG_LORE.getStringList();
                break;
            case ITEM_DELORE_TAG:
                this.material = XMaterial.matchXMaterial(Settings.ITEM_DELORE_TAG_MATERIAL.getString()).get();
                this.name = Settings.ITEM_DELORE_TAG_NAME.getString();
                this.lore = Settings.ITEM_DELORE_TAG_LORE.getStringList();
                break;
        }
    }

    public ItemStack getTag() {
        ItemStack stack = new TItemBuilder(this.material.parseItem()).setName(this.name).setLore(this.lore.stream().map(TextUtils::formatText).collect(Collectors.toList())).toItemStack();
        stack = NBTEditor.set(stack, type.name(), "ItemTagType");
        return stack;
    }
}
