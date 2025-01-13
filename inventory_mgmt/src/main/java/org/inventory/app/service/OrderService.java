package org.inventory.app.service;

import org.inventory.app.entity.Order;
import org.inventory.app.entity.OrderStatus;
import org.inventory.app.entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderService {
    private final StockService stockService;
    private final List<Order> orders;

    public OrderService(StockService stockService){
        this.stockService = stockService;
        this.orders = new ArrayList<>();
    }

    public Integer createOrder(Map<Product, Integer> productQuantityMap) {
        int orderId = orders.size() + 1;
        Order order = new Order(orderId, productQuantityMap, LocalDate.now());
        orders.add(order);

        productQuantityMap.keySet()
                .forEach(product -> {
                   stockService.removeStock(product, productQuantityMap.get(product));
                });
        return orderId;
    }

    public Boolean cancelOrder(Integer orderId) {
        Order order = viewOrder(orderId);
        if(order == null) return Boolean.FALSE;
        order.getProducts().keySet()
                .forEach(product -> {
                    stockService.addStock(product, order.getProducts().get(product));
                });
        return Boolean.TRUE;
    }

    public List<Order> listOrders(OrderStatus orderStatus) {
        return orders.stream()
                .filter(order -> order.getOrderStatus().equals(orderStatus))
                .toList();
    }

    public Order viewOrder(Integer orderId) {
        return orders.stream()
                .filter(order -> Objects.equals(order.getId(), orderId))
                .findFirst()
                .orElse(null);
    }

    public List<Order> salesReport(Product product){
        return orders.stream()
                .filter(order -> order.getProducts().containsKey(product))
                .toList();
    }

    public List<Order> salesReport(LocalDate tillDate){
        return orders.stream()
                .filter(order -> order.getOrderDate().isBefore(tillDate))
                .toList();
    }
}
