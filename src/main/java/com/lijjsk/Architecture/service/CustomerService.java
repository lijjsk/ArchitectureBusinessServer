package com.lijjsk.Architecture.service;

import com.lijjsk.Architecture.common.service.LogicService;
import com.lijjsk.Architecture.dao.*;
import com.lijjsk.Architecture.dto.request.LineItemRequestDto;
import com.lijjsk.Architecture.dto.request.OrderRequestDto;
import com.lijjsk.Architecture.dto.response.LineItemResponseDto;
import com.lijjsk.Architecture.dto.response.OrderResponseDto;
import com.lijjsk.Architecture.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CustomerService extends LogicService<CustomerDao, Customer,Long> {
    @Resource
    private ShopDao shopDao;
    @Resource
    private LineItemDao lineItemDao;
    @Resource
    private ShopItemDao shopItemDao;
    @Resource
    private OrderDao orderDao;

    public CustomerService(@Autowired CustomerDao lr) {
        super(lr);
    }

    /**
     * 用户下单
     * @param orderRequestDto
     * @return
     */
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto){
        //获取到顾客对象
        Customer customer = getDAO().getReferenceById(orderRequestDto.getCustomerId());
        //获取到顾客下单的店铺
        Shop shop = shopDao.getReferenceById(orderRequestDto.getShopId());
        //下单的店铺名称
        log.info(shop.getName());
        //生成一个订单对象
        Order order = new Order();
        //预处理数据
        Set<LineItem> lineItems = new HashSet<>();
        double totalPrice = 0.0;
        for (LineItemRequestDto lineItemRequestDto : orderRequestDto.getLineItemRequestDtos()) {
            ShopItem shopItem = shopItemDao.getReferenceById(lineItemRequestDto.getId());
            LineItem lineItem = new LineItem();
            lineItem.setShopItem(shopItem);
            lineItem.setQuantity(lineItemRequestDto.getQuantity());
            lineItems.add(lineItem);
            lineItem.setOrder(order);
            totalPrice += shopItem.getItem().getPrice() * lineItem.getQuantity();
        }
        //保存订单中的信息，包括单是谁下的，在哪里下的，总价，初始状态，订单项，并返回订单对象
        order.createOrder(customer,shop,totalPrice,lineItems);
        //手动保存，要不是部分相关对象没有更新数据，获取到字段为空
        orderDao.save(order);
        //用户下单,也就是往订单列表中增加一个新的订单
        customer.placeOrder(order);
        getDAO().save(customer);
        //对应的店铺增加一个订单
        shop.addOrder(order);
        shopDao.save(shop);
        //返回订单信息
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
        return orderResponseDto;
    }

    /**
     * 用户支付订单
     * @param orderId
     * @param customerId
     * @return
     */
    @Transactional
    public Boolean paidOrder(Long orderId,Long customerId){
        Order order = orderDao.getReferenceById(orderId);
        Customer customer = getDAO().getReferenceById(customerId);
        Boolean flag = customer.paidOrder(order);
        if (flag){
            getDAO().save(customer);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用户取消订单
     * @param orderId
     * @param customerId
     */
    @Transactional
    public Boolean cancelOrder(Long orderId,Long customerId){

        Order order = orderDao.getReferenceById(orderId);
        if (order.getStatus() == 1){
            return false;
        }
        Customer customer = getDAO().getReferenceById(customerId);
        Boolean flag = customer.cancelOrder(order);
        if (flag){
            getDAO().save(customer);
            return true;
        }else {
            return false;
        }
    }

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderDao.getReferenceById(orderId);
        //返回订单信息
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
        return orderResponseDto;
    }
}
