package io.order.app;

import java.util.Scanner;

public class FoodOrderApplication {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        UserInput app = new UserInput();
        boolean running = true;
        String input, userName, password, restaurantName, key, orderId, status;
        String[] command, items, parts;
        Integer[] quantities;
        Double[] prices;
        String invalidInput = "Invalid Input!!";
        while(running){
            input = in.nextLine();
            command = input.split(" ");
            try {
                switch (command[0]) {
                    case "exit":
                        running = false;
                        break;

                    // User Management
                    case "register":
                        userName = command[1];
                        password = command[2];
                        app.register(userName, password);
                        break;
                    case "login":
                        userName = command[1];
                        password = command[2];
                        app.login(userName, password);
                        break;
                    case "logout":
                        app.logout();
                        break;

                    // Restaurant Management
                    case "add_restaurant":
                        restaurantName = command[1];
                        parts = command[2].split(",");
                        items = new String[parts.length];
                        prices = new Double[parts.length];
                        for(int i=0;i<parts.length; i++){
                            String[] menus = parts[i].split(":");
                            items[i] = menus[0];
                            prices[i] = Double.parseDouble(menus[1]);
                        }
                        app.addRestaurant(restaurantName, items, prices);
                        break;
                    case "view_restaurants":
                        app.viewRestaurants();
                        break;
                    case "search_restaurant":
                        key = command[1];
                        app.searchRestaurant(key);
                        break;
                    case "view_menu":
                        restaurantName = command[1];
                        app.viewMenu(restaurantName);
                        break;

                    // Order Management
                    case "add_to_cart":
                        restaurantName = command[1];
                        parts = command[2].split(",");
                        items = new String[parts.length];
                        quantities = new Integer[parts.length];
                        for(int i=0;i<parts.length; i++){
                            String[] menus = parts[i].split(":");
                            items[i] = menus[0];
                            quantities[i] = Integer.parseInt(menus[1]);
                        }
                        app.addToCart(restaurantName, items, quantities);
                        break;
                    case "view_cart":
                        app.viewCart();
                        break;
                    case "remove_from_cart":
                        app.removeFromCart(command[1]);
                        break;
                    case "clear_cart":
                        app.clearCart();
                        break;
                    case "place_order":
                        app.placeOrder();
                        break;
                    case "view_orders":
                        app.viewOrders();
                        break;
                    case "view_order_status":
                        app.viewOrderStatus(command[1]);
                        break;
                    case "view_all_orders":
                        app.viewAllOrders();
                        break;
                    case "update_order_status":
                        orderId = command[1];
                        status = command[2];
                        app.updateOrderStatus(orderId, status);
                        break;
                    default:
                        System.out.println(invalidInput);
                        break;
                }
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println(invalidInput);
            }
        }
    }
}
