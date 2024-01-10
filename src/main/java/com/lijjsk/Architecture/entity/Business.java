package com.lijjsk.Architecture.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lijjsk.Architecture.dto.request.ItemRequestDto;
import com.lijjsk.Architecture.dto.request.ShopRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("2")
@Where(clause = "is_deleted = 0")
public class Business extends User {
    @Column
    private String name;
    @Column
    private String phone;

    @OneToMany(
            mappedBy = "business",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties(value = {"business"})
    private Set<Item> items;
    @OneToMany(
            mappedBy = "business",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties(value = {"business"})
    private Set<Shop> shops;
    //商家创建门店
    public Shop createShop(ShopRequestDto shopRequestDto) {
        Shop shop = new Shop();
        shop.setBusiness(this);
        shop.setAddress(shopRequestDto.getAddress());
        shop.setName(shopRequestDto.getName());
        shop.setPhone(shopRequestDto.getPhone());
        shops.add(shop);
        return shop;
    }
    //商家创建商品
    public Item createItem(ItemRequestDto itemRequestDto){
        Item item = new Item();
        item.setBusiness(this);
        item.setName(itemRequestDto.getName());
        item.setPrice(itemRequestDto.getPrice());
        item.setImageUrl(itemRequestDto.getImageUrl());
        items.add(item);
        return item;
    }
    //商家在门店上架商品
    //商家在门店创建商品
    //商家查看门店订单
    //商家确认门店订单
    //商家取消已支付订单
}
