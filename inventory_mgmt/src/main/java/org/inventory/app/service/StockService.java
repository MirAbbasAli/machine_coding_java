package org.inventory.app.service;

import org.inventory.app.entity.Product;
import org.inventory.app.entity.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockService {
    private final List<Stock> stocks;

    public StockService(){
        this.stocks = new ArrayList<>();
    }
    
    public Integer addStock(Product product, Integer quantity) {
        int stockId = stocks.size() + 1;
        Stock stock = new Stock(stockId, product, quantity);
        stocks.add(stock);
        return stockId;
    }

    public void updateStock(Stock stock, Integer quantity){
        stock.setQuantity(quantity);

    }
    
    public Boolean removeStock(Product product, Integer quantity) {
        Optional<Stock> optionalStock = checkStock(product.getId());
        if(optionalStock.isEmpty()) return Boolean.FALSE;

        Stock stock = optionalStock.get();
        int expectedStockQuantity = stock.getQuantity() - quantity;

        if (expectedStockQuantity < 0) return Boolean.FALSE;

        stock.setQuantity(expectedStockQuantity);
        return Boolean.TRUE;
    }

    public Optional<Stock> checkStock(String productId) {
        return stocks.stream()
                .filter(stock -> stock.getProduct().getId().equals(productId))
                .findFirst();
    }

    public List<Stock> lowStockReport(Integer threshold){
        return stocks.stream()
                .filter(stock -> stock.getQuantity() <= threshold)
                .toList();
    }

    public List<Stock> inventoryReport(){
        return stocks;
    }

}
