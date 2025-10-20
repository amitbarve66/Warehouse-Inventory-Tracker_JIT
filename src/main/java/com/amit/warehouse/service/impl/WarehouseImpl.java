package com.amit.warehouse.service.impl;

import com.amit.warehouse.model.Product;
import com.amit.warehouse.service.StockObserver;
import com.amit.warehouse.service.Warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WarehouseImpl implements Warehouse {
    private final Map<String, Product> inventory = new ConcurrentHashMap<>();
    private final List<StockObserver> observers = Collections.synchronizedList(new ArrayList<>());
    private final String inventoryFile = "src/main/resources/inventory.txt";

    public WarehouseImpl() {
    //load inventory method need to implement
    }

    @Override
    public void addProduct(Product p) {
        if (p == null || inventory.containsKey(p.getId())) {
            System.out.println("Invalid or duplicate product.");
            return;
        }
        inventory.put(p.getId(), p);
        save();
    }

    @Override
    public void registerObserver(StockObserver obs) {

    }

    @Override
    public void unregisterObserver(StockObserver obs) {

    }

    @Override
    public void receiveShipment(String productId, int qty) {

    }

    @Override
    public void fulfillOrder(String productId, int qty) {

    }

    @Override
    public void printInventory() {

    }

    private void save() {
        //save inventory need to implement
    }
}
