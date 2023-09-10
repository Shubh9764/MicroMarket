package com.project.NotificationService;

import com.project.NotificationService.events.OrderPlacedEvents;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "topic_order")
	public void handleNotification(OrderPlacedEvents orderPlacedEvents){
		System.out.println("order Placed with order Number "+ orderPlacedEvents.getOrderNumber());
	}

}
