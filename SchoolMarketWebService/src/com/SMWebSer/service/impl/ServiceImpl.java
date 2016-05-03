package com.SMWebSer.service.impl;

import java.util.List;

import com.SMWebSer.Domain.User;
import com.SMWebSer.dao.UserDao;
import com.SMWebSer.service.Service;

public class ServiceImpl implements Service{

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int addUser(User user) {
		return (int) userDao.save(user);
	}

	@Override
	public List<User> getAllusers() {
		// TODO Auto-generated method stub
		return userDao.findAll(User.class);
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		userDao.delete(User.class,id);
	}

	@Override
	public User validLogin(String name, String password) {
		User user = userDao.findUserByNameAndPass(name, password);
		if(user != null){
			return user;
		}
		return null;
	}

	@Override
	public void UpdateUser(String name, String imageName) {
		userDao.UpdateUser(name, imageName);
	}

}
