package com.lijjsk.Architecture.dao;

import com.lijjsk.Architecture.common.dao.LogicDAO;
import com.lijjsk.Architecture.entity.User;

public interface UserDao extends LogicDAO<User,Long> {
    public User getUserByUsername(String username);
}
