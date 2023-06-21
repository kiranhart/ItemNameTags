package ca.tweetzy.itemtags.api;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/26/2020
 * Time Created: 2:48 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public enum TagType {

    ITEM_NAME_TAG("itemnametag"),
    ITEM_LORE_TAG("itemloretag"),
    ITEM_DELORE_TAG("itemdeloretag");

    private final String type;

    TagType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}