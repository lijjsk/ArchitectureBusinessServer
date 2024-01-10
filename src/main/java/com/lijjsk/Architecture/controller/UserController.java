package com.lijjsk.Architecture.controller;

import com.lijjsk.Architecture.common.controller.LogicController;
import com.lijjsk.Architecture.dao.UserDao;
import com.lijjsk.Architecture.dto.enums.AppHttpCodeEnum;
import com.lijjsk.Architecture.dto.request.LoginDto;
import com.lijjsk.Architecture.dto.response.ResponseResult;
import com.lijjsk.Architecture.dto.response.UserResponseDto;
import com.lijjsk.Architecture.entity.User;
import com.lijjsk.Architecture.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户实体控制器")
@RestController
@RequestMapping("/user")
public class UserController extends LogicController<UserService, UserDao, User,Long> {
    public UserController(@Autowired UserService ls) {
        super(ls);
    }
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        UserResponseDto userResponseDto = getService().login(loginDto.getUsername(),loginDto.getPassword());
        if (userResponseDto != null) {
            return ResponseResult.okResult(userResponseDto);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }
}
