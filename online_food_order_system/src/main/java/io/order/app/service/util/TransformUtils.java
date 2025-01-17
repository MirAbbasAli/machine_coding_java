package io.order.app.service.util;

import io.order.app.entity.ItemQuantity;

import java.util.List;

public class TransformUtils {
    public static Double calculatePrice(List<ItemQuantity> items){
        return items.stream().mapToDouble(itemQuantity -> itemQuantity.getItem().getPrice() * itemQuantity.getQuantity()).sum();
    }
}
