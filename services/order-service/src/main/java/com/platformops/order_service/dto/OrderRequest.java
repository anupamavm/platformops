package com.platformops.order_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long userId;
    private String product;
    private int quantity;
    private double price;
}