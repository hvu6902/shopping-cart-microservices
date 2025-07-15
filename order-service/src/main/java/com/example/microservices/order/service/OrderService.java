package com.example.microservices.order.service;

import com.example.microservices.order.dto.OrderRequest;
import com.example.microservices.order.model.Order;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.microservices.order.repository.OrderRepository;
import com.example.microservices.order.client.InventoryClient;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        // save order to database
        orderRepository.save(order);
        } else {
            throw new RuntimeException("Product is not in stock");
        }
    }
}
