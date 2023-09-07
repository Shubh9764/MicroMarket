package com.project.InventoryService.services;

import com.project.InventoryService.model.Inventory;

import java.util.List;

public interface InventoryService {

     boolean isInStock(String skuCode);

     List<Inventory> areInStock(List<String> skuCode);

    List<Inventory> getAll();
}
