package io.order.app.entity;

import java.util.List;
import java.util.stream.Collectors;

public class Restaurant {
    private String name;
    private List<Item> menu;

    public Restaurant(String name, List<Item> menu) {
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", menu=" + menu.stream().map(Item::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
