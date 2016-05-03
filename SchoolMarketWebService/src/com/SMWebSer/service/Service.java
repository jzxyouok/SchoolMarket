package com.SMWebSer.service;

import java.util.List;

import com.SMWebSer.Domain.User;

public interface Service {

	//添加用户
	int addUser(User user);

	//查找所有的用户
	List<User> getAllusers();
	
	//删除用户
	void deleteUser(int id);
	
	//登陆时的密码验证
	User validLogin(String name,String password);
	
	//更新用户头像的操作
	void UpdateUser(String name,String imageName);
}
