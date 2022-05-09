package com.project.item;

import java.util.HashMap;
import java.util.Map;

public class ItemList {
    Map<String, Item> itemlist;

    public ItemList() {
       itemlist = new HashMap<>();
    }

    //overloading add method
    public void add(Item item) {
        itemlist.put(item.getItemName().toLowerCase(), item);
    }

    public Map<String, Item> getItemlist() {
        return itemlist;
    }

    public double getCharge(String item) {
        return itemlist.get(item).getPrice();
    }
}
