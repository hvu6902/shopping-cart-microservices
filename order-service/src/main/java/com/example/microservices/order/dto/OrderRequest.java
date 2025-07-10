package com.example.microservices.order.dto;

import java.math.BigDecimal;

public record OrderRequest (Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity) {
    
    // Additional methods or validations can be added here if needed
    
}
