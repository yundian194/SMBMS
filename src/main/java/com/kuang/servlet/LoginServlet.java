package com.kuang.servlet;

import com.alibaba.fastjson.JSONArray;
import com.kuang.pojo.User;
import com.kuang.service.user.UserServiceImpl;
import com.kuang.util.Constants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    //Servlet:控制层,调用业务层代码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start....");
        String method = req.getParameter("method");
        if("loginPro".equals(method)){
            loginPro(req,resp);
        }else if ("loginVerify".equals(method)){
            try {
                loginVerify(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void loginPro(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取前端的用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);
        req.getSession().setAttribute(Constants.USER_SESSION,user);
        req.setAttribute("userName",user.getUserName());
        req.getRequestDispatcher("/jsp/frame.jsp").forward(req,resp);
    }
    public void loginVerify(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        Map map = new HashMap<String,String>();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        if(userService.loginVerify(userCode,userPassword)){//查有此人 提示用户可以登录
            map.put("result","success");//将结果添加到map里
            writer.write(JSONArray.toJSONString(map));
            writer.flush();
            writer.close();
        } else{//查无此人 提示用户名或者密码错误
            map.put("result","error");//将结果添加到map里
            writer.write(JSONArray.toJSONString(map));
            writer.flush();
            writer.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
