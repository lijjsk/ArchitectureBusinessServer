package com.lijjsk.Architecture.service;

import com.lijjsk.Architecture.common.service.LogicService;
import com.lijjsk.Architecture.dao.BusinessDao;
import com.lijjsk.Architecture.dao.ShopDao;
import com.lijjsk.Architecture.dto.request.ItemRequestDto;
import com.lijjsk.Architecture.dto.request.ShopRequestDto;
import com.lijjsk.Architecture.dto.response.ItemResponseDto;
import com.lijjsk.Architecture.dto.response.ShopResponseDto;
import com.lijjsk.Architecture.entity.Business;
import com.lijjsk.Architecture.entity.Item;
import com.lijjsk.Architecture.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BusinessService extends LogicService<BusinessDao, Business,Long> {
    @Resource
    private ShopDao shopDao;

    public BusinessService(@Autowired BusinessDao lr) {
        super(lr);
    }
    /**
     * 增加门店
     */
    @Transactional
    public ShopResponseDto createShop(ShopRequestDto shopRequestDto){
        Business business = getDAO().getReferenceById(shopRequestDto.getBusinessId());
        Shop shop = business.createShop(shopRequestDto);
        getDAO().save(business);
        ShopResponseDto responseDto = new ShopResponseDto();
        responseDto.setName(shop.getName());
        responseDto.setPhone(shop.getPhone());
        responseDto.setAddress(shop.getAddress());
        responseDto.setId(shop.getId());
        return responseDto;
    }

    /**
     * 获取店铺列表
     * @param businessId
     * @return
     */

    public List<ShopResponseDto> getShopList(Long businessId) {
        Business business = getDAO().getReferenceById(businessId);
        List<ShopResponseDto> shopResponseDtos = new ArrayList<>();
        Set<Shop> shops = business.getShops();
        for (Shop shop : shops) {
            ShopResponseDto responseDto = new ShopResponseDto();
            responseDto.setAddress(shop.getAddress());
            responseDto.setName(shop.getName());
            responseDto.setPhone(shop.getPhone());
            responseDto.setId(shop.getId());
            shopResponseDtos.add(responseDto);
        }
        return shopResponseDtos;
    }

    /**
     * 商家创建商品
     */
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto){
        Business business = getDAO().getReferenceById(itemRequestDto.getBusinessId());
        Item item = business.createItem(itemRequestDto);
        getDAO().save(business);
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setName(item.getName());
        responseDto.setPrice(item.getPrice());
        responseDto.setImageUrl(item.getImageUrl());
        responseDto.setBusinessId(item.getBusiness().getId());
        return responseDto;
    }

    /**
     * 获取商品列表
     * @param businessId
     * @return
     */
    public List<ItemResponseDto> getItemList(Long businessId) {
        Business business = getDAO().getReferenceById(businessId);
        Set<Item> items = business.getItems();
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        for (Item item : items) {
            ItemResponseDto responseDto = new ItemResponseDto();
            responseDto.setId(item.getId());
            responseDto.setName(item.getName());
            responseDto.setImageUrl(item.getImageUrl());
            responseDto.setPrice(item.getPrice());
            responseDto.setBusinessId(item.getBusiness().getId());
            itemResponseDtos.add(responseDto);
        }
        return itemResponseDtos;
    }
}
