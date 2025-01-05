package com.dailycodework.dreamshops.DTO;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}
