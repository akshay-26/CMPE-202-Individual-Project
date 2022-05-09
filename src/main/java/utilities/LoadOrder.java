package utilities;

import com.project.card.CardList;
import com.project.item.ItemList;
import com.project.mapping.ItemCategory;
import com.project.order.OrderList;

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

import java.io.*;

public class LoadOrder {

    public static CardList loadOrder(String orderFilename, ItemList itemList, ItemCategory itemCategory, String outputFailedFileName) {

        OrderList orderList = new OrderList();
        CardList cardlist = new CardList();

        boolean isFailed = false;
        try (BufferedReader br = new BufferedReader(new FileReader(orderFilename))) {
            String line = br.readLine(); //header
            while ((line = br.readLine()) != null) {
                String[] itemSplit = line.split(",");

                int orderid = 1;
                if (itemSplit.length == 3 && itemCategory.hasItem(itemSplit[0].trim().toLowerCase())) {
                    String itemName = itemSplit[0].trim().toLowerCase();
                    String category = itemCategory.getCategory(itemName);
                    int quantity = Integer.parseInt(itemSplit[1].trim());
                    String card = itemSplit[2].trim();

                    boolean successOrder = orderList.addOrder(orderid, itemName, category, quantity, itemList);
                    if (successOrder) {
                        cardlist.addCharge(card, quantity * itemList.getCharge(itemName));
                    } else {
                        isFailed = true;
                        // output to not successful filepath
                        try {
                            BufferedWriter bw= new BufferedWriter(new FileWriter( outputFailedFileName, true));
                            PrintWriter out1 = new PrintWriter(bw);
                            String message = "(Failed item) Unable to process: " + quantity + " quantity for " + itemName + "(Category: " +category + ")";
                            System.out.println(message);
                            out1.println(message);
                            out1.flush();
                        } catch (IOException e) {
                            System.out.println("EXCEPTION caught: for deleteIfExists:"+outputFailedFileName);
                        }
                    }

                } else {
                    System.out.println("Unable to process line:" + line);
                }
            }

            if(!isFailed) {
                System.out.println("Congratulations: No failed orders. All order have been processed succesfully! ");
            }
        } catch (IOException e) {
            System.out.println("EXCEPTION caught: for deleteIfExists:"+orderFilename);
        }


        return cardlist;
    }
}
