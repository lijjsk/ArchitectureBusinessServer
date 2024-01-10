package com.lijjsk.Architecture.controller;

import com.lijjsk.Architecture.common.controller.LogicController;
import com.lijjsk.Architecture.dao.ShopDao;
import com.lijjsk.Architecture.dto.response.ResponseResult;
import com.lijjsk.Architecture.entity.Shop;
import com.lijjsk.Architecture.service.ShopService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺实体控制器")
@RestController
@RequestMapping("/shop")
public class ShopController extends LogicController<ShopService, ShopDao, Shop,Long> {

    public ShopController(@Autowired ShopService ls) {
        super(ls);
    }
    @PostMapping("/addShopItem")
    public ResponseResult addShopItem(@RequestParam("shopId") Long shopId,@RequestParam("itemId") Long itemId){
        return ResponseResult.okResult(getService().addShopItem(shopId, itemId));
    }
    @GetMapping("/getShopItemList")
    public ResponseResult getShopItemList(@RequestParam("shopId") Long shopId){
        return ResponseResult.okResult(getService().getShopItemList(shopId));
    }
    @GetMapping("/getOrders")
    public ResponseResult getOrders(@RequestParam("shopId") Long shopId){
        return ResponseResult.okResult(getService().getOrders(shopId));
    }
    @GetMapping("/getOrdersByType")
    public ResponseResult getOrdersByType(@RequestParam("shopId") Long shopId,@RequestParam("status") Integer status){
        return ResponseResult.okResult(getService().getOrdersByType(shopId,status));
    }
    @PutMapping("/confirmOrder")
    public ResponseResult confirmOrder(@RequestParam("orderId") Long orderId,@RequestParam("shopId") Long shopId){
        return ResponseResult.okResult(getService().confirmOrder(orderId,shopId));
    }
    @PutMapping("/refundOrder")
    public ResponseResult refundOrder(@RequestParam("orderId") Long orderId,@RequestParam("shopId") Long shopId){
        return ResponseResult.okResult(getService().refundOrder(orderId,shopId));
    }
    @GetMapping("/getAllShop")
    public ResponseResult getAllShop(){
        return ResponseResult.okResult(getService().getAllShop());
    }
}
