package com.project.order;

import com.project.category.CategoryLimit;
import com.project.item.ItemList;

import java.util.HashMap;
import java.util.Map;

public class OrderList {
    //Map<orderid, <category, remaining>>
    Map<Integer, Map<String, Integer>> ordercategorylimit;

    public OrderList() {
        ordercategorylimit = new HashMap<>();
    }

    public boolean addOrder(int orderid, String item, String category, int quantity, ItemList itemlist) {

        if(!ordercategorylimit.containsKey(orderid)) {
            ordercategorylimit.put(orderid, CategoryLimit.getCategoryLimitDeepCopy());
        }

        Map<String, Integer> remainingCategoryLimit = ordercategorylimit.get(orderid);
        int currentcapacity = remainingCategoryLimit.get(category);
        int currentinventory = 0;

        if(itemlist.getItemlist().containsKey(item))
            currentinventory = itemlist.getItemlist().get(item).getQuantity();

        if(currentcapacity>=quantity && currentinventory>=quantity) {

            // 1. Reducing it from the current capacity
            int newcapacity = currentcapacity - quantity;
            remainingCategoryLimit.put(category, newcapacity);

            //2 Reducing it from the inventory
            int newinventory = currentinventory-quantity;
            itemlist.getItemlist().get(item).setQuantity(newinventory);

            return true;
        } else
            return false;
    }

    public Map<Integer, Map<String, Integer>> getOrdercategorylimit() {
        return ordercategorylimit;
    }
}
