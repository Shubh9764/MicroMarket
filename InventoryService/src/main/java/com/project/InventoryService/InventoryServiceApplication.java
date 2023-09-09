package com.project.InventoryService;

import com.project.InventoryService.model.Inventory;
import com.project.InventoryService.repo.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner lodData(InventoryRepo inventoryRepo){
//		return  args -> {
//			Inventory inventory = new Inventory();
//			inventory.setQuantity(10);
//			inventory.setSkuCode("Laptop_1");
//			Inventory inventory2 = new Inventory();
//			inventory2.setQuantity(0);
//			inventory2.setSkuCode("Smartphone_1");
//			inventoryRepo.save(inventory);
//			inventoryRepo.save(inventory2);
//		};
//	}

}
