package com.lijjsk.Architecture.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LineItemRequestDto implements Serializable {
    private Long id;
    private Integer quantity;
}
