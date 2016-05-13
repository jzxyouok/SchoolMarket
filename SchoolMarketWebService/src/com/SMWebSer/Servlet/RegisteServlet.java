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

import com.SMWebSer.Domain.CallBack;
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
    	CallBack callback = validRegist(name, password, "default.jpg");
    	
    	Gson gson = new Gson();
    	out.write(gson.toJson(callback));
    	System.out.println(gson.toJson(callback));
    }
    
    //查找是否该用户名已经被注册了，若有返回已注册用户名
    public CallBack validRegist(String name,String password,String imageName){
    	CallBack callback = new CallBack();
    	
    	User user = new User(name,password,imageName);
    	User user_find = service.validRegist(name);
    	//用户名存在了，则返回代码为401，和用户的ID
    	if(user_find != null){
    		//将已注册的用户名的ID转成json格式
    		JSONObject jsonObject = new JSONObject();
    		try {
				jsonObject.put("user_id", user_find.getId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		callback = new CallBack(401,"该用户名已被注册",jsonObject);
    		System.out.print("该用户名已被注册！");
    		return callback;
    	}else{
    		service.addUser(user);
    		callback = new CallBack(201,"注册成功",null);
    		System.out.println("注册成功！");
    	}
    	return callback;
    }
}
