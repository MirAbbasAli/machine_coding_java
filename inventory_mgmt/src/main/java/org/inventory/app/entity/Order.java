package org.inventory.app.entity;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Order {
    private final Integer id;
    private Map<Product, Integer> products;
    private Double totalPrice;
    private LocalDate orderDate;
    private OrderStatus orderStatus;

    public Order(Integer orderId, Map<Product, Integer>  products, LocalDate orderDate) {
        this.id = orderId;
        this.products = products;
        this.orderDate = orderDate;
        this.orderStatus = OrderStatus.ORDERED;
        this.totalPrice = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getId()!=null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        String productInfos = products.keySet()
                .stream()
                .map(product -> "{" + "id=" + product.getId() + ", name=" + product.getName() + ", quantity=" + products.get(product) + '}')
                .collect(Collectors.joining(",\n "));
        return "Order{" +
                "id=" + id +
                ",\n orderDate=" + orderDate +
                ",\n orderStatus=" + orderStatus +
                ",\n totalPrice=" + totalPrice +
                ",\n product=" + productInfos +
                '}';
    }
}
