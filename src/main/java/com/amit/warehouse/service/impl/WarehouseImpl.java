package com.amit.warehouse.service.impl;

import com.amit.warehouse.model.Product;
import com.amit.warehouse.persistence.InventoryPersistence;
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
        InventoryPersistence.loadInventory(inventoryFile, inventory);
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
        if (obs != null) observers.add(obs);

    }

    @Override
    public void unregisterObserver(StockObserver obs) {
        observers.remove(obs);

    }

    @Override
    public void receiveShipment(String productId, int qty) {
        Product p = inventory.get(productId);
        if (p == null || qty <= 0) {
            System.out.println("Invalid input.");
            return;
        }
        p.increaseQuantity(qty);
        System.out.println("Shipment received: +" + qty + " → " + p);
        save();
    }

    @Override
    public void fulfillOrder(String productId, int qty) {
        Product p = inventory.get(productId);
        if (p == null || qty <= 0) {
            System.out.println("Invalid input.");
            return;
        }
        boolean success = p.decreaseQuantity(qty);
        if (!success) {
            System.out.println("Insufficient stock for " + p.getName() + ". Available: " + p.getQuantity());
            return;
        }
        System.out.println("Order fulfilled: -" + qty + " → " + p);
        if (p.getQuantity() < p.getReorderThreshold()) {
            notifyObservers(p);
        }
        save();
    }

    @Override
    public void printInventory() {
        System.out.println("Current Inventory:");
        inventory.values().forEach(System.out::println);

    }

    private void notifyObservers(Product product) {
        synchronized (observers) {
            for (StockObserver o : observers) {
                try {
                    o.onLowStock(product);
                } catch (Exception e) {
                    System.out.println("Observer error: " + e.getMessage());
                }
            }
        }
    }

    private void save() {
        InventoryPersistence.saveInventory(inventoryFile, inventory);
    }
}
