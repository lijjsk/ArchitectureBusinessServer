package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.common.entity.LogicEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Where(clause = "is_deleted = 0")
public class Item extends LogicEntity {
    //名称
    @Column
    private String name;
    //价格
    @Column
    private Double price;
    //商品图片
    @Column
    private String imageUrl;
    //所属商家
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"items"})
    private Business business;
    //对应店铺商品
    @OneToMany(
            mappedBy = "item",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties(value = {"item"})
    private Set<ShopItem> shopItems;
}
