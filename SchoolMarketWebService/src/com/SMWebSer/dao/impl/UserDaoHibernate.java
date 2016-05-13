package com.SMWebSer.dao.impl;

import java.util.List;

import com.SMWebSer.Domain.User;
import com.SMWebSer.common.dao.impl.BaseDaoHibernate4;
import com.SMWebSer.dao.UserDao;

public class UserDaoHibernate extends BaseDaoHibernate4<User> implements UserDao {

	@Override
	public User findUserByNameAndPass(String name, String password) {
		String hql = "select distinct user from User user where user.name = ?0 and user.password = ?1";
		List<User> users = find(hql, name, password);
		if (users.size() == 1) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public void UpdateUser(String name, String imageName) {
		String hql = "update User user set user.image = ?0 where user.name = ?1";
		update(hql, name, imageName);

	}

	@Override
	public User findUserByName(String name) {
		String hql = "select distinct user from User user where user.name = ?0";
		List<User> users = find(hql,name);
		if(users.size() == 1){
			return users.get(0);
		}
		return null;
	}

}
