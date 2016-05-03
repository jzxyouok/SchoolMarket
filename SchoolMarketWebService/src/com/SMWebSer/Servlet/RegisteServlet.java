package com.SMWebSer.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.SMWebSer.Domain.User;
import com.SMWebSer.Utils.ServiceUtil;
import com.SMWebSer.service.Service;
import com.google.gson.Gson;

/**
 * Servlet implementation class RegisteServlet
 */
@WebServlet(name = "RegisteServlet",urlPatterns = "/regist.jsp")
public class RegisteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	response.setContentType("text/html;charset = utf-8");
    	PrintWriter out = response.getWriter();
    	service = new ServiceUtil(getServletContext()).getServiceBean();
    	
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	int user_id = validRegist(name, password, "default.jpg");
    	//返回给客户端已注册用户的id,>0则被注册了
    	JSONObject jsonObject = new JSONObject();
    	try {
			jsonObject.put("user_id", user_id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	out.write(jsonObject.toString());
    	System.out.println(jsonObject.toString());
    }
    
    //查找是否该用户名已经被注册了，若有返回已注册用户名
    public int validRegist(String name,String password,String imageName){
    	User user = new User(name,password,imageName);
    	User user_find = service.validLogin(name, password);
    	if(user_find != null){
    		System.out.print("该用户名已被注册！");
    		return user_find.getId();
    	}else{
    		service.addUser(user);
    		System.out.println("注册成功！");
    	}
    	return -1;
    }
}
