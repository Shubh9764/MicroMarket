package com.project.orderService.service.impl;

import com.project.orderService.dto.InventoryResponse;
import com.project.orderService.dto.OrderLineItemsDto;
import com.project.orderService.dto.OrderRequest;
import com.project.orderService.events.OrderPlacedEvents;
import com.project.orderService.model.Order;
import com.project.orderService.model.OrderLineItems;
import com.project.orderService.repositories.OrderRepo;
import com.project.orderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    private KafkaTemplate<String,OrderPlacedEvents> kafkaTemplate;

//    @Autowired
//    RestTemplate restTemplate;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNo(UUID.randomUUID().toString())
                        .build();
       List<OrderLineItems> orderLineItems = orderRequest.getOrderLinesItemsDtoList().stream().map(this::mapDtoToOrder).toList();
       order.setOrderLineItems(orderLineItems);
       // call inventory service and place order if item is in stock
        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] responseList = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();
//        InventoryResponse[] responseList = restTemplate.getForObject("http://INVENTORY_SERVICE/api/inventory?skuCode="+skuCodes.get(0),InventoryResponse[].class);

       boolean isAvailable = Arrays.stream(responseList).allMatch(InventoryResponse::isInStock);
       if(isAvailable){
           kafkaTemplate.send("topic_order",new OrderPlacedEvents(order.getOrderNo()));
           orderRepo.save(order);
       }
       else
           throw new RuntimeException("Product out of Stock");

    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    private OrderLineItems mapDtoToOrder(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .price(orderLineItemsDto.getPrice())
                .build();
        return orderLineItems;
    }
}
