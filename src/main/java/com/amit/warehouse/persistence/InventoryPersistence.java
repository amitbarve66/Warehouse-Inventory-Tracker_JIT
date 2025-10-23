package com.amit.warehouse.persistence;

import com.amit.warehouse.model.Product;

import java.io.*;
import java.util.Map;

public class InventoryPersistence {
    public static void loadInventory(String filename, Map<String, Product> inventory){
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product p = Product.fromCSV(line);
                inventory.put(p.getId(), p);
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
    public static void saveInventory(String filename, Map<String, Product> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Product p : inventory.values()) {
                writer.write(p.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
}
