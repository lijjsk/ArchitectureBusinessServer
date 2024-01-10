package com.lijjsk.Architecture.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.entity.Item;
import com.lijjsk.Architecture.entity.LineItem;
import com.lijjsk.Architecture.entity.Shop;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Data
public class ShopItemResponseDto implements Serializable {
    private Long id;
    private Long itemId;
    private String name;
    private Double price;
    private String imageUrl;
}
