package com.kuang.servlet;

import com.alibaba.fastjson.JSONArray;
import com.kuang.pojo.User;
import com.kuang.service.user.UserService;
import com.kuang.service.user.UserServiceImpl;
import com.kuang.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("userServlet start.....");
        String method = req.getParameter("method");
        if(method.equals("savepwd")&&(null!=method)){
            updatePwd(req,resp);
        }else if (method.equals("pwdmodify")&&(null!=method)){
            pwdModify(req,resp);
        }
    }
    //修改用户密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("userServlet start....");
        User o = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newpassword");
        boolean flag =false;
        if(o!=null&& !StringUtils.isNullOrEmpty(newPassword)){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(o.getId(),newPassword);
            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                //密码修改成功 移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
            }
        }else {
            req.setAttribute("message","新密码有问题");
        }
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }
    //验证旧密码，session中有用户的密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        //万能的Map
        Map<String,String> resultMap = new HashMap<String,String>();
        if(null==o){//session失效了
            resultMap.put("result","sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){//输入密码为空
            resultMap.put("result","error");
        }else{
            String userPassword = ((User) o).getUserPassword();
            String resultStr = oldpassword.equals(userPassword)?"true":"false";//用户输入的密码和旧密码是否相同 相同true 不同false
            resultMap.put("result",resultStr);
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴的JSON工具类 ,转换格式
            //将其他类型数据转换为json响应给前端
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
