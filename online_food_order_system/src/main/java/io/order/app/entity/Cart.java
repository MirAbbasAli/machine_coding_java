package io.order.app.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private String restaurantName;
    private List<ItemQuantity> selectedItem;
    private Double totalPrice;

    public Cart(String restaurantName, List<ItemQuantity> selectedItem) {
        this.restaurantName = restaurantName;
        this.selectedItem = selectedItem;
        this.totalPrice = 0.0;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
        return "Cart{" +
                "restaurantName='" + restaurantName + '\'' +
                ", selectedItem=" + selectedItem.stream().map(ItemQuantity::toString).collect(Collectors.joining(", ")) +
                ", totalPrice = " + String.format("%.2f", totalPrice) +
                '}';
    }
}