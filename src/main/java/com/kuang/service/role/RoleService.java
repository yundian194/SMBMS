package com.kuang.service.role;

import com.kuang.pojo.Role;
import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleService {
    //获取角色列表
    public List<Role> getRoleList(Connection connection);
}
