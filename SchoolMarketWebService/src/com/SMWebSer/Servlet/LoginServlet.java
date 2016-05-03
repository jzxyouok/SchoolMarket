package com.SMWebSer.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SMWebSer.Domain.User;
import com.SMWebSer.Utils.ServiceUtil;
import com.SMWebSer.service.Service;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet",urlPatterns = "/login.jsp")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service;
	private User user;
       
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	service = new ServiceUtil(getServletContext()).getServiceBean();
    	
    	//获取请求的name,password，返回User对象
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	validLogin(name, password);
    	Gson gson = new Gson();
    	out.write(gson.toJson(user));
    }

    public void validLogin(String name,String password){
    	user = service.validLogin(name, password);
    	if(user == null){
    		System.out.println("登陆失败！");
    	}else{
    		System.out.println("登陆成功！");
    	}
    }
}
