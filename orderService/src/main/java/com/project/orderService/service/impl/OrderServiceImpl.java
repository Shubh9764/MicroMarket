package com.project.orderService.service.impl;

import com.project.orderService.dto.InventoryResponse;
import com.project.orderService.dto.OrderLineItemsDto;
import com.project.orderService.dto.OrderRequest;
import com.project.orderService.model.Order;
import com.project.orderService.model.OrderLineItems;
import com.project.orderService.repositories.OrderRepo;
import com.project.orderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    WebClient webClient;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNo(UUID.randomUUID().toString())
                        .build();
       List<OrderLineItems> orderLineItems = orderRequest.getOrderLinesItemsDtoList().stream().map(this::mapDtoToOrder).toList();
       order.setOrderLineItems(orderLineItems);
       // call inventory service and place order if item is in stock
        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] responseList = webClient.get().uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();

       boolean isAvailable = Arrays.stream(responseList).allMatch(InventoryResponse::isInStock);
       if(isAvailable)
           orderRepo.save(order);
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
