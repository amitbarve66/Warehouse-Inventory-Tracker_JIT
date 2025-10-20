package com.amit.warehouse.service;

import com.amit.warehouse.model.Product;

public interface StockObserver {
    void onLowStock(Product product);
}
