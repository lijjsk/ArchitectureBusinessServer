package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class LineItem extends LogicEntity {
    //商品数量
    @Column
    private Integer quantity;
    //所属订单
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "lineItems")
    private Order order;
    //所属店铺商品
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "lineItems")
    private ShopItem shopItem;
}
