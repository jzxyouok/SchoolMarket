package com.SMWebSer.dao;

import com.SMWebSer.Domain.User;
import com.SMWebSer.common.dao.BaseDao;

public interface UserDao extends BaseDao<User>{
	// 根据用户名密码查找用户
	User findUserByNameAndPass(String name, String password);

	//注册时用户名验证
	User findUserByName(String name);
	
	// 根据name更新user对象
	void UpdateUser(String name, String imageName);
}
