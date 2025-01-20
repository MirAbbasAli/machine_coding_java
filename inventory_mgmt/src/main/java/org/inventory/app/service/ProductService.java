package org.inventory.app.service;

import org.inventory.app.entity.Product;
import org.inventory.app.entity.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final StockService stockService;
    private final List<Product> products;

    public ProductService(StockService stockService){
        this.stockService = stockService;
        this.products = new ArrayList<>();
    }

    
    public String addProduct(String name, Integer quantity, Double price) {
        String productId = String.format("PRO-%d", products.size()+1);
        Product product = new Product(productId, name, price);
        products.add(product);

        Optional<Stock> optionalStock = stockService.checkStock(productId);
        optionalStock.ifPresentOrElse(stock -> {
            stockService.updateStock(stock, quantity);
        }, () -> {
            stockService.addStockForNewProduct(product, quantity);
        });

        return productId;
    }

    
    public Boolean updateProduct(String productId, Integer quantity, Double price) {
        Optional<Product> optionalProduct = findById(productId);
        if (optionalProduct.isEmpty()) return Boolean.FALSE;
        optionalProduct.ifPresentOrElse(
                product -> {
                    product.setPrice(price);
                    var stock = stockService.findByProduct(product);
                    stock.ifPresent(s -> {
                        s.setQuantity(0);
                        s.setQuantity(quantity);
                    });
                },
                () -> stockService.checkStock(productId).ifPresent(stock -> stockService.updateStock(stock, quantity))
        );
        return Boolean.TRUE;
    }

    public Boolean deleteProduct(String productId) {
        Optional<Product> optionalProduct = findById(productId);
        if (optionalProduct.isEmpty()) return Boolean.FALSE;
        optionalProduct.ifPresent(products::remove);
        return Boolean.TRUE;
    }

    
    public Product viewProduct(String productId) {
        return findById(productId).orElse(null);
    }

    
    public List<Product> listProducts() {
        return products;
    }

    
    public Optional<Product> findById(String productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }
}
