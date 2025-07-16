package com.example.microservices.order.client;

@FeignClient(value = "inventory, url = "http://localhost:8082)

public interface InventoryClient {
    
}
