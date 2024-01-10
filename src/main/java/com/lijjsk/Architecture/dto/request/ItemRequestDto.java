package com.lijjsk.Architecture.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemRequestDto implements Serializable {
    private Long businessId;
    private String name;
    private Double price;
    private String imageUrl;

}
