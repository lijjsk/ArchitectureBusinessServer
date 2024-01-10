package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.common.constants.OrderConstants;
import com.lijjsk.Architecture.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
//order是关键字，需要自己指定表名，不会影响实体关系
@Table(name = "my_order")
@Where(clause = "is_deleted = 0")
public class Order extends LogicEntity {
    //订单总价
    @Column
    private Double totalPrice;
    //订单状态
    @Column
    private Integer status;
    //订单项
    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties(value = {"order"})
    private Set<LineItem> lineItems;
    //所属用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "orders")
    private Customer customer;
    //所属店铺
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "orders")
    private Shop shop;

    //生成完整的订单
    public void createOrder(Customer customer, Shop shop, double totalPrice, Set<LineItem> lineItems) {
        //更新当前对象信息
        this.setTotalPrice(totalPrice);
        this.setCustomer(customer);
        this.setShop(shop);
        this.setLineItems(lineItems);
        this.setStatus(OrderConstants.UNPAID);
        this.setIsDeleted(0);
    }

}
