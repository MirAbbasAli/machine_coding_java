package org.inventory.app.entity;

public class Stock {
    private final Integer id;
    private Product product;
    private Integer quantity;

    public Stock(Integer stockId, Product product, Integer quantity) {
        this.id = stockId;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "productId=" + product.getId() +
                ", productName=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
