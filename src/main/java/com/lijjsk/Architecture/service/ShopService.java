package com.lijjsk.Architecture.service;

import com.lijjsk.Architecture.common.constants.OrderConstants;
import com.lijjsk.Architecture.common.service.LogicService;
import com.lijjsk.Architecture.dao.ItemDao;
import com.lijjsk.Architecture.dao.OrderDao;
import com.lijjsk.Architecture.dao.ShopDao;
import com.lijjsk.Architecture.dto.response.LineItemResponseDto;
import com.lijjsk.Architecture.dto.response.OrderResponseDto;
import com.lijjsk.Architecture.dto.response.ShopItemResponseDto;
import com.lijjsk.Architecture.dto.response.ShopResponseDto;
import com.lijjsk.Architecture.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShopService extends LogicService<ShopDao, Shop,Long> {
    @Resource
    private ItemDao itemDao;
    @Resource
    private OrderDao orderDao;

    public ShopService(@Autowired ShopDao lr) {
        super(lr);
    }

    /**
     * 获取店铺商品列表
     * @param shopId
     * @return
     */
    public List<ShopItemResponseDto> getShopItemList(Long shopId){
        List<ShopItemResponseDto> responseDtos = new ArrayList<>();
        Shop shop = getDAO().getReferenceById(shopId);
        Set<ShopItem> shopItems = shop.getShopItems();
        for (ShopItem shopItem : shopItems) {
            ShopItemResponseDto responseDto = new ShopItemResponseDto();
            responseDto.setImageUrl(shopItem.getItem().getImageUrl());
            responseDto.setName(shopItem.getItem().getName());
            responseDto.setPrice(shopItem.getItem().getPrice());
            responseDto.setItemId(shopItem.getItem().getId());
            responseDto.setId(shopItem.getId());
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
    /**
     * 上架商品
     * @param itemId
     * @return
     */
    @Transactional
    public ShopItemResponseDto addShopItem(Long shopId,Long itemId){
        Item item = itemDao.getReferenceById(itemId);
        Shop shop = getDAO().getReferenceById(shopId);
        shop.addShopItem(item);
        getDAO().save(shop);
        ShopItemResponseDto responseDto = new ShopItemResponseDto();
        responseDto.setItemId(item.getId());
        responseDto.setName(item.getName());
        responseDto.setPrice(item.getPrice());
        responseDto.setImageUrl(item.getImageUrl());
        return responseDto;
    }

    public List<OrderResponseDto> getOrders(Long shopId) {
        Shop shop = getDAO().getReferenceById(shopId);
        Set<Order> orders = shop.getOrders();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserId(order.getCustomer().getId());
            orderResponseDto.setShopId(order.getShop().getId());
            orderResponseDto.setUsername(order.getCustomer().getUsername());
            orderResponseDto.setShopName(order.getShop().getName());
            orderResponseDto.setCreatedTime(order.getCreateTime());
            orderResponseDto.setTotalPrice(order.getTotalPrice());
            List<LineItemResponseDto> lineItemResponseDtos = new ArrayList<>();
            for (LineItem lineItem : order.getLineItems()) {
                LineItemResponseDto lineItemResponseDto = new LineItemResponseDto();
                lineItemResponseDto.setName(lineItem.getShopItem().getItem().getName());
                lineItemResponseDto.setShopItemId(lineItem.getShopItem().getId());
                lineItemResponseDto.setPrice(lineItem.getShopItem().getItem().getPrice());
                lineItemResponseDto.setQuantity(lineItem.getQuantity());
                lineItemResponseDtos.add(lineItemResponseDto);
            }
            orderResponseDto.setLineItemResponseDtos(lineItemResponseDtos);
            orderResponseDtos.add(orderResponseDto);
        }
        return orderResponseDtos;
    }

    /**
     * 确认订单
     * @param orderId
     * @param shopId
     * @return
     */
    @Transactional
    public Boolean confirmOrder(Long orderId,Long shopId){
        Order order = orderDao.getReferenceById(orderId);
        if (!Objects.equals(order.getStatus(), OrderConstants.PAID)){
            return false;
        }
        Shop shop = getDAO().getReferenceById(shopId);
        Boolean flag = shop.confirmOrder(order);
        if (flag){
            getDAO().save(shop);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 退款
     * @param orderId
     * @param shopId
     * @return
     */
    @Transactional
    public Boolean refundOrder(Long orderId,Long shopId){
        Order order = orderDao.getReferenceById(orderId);
        if (!Objects.equals(order.getStatus(), OrderConstants.PAID)){
            return false;
        }
        Shop shop = getDAO().getReferenceById(shopId);
        Boolean flag = shop.refundOrder(order);
        if (flag){
            getDAO().save(shop);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 按照类型返回订单列表
     * @param shopId
     * @param status
     * @return
     */
    public List<OrderResponseDto> getOrdersByType(Long shopId, Integer status) {
        Shop shop = getDAO().getReferenceById(shopId);
        Set<Order> ordersByType = shop.getOrdersByType(status);
        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        for (Order order : ordersByType) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setUserId(order.getCustomer().getId());
            orderResponseDto.setShopId(order.getShop().getId());
            orderResponseDto.setUsername(order.getCustomer().getUsername());
            orderResponseDto.setShopName(order.getShop().getName());
            orderResponseDto.setCreatedTime(order.getCreateTime());
            orderResponseDto.setTotalPrice(order.getTotalPrice());
            orderResponseDto.setLineItemResponseDtos(null);
            responseDtoList.add(orderResponseDto);
        }
        return responseDtoList;
    }

    public List<ShopResponseDto> getAllShop() {
        List<Shop> shops = this.getAll();
        List<ShopResponseDto> shopResponseDtos = new ArrayList<>();
        for (Shop shop : shops) {
            ShopResponseDto responseDto = new ShopResponseDto();
            responseDto.setAddress(shop.getAddress());
            responseDto.setPhone(shop.getPhone());
            responseDto.setName(shop.getName());
            responseDto.setId(shop.getId());
            shopResponseDtos.add(responseDto);
        }
        return shopResponseDtos;
    }
}
