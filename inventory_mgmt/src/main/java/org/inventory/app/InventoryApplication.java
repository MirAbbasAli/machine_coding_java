package org.inventory.app;

import org.inventory.app.util.InputDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InventoryApplication {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args){
        InputDriver driver = new InputDriver();
        boolean running = true;
        String userInput;
        String[] commands;
        String[] products;
        Integer[] quantities;
        String invalidMessage = "Invalid Input!!";
        while(running){
            try {
                userInput = in.nextLine();
                commands = userInput.split(" ");

                switch (commands[0]) {
                    case "exit":
                        running = false;
                        break;
                    case "add_product":
                        driver.addProduct(commands[1], Integer.parseInt(commands[2]), Double.parseDouble(commands[3]));
                        break;
                    case "delete_product":
                        driver.deleteProduct(commands[1]);
                        break;
                    case "update_product":
                        driver.updateProduct(commands[1], Integer.parseInt(commands[2]), Double.parseDouble(commands[3]));
                        break;
                    case "view_product":
                        driver.viewProduct(commands[1]);
                        break;
                    case "list_products":
                        driver.listProducts();
                        break;
                    case "add_stock":
                        driver.addStock(commands[1], Integer.parseInt(commands[2]));
                        break;
                    case "remove_stock":
                        driver.removeStock(commands[1], Integer.parseInt(commands[2]));
                        break;
                    case "check_stock":
                        driver.checkStock(commands[1]);
                        break;
                    case "create_order":
                        int numOfProducts = Integer.parseInt(commands[1]);
                        products = new String[numOfProducts];
                        quantities = new Integer[numOfProducts];
                        for(int i=0; i<numOfProducts; i++){
                            String[] parts = commands[2].split(",");
                            products[i] = parts[0];
                            quantities[i] = Integer.parseInt(parts[1]);
                        }
                        driver.createOrder(products, quantities);
                        break;
                    case "cancel_order":
                        driver.cancelOrder(Integer.parseInt(commands[1]));
                        break;
                    case "list_orders":
                        driver.listOrders(commands[1]);
                        break;
                    case "view_order":
                        driver.viewOrder(Integer.parseInt(commands[1]));
                        break;
                    case "inventory_report":
                        driver.inventoryReport();
                        break;
                    case "sales_report":
                        try{
                            driver.salesReport(null, parseToLocalDate(commands[1]));
                        } catch(DateTimeParseException e){
                            driver.salesReport(commands[1], null);
                        }
                        break;
                    case "low_stock_report":
                        driver.lowStockReport(Integer.parseInt(commands[1]));
                        break;
                    default:
                        System.out.println(invalidMessage);
                        break;
                }
            } catch (Exception e){
                System.out.println(invalidMessage);
            }
        }
    }

    private static LocalDate parseToLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
