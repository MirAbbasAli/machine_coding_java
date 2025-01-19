package io.order.app;

import io.order.app.entity.*;
import io.order.app.service.OrderService;
import io.order.app.service.RestaurantService;
import io.order.app.service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserInput {
    private final OrderService orderService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    public UserInput() {
        this.userService = new UserService();
        this.restaurantService = new RestaurantService();
        this.orderService = new OrderService(userService, restaurantService);
    }

    // User Management
    public void register(String userName, String password){
       if (userService.registerUser(userName, password)) System.out.printf("%s is registered%n", userName);
       else System.out.println("Registration not possible");
    }
    public void login(String userName, String password){
        if (userService.login(userName, password)!=null) System.out.println(userName + " logged in successfully");
        else System.out.println("Cannot log in "+ userName);
    }

    public void logout(){
        if (userService.logout()) System.out.println("Logged out successfully");
        else System.out.println("Cannot log out");
    }

    // Restaurant Management
    public void addRestaurant(String restaurantName, String[] items, Double[] prices){
        Restaurant restaurant = restaurantService.addRestaurant(restaurantName, items, prices);
        if(restaurant!=null) System.out.println(restaurant);
        else System.out.println("Cannot add Restaurant "+ restaurantName);
    }

    public void viewRestaurants(){
        List<Restaurant> restaurants = restaurantService.viewRestaurants();
        if (restaurants.isEmpty()) System.out.println("No restaurants yet");
        restaurants.forEach(System.out::println);
    }

    public void searchRestaurant(String keyword){
       List<RestaurantItem> restaurantItems = restaurantService.searchRestaurant(keyword);
       if (restaurantItems.isEmpty()) System.out.println("No item/Restaurant found");
       restaurantItems.forEach(System.out::println);
    }

    public void viewMenu(String restaurantName){
        List<Item> menu = restaurantService.viewMenu(restaurantName);
        if(menu.isEmpty()) System.out.println(restaurantName +" does not have menu");
        menu.forEach(System.out::println);
    }

    // Order Management
    public void addToCart(String restaurantName, String[] items, Integer[] quantities) {
        if(orderService.addToCart(restaurantName, Arrays.asList(items), Arrays.asList(quantities))) System.out.println("Item added successfully!");
        else System.out.println("Cannot add items");
    }

    public void viewCart(){
        Cart cart = orderService.viewCart();
        if (cart!=null) System.out.println(cart);
        else System.out.println("Cart empty");
    }

    public void removeFromCart(String item) {
        if (orderService.removeFromCart(item)) System.out.println(item + " successfully removed from cart");
        else System.out.println(item + " does not exists in the cart");
    }

    public void clearCart(){
        if (orderService.clearCart()) System.out.println("Cart is cleared");
        else System.out.println("Cannot clear the cart");
    }

    public void placeOrder() {
        Order order = orderService.placeOrder();
        if (order == null) System.out.println("Order cannot be placed");
        else System.out.println(order.getOrderId()+" order placed");
    }

    public void viewOrders() {
        List<Order> orders = orderService.viewOrders();
        if(orders.isEmpty()) System.out.println("No Orders yet");
        orders.forEach(System.out::println);
    }

    public void viewOrderStatus(String orderId){
        OrderStatus status = orderService.viewOrderStatus(orderId);
        System.out.println(status.name());
    }

    public void updateOrderStatus(String orderId, String orderStatus){
        if (orderService.updateOrderStatus(orderId, orderStatus)) System.out.println(orderId +" status "+orderStatus+" is updated");
        else System.out.println("Cannot update the status");
    }

    public void viewAllOrders() {
       List<Order> orders = orderService.viewAllOrders();
       if (orders.isEmpty()) System.out.println("No Orders yet");
       orders.forEach(System.out::println);
    }

}
