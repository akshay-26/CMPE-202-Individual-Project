package utilities;

import com.project.category.CategoryLimit;
import com.project.item.Item;
import com.project.item.ItemList;
import com.project.mapping.ItemCategory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

public class LoadItems  {

    public static ItemList loadItems(String inventoryFilename, ItemCategory itemCategory) {
        System.out.println("Reading Order Input File:" + inventoryFilename);

        ItemList itemList = new ItemList();
        try (BufferedReader br = new BufferedReader(new FileReader(inventoryFilename))) {
            String line = br.readLine(); //header
            while ((line = br.readLine()) != null) {

                String[] itemSplit = line.split(",");
                String categoryName = itemSplit[0].trim().toLowerCase();
                if (itemSplit.length == 4 ) {
                    String itemName = itemSplit[1].trim().toLowerCase();
                    int quantity = Integer.parseInt(itemSplit[2].trim());
                    double price = Double.parseDouble(itemSplit[3].trim());

                    Item item = new Item(categoryName, itemName, quantity, price);
                    itemList.add(item);
                } else {
                    System.out.println("Unable to process line:" + line);
                }


            }

        } catch (FileNotFoundException e) {
            System.err.println("File Not Found:" + inventoryFilename);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception while reading file: " + inventoryFilename);
            e.printStackTrace();
        }
        return itemList;
    }

}



