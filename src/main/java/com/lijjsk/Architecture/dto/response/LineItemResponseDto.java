package com.lijjsk.Architecture.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class LineItemResponseDto implements Serializable {
    private Long ShopItemId;
    private String name;
    private Integer quantity;
    private Double price;
}
