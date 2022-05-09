package com.project.mapping;

import java.util.HashMap;
import java.util.Map;

public class ItemCategory {
    Map<String, String> itemCategory;

    public ItemCategory() {
        itemCategory = new HashMap<>();
    }

    public void add(String item, String category) {

        itemCategory.put(item.toLowerCase(), category.toLowerCase());
    }

    public boolean hasItem(String itemName) {

        return itemCategory.containsKey(itemName);
    }

    public String getCategory(String itemName) {

        return itemCategory.get(itemName);
    }
}

