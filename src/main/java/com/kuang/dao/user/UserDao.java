package com.kuang.dao.user;

import com.kuang.pojo.Role;
import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException;
    //修改当前用户的密码
    public int updatePwd(Connection connection,int id,String password) throws SQLException;
    //增加一个用户
    public boolean addUser(Connection connection, String userCode, String userName, String userPassword,
                           Integer gender, Date birthday,String phone,String address);
    //用户登录验证
    public boolean loginVerify(Connection connection, String userCode, String userPassword) throws SQLException;
    //查询用户名或者角色查询用户总数【最难理解的SQL】
    public int getUserCount(Connection connection,String userName,int userRole) throws SQLException;
    //获取用户列表
    public List<User> getUserList(Connection connection,String userName, int userRole, int currentPageNo,int pageSize) throws Exception;


}
