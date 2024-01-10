package com.lijjsk.Architecture.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;
@Data
public class OrderRequestDto implements Serializable {
    private Long customerId;
    private Long shopId;
    private Set<LineItemRequestDto> lineItemRequestDtos;
}
