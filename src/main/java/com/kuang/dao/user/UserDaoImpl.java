package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Role;
import com.kuang.pojo.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (connection != null) {
            String sql = "select * from smbms_user where userCode = ? and userPassWord = ?";
            Object[] params = {userCode, userPassword};
            rs = BaseDao.executeQuery(connection, pstm, rs, sql, params);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatBy(rs.getInt("createBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (null != connection) {
            String sql = "update  smbms_user set userPassword = ? where id = ?";
            Object params[] = {password, id};
            execute = BaseDao.executeUpdate(connection, sql, params, pstm);
            BaseDao.closeResource(null, pstm, null);//释放资源
        }
        return execute;
    }

    @Override
    public boolean addUser(Connection connection, String userCode, String userName, String userPassword, Integer gender, Date birthday, String phone, String address) {
        return false;
    }

    @Override
    public boolean loginVerify(Connection connection, String userCode, String userPassword) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean flag = false;
        if (null != connection) {
            Object[] params = {userCode, userPassword};
            String sql = "select * from smbms_user where userCode = ? and userPassword =  ?";
            rs = BaseDao.executeQuery(connection, pstm, rs, sql, params);
            if (rs.next()) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        ArrayList<Object> list = new ArrayList<Object>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //怎么把list转换为数组
            Object[] objects = list.toArray();
            System.out.println("UserDoImpl->getUserCount" + sql.toString());
            rs = BaseDao.executeQuery(connection, pstm, rs, sql.toString(), objects);
            if (rs.next()) {
                count = rs.getInt("count");//从结果集中获取最终的数量
            }
            BaseDao.closeResource(connection, pstm, rs);
    }
            return count;
}

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u ,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if (userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //在数据库中，分页使用 limit startindex,pageSize;总数
            //0,5 代表第一页 从0开始 5个数据记录 limit x,y (表示从x开始之后的y个记录)
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            Object[] params = list.toArray();
            System.out.println("sql---->" + sql.toString());
            rs = BaseDao.executeQuery(connection,pstm,rs,sql.toString(),params);
            while (rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString(userName));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return userList;
    }
}
