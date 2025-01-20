package org.inventory.app.util;

import org.inventory.app.entity.Order;
import org.inventory.app.entity.OrderStatus;
import org.inventory.app.entity.Product;
import org.inventory.app.entity.Stock;
import org.inventory.app.service.OrderService;
import org.inventory.app.service.ProductService;
import org.inventory.app.service.StockService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputDriver {
    private OrderService orderService;
    private ProductService productService;
    private StockService stockService;

    public InputDriver(){
        stockService = new StockService();
        orderService = new OrderService(stockService);
        productService = new ProductService(stockService);
    }

    // Product Management
    public void addProduct(String productName, Integer quantity, Double price){
        String productId = productService.addProduct(productName, quantity, price);
        System.out.println("Created Product: " + productId);
    }

    public void deleteProduct(String productId){
        boolean isDeleted = productService.deleteProduct(productId);
        if (isDeleted) System.out.println(productId + " is deleted");
    }

    public void updateProduct(String productId, Integer quantity , Double price){
        boolean isUpdate = productService.updateProduct(productId, quantity, price);
        if (isUpdate) System.out.println(productId + " is updated");
    }

    public void viewProduct(String productId){
        Product product = productService.viewProduct(productId);
        System.out.println(product);
    }

    public void listProducts(){
        List<Product> products = productService.listProducts();
        if (products.isEmpty()) System.out.println("No Products Present");
        products.forEach(System.out::println);
    }

    // Stock Management
    public void addStock(String productId, Integer quantity){
        Optional<Product> product = productService.findById(productId);
        if(product.isEmpty()) {
            System.out.println(productId + " product has no stock yet");
            return;
        }
        boolean isAdded = stockService.addStock(product.get(), quantity);
        if (isAdded) System.out.println("Stock is updated");
        else System.out.println("Stock isn't updated");
    }

    public void removeStock(String productId, Integer quantity){
        Optional<Product> product = productService.findById(productId);
        if (product.isEmpty()) {
            System.out.println(productId + " product has no stock yet");
            return;
        }
        boolean isRemoved = stockService.removeStock(product.get(), quantity);
        if (isRemoved) System.out.println(productId + ", " + quantity + " is removed from stock");
    }

    public void checkStock(String productId){
        Optional<Stock> optionalStock = stockService.checkStock(productId);
        if(optionalStock.isEmpty()) {
            System.out.println(productId + " product has no stock yet");
            return;
        }
        System.out.println(optionalStock.get());
    }

    // Order Management
    public void createOrder(String[] productIds, Integer[] quantities){
        Map<Product, Integer> productQuantityMap = IntStream.range(0, productIds.length)
                .boxed()
                .collect(Collectors.toMap(
                        id -> productService.findById(productIds[id]).get(),
                        id -> quantities[id]
                ));
        int orderId = orderService.createOrder(productQuantityMap);
        System.out.println("Created order: " + orderId);
    }

    public void cancelOrder(Integer orderId){
        boolean isCancel = orderService.cancelOrder(orderId);
        if (isCancel) System.out.println(orderId +" order is cancelled");
    }

    public void listOrders(String orderStatus){
        OrderStatus status = OrderStatus.getOrderStatusByName(orderStatus);
        List<Order> orders = orderService.listOrders(status);
        if (orders.isEmpty()) System.out.println("No Orders Yet");
        orders.forEach(System.out::println);
    }

    public void viewOrder(Integer orderId){
        Order order = orderService.viewOrder(orderId);
        System.out.println(order);
    }

    // Report Management
    public void inventoryReport(){
        List<Stock> stocks = stockService.inventoryReport();
        if (stocks.isEmpty()) System.out.println("No Stocks yet");
        stocks.forEach(System.out::println);
    }

    public void salesReport(String productId){
        Optional<Product> product = productService.findById(productId);
        if(product.isEmpty()) return;
        orderService.salesReport(product.get()).forEach(System.out::println);
    }

    public void salesReport(LocalDate tillDate){
        orderService.salesReport(tillDate).forEach(System.out::println);
    }

    public void lowStockReport(Integer threshold){
        List<Stock> stocks = stockService.lowStockReport(threshold);
        if (stocks.isEmpty()) System.out.println("No low Stocks");
        stocks.forEach(System.out::println);
    }
}
