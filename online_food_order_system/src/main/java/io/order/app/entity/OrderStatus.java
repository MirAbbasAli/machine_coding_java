package io.order.app.entity;

import java.util.Arrays;

public enum OrderStatus{
    ORDER, CANCEL, PAID, DELIVERED;

    public static OrderStatus getOrderByName(String name){
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(name + " no such order status exists"));
    }
}
