package io.order.app.service;


import io.order.app.entity.Item;
import io.order.app.entity.Restaurant;
import io.order.app.entity.RestaurantItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RestaurantService {
    private final List<Restaurant> restaurants;
    private final List<RestaurantItem> restaurantItems;

    public RestaurantService() {
        restaurants = new ArrayList<>();
        restaurantItems = new ArrayList<>();
    }

    public Restaurant addRestaurant(String name, String[] itemNames, Double[] itemPrices){
        List<Item> menu = new ArrayList<>();
        IntStream.range(0, itemNames.length)
                .forEach(index -> {
                    Item item = new Item(itemNames[index], itemPrices[index]);
                    menu.add(item);
                    restaurantItems.add(new RestaurantItem(name, item));
                });
        Restaurant restaurant = new Restaurant(name, menu);
        restaurants.add(restaurant);
        return restaurant;
    }

    public List<Restaurant> viewRestaurants(){
        return new ArrayList<>(restaurants);
    }

    public List<RestaurantItem> searchRestaurant(String key){
        return restaurantItems.stream()
                .filter(restaurantItem -> restaurantItem.getRestaurantName().equals(key) || restaurantItem.getMenuItem().getName().equals(key))
                .toList();
    }

    public List<Item> viewMenu(String restaurantName){
        return restaurants.stream()
                .filter(restaurant -> restaurant.getName().equals(restaurantName))
                .map(Restaurant::getMenu)
                .findFirst()
                .orElse(null);
    }
}
