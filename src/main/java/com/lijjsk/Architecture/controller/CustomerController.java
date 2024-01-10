package com.lijjsk.Architecture.controller;

import com.lijjsk.Architecture.common.controller.LogicController;
import com.lijjsk.Architecture.dao.CustomerDao;
import com.lijjsk.Architecture.dto.request.OrderRequestDto;
import com.lijjsk.Architecture.dto.response.ResponseResult;
import com.lijjsk.Architecture.entity.Customer;
import com.lijjsk.Architecture.service.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "顾客实体控制器")
@RestController
@RequestMapping("/customer")
public class CustomerController extends LogicController<CustomerService, CustomerDao, Customer,Long> {
    public CustomerController(@Autowired CustomerService ls) {
        super(ls);
    }
    @PostMapping("/placeOrder")
    public ResponseResult placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseResult.okResult(getService().placeOrder(orderRequestDto));
    }
    @PutMapping("/paidOrder")
    public ResponseResult paidOrder(@RequestParam("orderId") Long orderId,@RequestParam("customerId") Long customerId){
        return ResponseResult.okResult(getService().paidOrder(orderId,customerId));
    }
    @PutMapping("/cancelOrder")
    public ResponseResult cancelOrder(@RequestParam("orderId") Long orderId,@RequestParam("customerId") Long customerId){
        return ResponseResult.okResult(getService().cancelOrder(orderId,customerId));
    }
    @GetMapping("/getOrder")
    public ResponseResult getOrder(@RequestParam("orderId") Long orderId){
        return ResponseResult.okResult(getService().getOrder(orderId));
    }
}
