package org.inventory.app.entity;

import org.inventory.app.exception.OrderStatusException;

import java.util.Arrays;

public enum OrderStatus {
    ORDERED, CANCELLED;

    public static OrderStatus getOrderStatusByName(String name){
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new OrderStatusException(name + " invalid order status"));

    }
}
