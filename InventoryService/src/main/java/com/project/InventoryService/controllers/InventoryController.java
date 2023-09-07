package com.project.InventoryService.controllers;

import com.project.InventoryService.dto.InventoryResponse;
import com.project.InventoryService.model.Inventory;
import com.project.InventoryService.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    private boolean isInStock(@PathVariable String skuCode){

        return inventoryService.isInStock(skuCode);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<InventoryResponse> areInStock(@RequestParam List<String> skuCode){

        return inventoryService.areInStock(skuCode).stream().map(inventory -> InventoryResponse.builder().skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0).build()).toList();
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    private List<Inventory> getALl(){

        return inventoryService.getAll();
    }
}
