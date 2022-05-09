package utilities;

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

public class Commons {
    private static CategoryLimit categoryLimit = null;

    public static CategoryLimit loadCategories(String configFilename) {
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

    public static void writeTotalAmountCharged(String cardChargedfileName, CardList card) {
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter( cardChargedfileName, true));
            PrintWriter out1 = new PrintWriter(bw);
            String message = "CardNumber,AmountCharged";
            System.out.println(message);
            out1.println(message);
            for(Map.Entry<String,Double> cardamount: card.getCardlist().entrySet() ) {
                message = cardamount.getKey() + ",$" + df.format(cardamount.getValue());
                System.out.println(message);
                out1.println(message);
                out1.flush();
            }
        } catch (IOException e) {
            System.out.println("EXCEPTION caught: for deleteIfExists:"+cardChargedfileName);
        }
    }


}