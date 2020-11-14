package com.kuang.servlet;

import com.kuang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute(Constants.USER_SESSION)!=null){
            session.removeAttribute(Constants.USER_SESSION);
            PrintWriter writer = resp.getWriter();
            //writer.print("<h1 color ="+"red"+">注销登录成功!<h1>");
            System.out.println("user log out success!");
            resp.sendRedirect("/login.jsp");
        }
    }
}
