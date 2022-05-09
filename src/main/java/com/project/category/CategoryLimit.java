package com.project.category;

import com.project.mapping.ItemCategory;

import java.util.HashMap;
import java.util.Map;

public class CategoryLimit {

    private static Map<String, Integer> categoryLimit;
    private static ItemCategory itemCategory;

    public CategoryLimit() {
        categoryLimit = new HashMap<>();
        itemCategory = new ItemCategory();
    }

    public void add(Category category) {

        categoryLimit.put(category.getCategoryName().toLowerCase(), category.getLimit());

        //Iterator design pattern
        for(String item : category.getItems()) {
            itemCategory.add(item, category.getCategoryName());
        }
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public static Map<String, Integer> getCategoryLimitDeepCopy() {
        Map<String, Integer> newmap = new HashMap<>();

        for(Map.Entry<String,Integer> entry : categoryLimit.entrySet()) {
            newmap.put(entry.getKey(),entry.getValue());
        }

        return newmap;
    }
}
