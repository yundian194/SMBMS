package com.kuang.service.user;

import com.kuang.dao.BaseDao;
import com.kuang.dao.user.UserDao;
import com.kuang.dao.user.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
    private UserDao userDao;//业务层都会调用dao层 所以我们要引入dao层
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }
    @Override
    public User login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            //通过业务层调用具体的数据库操作
            user =  userDao.getLoginUser(connection,userCode,userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        Connection connection = null;
        Boolean flag = false;
        //修改密码
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection,id,pwd)>0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public boolean loginVerify(String userCode, String userPassword) throws SQLException {
        Connection connection = null;
        connection = BaseDao.getConnection();
        return userDao.loginVerify(connection,userCode,userPassword);
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }
    //根据条件查询用户列表
    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        if(connection != null){
            try {
                connection = BaseDao.getConnection();
                userList = userDao.getUserList(connection,userName,userRole,currentPageNo,pageSize);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                BaseDao.closeResource(connection,null,null);
            }
        }
        return userList;
    }
}
