package io.order.app.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private final String orderId;
    private OrderStatus orderStatus;
    private final User user;
    private String restaurantName;
    private List<ItemQuantity> selectedItem;
    private Double totalPrice;

    private static Integer orderNumber=0;


    public Order(List<ItemQuantity> selectedItem, String restaurantName, User user, OrderStatus orderStatus, Double totalPrice) {
        this.selectedItem = selectedItem;
        this.restaurantName = restaurantName;
        this.user = user;
        this.orderStatus = orderStatus;
        this.orderId = String.format("%s-%s-%d", user.getName(), restaurantName, orderNumber++);
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<ItemQuantity> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<ItemQuantity> selectedItem) {
        this.selectedItem = selectedItem;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", user=" + user.getName() +
                ", restaurantName='" + restaurantName + '\'' +
                ", selectedItem=" + selectedItem.stream().map(ItemQuantity::toString).collect(Collectors.joining(", ")) +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
