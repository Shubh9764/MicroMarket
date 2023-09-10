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
        try {
            System.out.println("wait Started");
            Thread.sleep(10000);
            System.out.println("wait ended");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return inventoryRepo.findAll();
    }
}
