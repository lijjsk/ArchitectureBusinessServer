package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.common.constants.OrderConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("1")
@Where(clause = "is_deleted = 0")
public class Customer extends User{
    @Column
    private String name;
    @Column
    private String phone;
    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"customer"})
    private Set<Order> orders;
    //顾客下单
    public void placeOrder(Order order) {
        orders.add(order);
    }
    //支付订单
    public Boolean paidOrder(Order order){
        for (Order orderItem : this.orders) {
            if (orderItem.getId().equals(order.getId())) {
                // 更新订单状态为已经支付
                orderItem.setStatus(OrderConstants.PAID);
                return true;
            }
        }
        return false;
    }
    //顾客取消未支付订单
    public Boolean cancelOrder(Order order){
        for (Order orderItem : this.orders) {
            if (orderItem.getId().equals(order.getId())) {
                // 更新订单状态为已经支付
                orderItem.setStatus(OrderConstants.CANCEL);
                return true;
            }
        }
        return false;
    }
    //顾客查看订单
    //顾客支付订单

    //顾客下单
}
