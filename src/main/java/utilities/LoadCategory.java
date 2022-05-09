package utilities;

import com.google.gson.Gson;
import com.project.category.Category;
import com.project.category.CategoryLimit;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.project.card.CardList;
import com.project.category.Category;
import com.project.category.CategoryLimit;
import com.project.item.Item;
import com.project.item.ItemList;
import com.project.mapping.ItemCategory;
import com.project.order.OrderList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Map;

public class LoadCategory  {

    private static CategoryLimit categoryLimit = null;

    public static CategoryLimit loadCategoryItems(String configFilename) {
        System.out.println("Reading File:" + configFilename);
        if (categoryLimit != null)
            return categoryLimit;

        categoryLimit = new CategoryLimit();
        Gson gson = new Gson();
        try {
            Category[] config = gson.fromJson(new FileReader(configFilename), Category[].class);
            for (Category c : config) {
                categoryLimit.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return categoryLimit;
    }

}
