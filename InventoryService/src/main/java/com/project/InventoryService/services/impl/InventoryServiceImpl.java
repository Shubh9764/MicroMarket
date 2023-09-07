package com.project.InventoryService.services.impl;

import com.project.InventoryService.model.Inventory;
import com.project.InventoryService.repo.InventoryRepo;
import com.project.InventoryService.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryRepo inventoryRepo;


    @Override
    public boolean isInStock(String skuCode) {
        Optional<Inventory> inventory = inventoryRepo.findBySkuCode(skuCode);
        if (inventory.isPresent())
            return inventory.get().getQuantity() > 0;
        return false;
    }

    @Override
    public List<Inventory> areInStock(List<String> skuCode) {
        List<Inventory> list = inventoryRepo.findBySkuCodeIn(skuCode);
        list.stream().forEach(inventory -> {
            if(inventory.getQuantity() > 0)
                inventory.setQuantity(inventory.getQuantity() - 1);
        });
        System.out.println("list : "+list);
        inventoryRepo.saveAll(list);
        return list;
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepo.findAll();
    }
}
