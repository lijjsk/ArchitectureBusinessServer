package com.lijjsk.Architecture.controller;

import com.lijjsk.Architecture.common.controller.LogicController;
import com.lijjsk.Architecture.dao.BusinessDao;
import com.lijjsk.Architecture.dto.request.ItemRequestDto;
import com.lijjsk.Architecture.dto.response.ResponseResult;
import com.lijjsk.Architecture.dto.request.ShopRequestDto;
import com.lijjsk.Architecture.entity.Business;
import com.lijjsk.Architecture.service.BusinessService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商家实体控制器")
@RestController
@RequestMapping("/business")
public class BusinessController extends LogicController<BusinessService, BusinessDao,Business,Long> {

    public BusinessController(@Autowired BusinessService ls) {
        super(ls);
    }

    @PostMapping("/createShop")
    public ResponseResult createShop(@RequestBody ShopRequestDto shopRequestDto){
        return ResponseResult.okResult(getService().createShop(shopRequestDto));
    }
    @GetMapping("/getShops")
    public ResponseResult getShopList(@RequestParam("businessId") Long businessId){
        return ResponseResult.okResult(getService().getShopList(businessId));
    }
    @PostMapping("/createItem")
    public ResponseResult createItem(@RequestBody ItemRequestDto itemRequestDto){
        return ResponseResult.okResult(getService().createItem(itemRequestDto));
    }
    @GetMapping("/getItems")
    public ResponseResult getItemList(@RequestParam("businessId") Long businessId){
        return ResponseResult.okResult(getService().getItemList(businessId));
    }

}
