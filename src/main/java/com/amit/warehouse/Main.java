package com.amit.warehouse;

import com.amit.warehouse.model.Product;
import com.amit.warehouse.service.Warehouse;
import com.amit.warehouse.service.impl.ConsoleAlertService;
import com.amit.warehouse.service.impl.WarehouseImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new WarehouseImpl();
        warehouse.registerObserver(new ConsoleAlertService());

        // Add sample products
        warehouse.addProduct(new Product("P001", "Laptop", 10, 5));
        warehouse.addProduct(new Product("P002", "Mouse", 20, 10));
        warehouse.addProduct(new Product("P003", "Keyboard", 15, 7));

        // Simulate operations
        warehouse.receiveShipment("P001", 5);
        warehouse.fulfillOrder("P001", 12); // triggers alert
        warehouse.fulfillOrder("P002", 5);
        warehouse.receiveShipment("P003", 3);

        // Print final inventory
        warehouse.printInventory();
    }
}