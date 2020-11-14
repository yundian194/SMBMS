package com.kuang.service.user;

import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String userPassword);
    //根据用户ID修改密码
    public boolean updatePwd(int id, String pwd);
    //用户登录验证
    public boolean loginVerify(String userCode, String userPassword) throws SQLException;
    //查询记录数
    public int getUserCount(String userName,int userRole);
    //根据条件查询用户列表
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) throws Exception;
}
