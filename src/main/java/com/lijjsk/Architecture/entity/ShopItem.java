package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class ShopItem extends LogicEntity {
    //对应订单项
    @OneToMany(
            mappedBy = "shopItem",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties(value = {"shopItem"})
    private Set<LineItem> lineItems;
    //所属店铺
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"shopItems"})
    private Shop shop;
    //所属商品
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"shopItems"})
    private Item item;
}
