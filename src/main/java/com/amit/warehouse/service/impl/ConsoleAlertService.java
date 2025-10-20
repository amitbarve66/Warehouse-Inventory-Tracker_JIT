package com.amit.warehouse.service.impl;

import com.amit.warehouse.model.Product;
import com.amit.warehouse.service.StockObserver;

public class ConsoleAlertService implements StockObserver {

    @Override
    public void onLowStock(Product product) {
        System.out.println("ALERT: Low stock for " + product.getName() +" — only " + product.getQuantity() + " left!");
    }
}
