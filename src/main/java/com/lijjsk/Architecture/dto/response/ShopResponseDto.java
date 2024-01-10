package com.lijjsk.Architecture.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.entity.Business;
import com.lijjsk.Architecture.entity.Order;
import com.lijjsk.Architecture.entity.ShopItem;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
public class ShopResponseDto implements Serializable {
    private Long Id;
    private String name;
    private String address;
    private String phone;
}
