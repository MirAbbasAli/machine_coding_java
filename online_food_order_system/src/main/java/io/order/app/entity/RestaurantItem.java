package io.order.app.entity;

public class RestaurantItem {
    private String restaurantName;
    private Item menuItem;

    public RestaurantItem(String restaurantName, Item menuItem) {
        this.restaurantName = restaurantName;
        this.menuItem = menuItem;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Item getMenuItem() {
        return menuItem;
    }

    @Override
    public String toString() {
        return "RestaurantItem{" +
                "restaurantName='" + restaurantName + '\'' +
                ", menuItem=" + menuItem +
                '}';
    }
}
