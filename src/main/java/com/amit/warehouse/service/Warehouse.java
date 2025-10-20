package com.amit.warehouse.service;

import com.amit.warehouse.model.Product;

public interface Warehouse {
    void addProduct(Product p);
    void registerObserver(StockObserver obs);
    void unregisterObserver(StockObserver obs);
    void receiveShipment(String productId, int qty);
    void fulfillOrder(String productId, int qty);
    void printInventory();
}
