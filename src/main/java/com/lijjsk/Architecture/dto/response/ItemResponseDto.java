package com.lijjsk.Architecture.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.entity.Business;
import com.lijjsk.Architecture.entity.ShopItem;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
public class ItemResponseDto implements Serializable {
    private Long id;
    private Long businessId;
    private String name;
    private Double price;
    private String imageUrl;
}
