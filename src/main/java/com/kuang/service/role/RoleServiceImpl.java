package com.kuang.service.role;

import com.kuang.dao.BaseDao;
import com.kuang.dao.role.RoleDao;
import com.kuang.dao.role.RoleDaoImpl;
import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;//引入Dao层对象

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList(Connection connection){
        List<Role> roleList = new ArrayList<>();
        if (connection != null){
            try {
                roleList = roleDao.getRoleList(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                BaseDao.closeResource(connection,null,null);
            }
        }
        return roleList;
    }
}
