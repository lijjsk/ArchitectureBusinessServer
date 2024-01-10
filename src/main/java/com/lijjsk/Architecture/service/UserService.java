package com.lijjsk.Architecture.service;

import com.lijjsk.Architecture.common.service.LogicService;
import com.lijjsk.Architecture.dao.UserDao;
import com.lijjsk.Architecture.dto.response.UserResponseDto;
import com.lijjsk.Architecture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService extends LogicService<UserDao, User,Long> {

    public UserService(@Autowired UserDao dao) {
        super(dao);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public UserResponseDto login(String username, String password){
        User user = getDAO().getUserByUsername(username);
        if (Objects.equals(user.getPassword(), password)) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(user.getId());
            userResponseDto.setType(user.getType());
            userResponseDto.setUsername(user.getUsername());
            return userResponseDto;
        }
        return null;
    }
}
