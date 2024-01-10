package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.lijjsk.Architecture.common.constants.OrderConstants;
import com.lijjsk.Architecture.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class Shop extends LogicEntity {
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phone;
    //所属商家
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = ("shops"))
    private Business business;
    //店铺商品
    @OneToMany(
            mappedBy = "shop",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties(value = {"shop"})
    private Set<ShopItem> shopItems;
    //店铺订单
    @OneToMany(
            mappedBy = "shop",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties(value = {"shop"})
    private Set<Order> orders;
    //店铺上架商品
    public ShopItem addShopItem(Item item){
        ShopItem shopItem = new ShopItem();
        shopItem.setShop(this);
        shopItem.setItem(item);
        shopItems.add(shopItem);
        return shopItem;
    }

    public Boolean confirmOrder(Order order) {
        for (Order orderItem : this.orders) {
            if (orderItem.getId().equals(order.getId())) {
                // 更新订单状态为已经支付
                orderItem.setStatus(OrderConstants.CONFIRM);
                return true;
            }
        }
        return false;
    }

    public Boolean refundOrder(Order order) {
        for (Order orderItem : this.orders) {
            if (orderItem.getId().equals(order.getId())) {
                // 更新订单状态为已经支付
                orderItem.setStatus(OrderConstants.REFUND);
                return true;
            }
        }
        return false;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Set<Order> getOrdersByType(Integer status) {
        Set<Order> orderSet = this.getOrders();
        Set<Order> orderSetByType = new HashSet<>();
        for (Order order : orderSet) {
            if (Objects.equals(order.getStatus(), status)){
                orderSetByType.add(order);
            }
        }
        return orderSetByType;
    }
}
