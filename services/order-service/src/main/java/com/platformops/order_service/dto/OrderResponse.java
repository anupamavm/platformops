package com.platformops.order_service.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String product;
    private int quantity;
    private double price;
    private String status;
    private LocalDateTime createdAt;
}
}