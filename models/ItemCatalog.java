package models;

import java.util.HashSet;
import java.util.Set;

public class ItemCatalog {

    private final Set<String> allowedItems=new HashSet<>();

    public ItemCatalog() {
        allowedItems.add("FOOD");
        allowedItems.add("MEDICINE");
    }

    public boolean isValidItem(String itemId){
        return allowedItems.contains(itemId);
    }

    public Set<String> getAllowedItems() {
        return allowedItems;
    }
}
