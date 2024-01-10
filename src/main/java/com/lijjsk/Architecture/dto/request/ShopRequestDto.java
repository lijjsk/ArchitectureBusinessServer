package com.lijjsk.Architecture.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopRequestDto implements Serializable {
    private Long businessId;
    private String name;
    private String address;
    private String phone;
}
