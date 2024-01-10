package com.lijjsk.Architecture.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto implements Serializable {
    private Long orderId;
    private Long userId;
    private Long shopId;
    private String username;
    private String shopName;
    private Date createdTime;
    private Double totalPrice;
    private List<LineItemResponseDto> LineItemResponseDtos;

}
