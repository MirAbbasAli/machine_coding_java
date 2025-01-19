package io.order.app.service;

import io.order.app.entity.*;
import io.order.app.service.util.TransformUtils;

import java.util.*;
import java.util.stream.IntStream;

public class OrderService {
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final List<Order> orders;
    private Cart currentCart;


    public OrderService(UserService userService, RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.orders = new ArrayList<>();
    }

    public Boolean addToCart(String restaurantName, List<String> selectedItems, List<Integer> quantities){
        if (!userService.isUserLoggedIn()) return Boolean.FALSE;
        List<Item> menu = restaurantService.viewMenu(restaurantName).stream().sorted(Comparator.comparing(Item::getName)).toList();
        if (menu.isEmpty()) return Boolean.FALSE;
        List<ItemQuantity> itemQuantityList = IntStream.range(0, selectedItems.size())
                .mapToObj(i ->{
                    int index = Collections.binarySearch(menu, new Item(selectedItems.get(i), 0.0), Comparator.comparing(Item::getName));
                    if (index >= 0)
                        return new ItemQuantity(menu.get(index), quantities.get(i));
                    else return null;
                }).filter(Objects::nonNull).toList();

        currentCart = new Cart(restaurantName, itemQuantityList);
        currentCart.setTotalPrice(TransformUtils.calculatePrice(itemQuantityList));
        return Boolean.TRUE;
    }

    public Cart viewCart(){
        return currentCart;
    }

    public Boolean removeFromCart(String itemName){
        if(currentCart==null) return Boolean.FALSE;
        if (!userService.isUserLoggedIn()) return Boolean.FALSE;
        var items = currentCart.getSelectedItem().stream()
                .filter(itemQuantity -> !itemQuantity.getItem().getName().equals(itemName))
                .toList();
        currentCart.setSelectedItem(items);
        currentCart.setTotalPrice(TransformUtils.calculatePrice(currentCart.getSelectedItem()));
        return !items.isEmpty();
    }

    public Boolean clearCart(){
        if (!userService.isUserLoggedIn()) return Boolean.FALSE;
        currentCart = null;
        return Boolean.TRUE;
    }

    public Order placeOrder(){
        if (!userService.isUserLoggedIn()) return null;
        if (currentCart == null) return null;
        Order order =  new Order(currentCart.getSelectedItem(), currentCart.getRestaurantName(), userService.getCurrentlyLoggerUser(), OrderStatus.ORDER, currentCart.getTotalPrice());
        currentCart = null;
        orders.add(order);
        return order;
    }

    public List<Order> viewOrders(){
        if (!userService.isUserLoggedIn()) return null;
        return orders.stream()
                .filter(order -> order.getUser().equals(userService.getCurrentlyLoggerUser()))
                .toList();
    }

    public OrderStatus viewOrderStatus(String orderId){
        if (!userService.isUserLoggedIn()) return null;
        return findById(orderId).map(Order::getOrderStatus).orElse(null);
    }

    public Optional<Order> findById(String id){
        return orders.stream().filter(order -> order.getOrderId().equals(id))
                .findFirst();
    }

    public Boolean updateOrderStatus(String orderId, String orderStatus){
        if (!userService.isUserLoggedIn()) return Boolean.FALSE;
        User user = userService.getCurrentlyLoggerUser();
        if (!user.getName().equals("admin")) return Boolean.FALSE;
        OrderStatus status = OrderStatus.getOrderByName(orderStatus);
        findById(orderId).ifPresent(order -> order.setOrderStatus(status));
        return Boolean.TRUE;
    }

    public List<Order> viewAllOrders(){
        if (!userService.isUserLoggedIn()) return null;
        User user = userService.getCurrentlyLoggerUser();
        if (!user.getName().equals("admin")) return null;
        return new ArrayList<>(orders);
    }

}
