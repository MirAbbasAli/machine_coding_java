package io.order.app.entity;

import java.util.Objects;

public class ItemQuantity {
    private Item item;
    private Integer quantity;

    public ItemQuantity(Item item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemQuantity{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof ItemQuantity givenItem)) return false;
        return getItem()!=null && Objects.equals(item, givenItem.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }
}
