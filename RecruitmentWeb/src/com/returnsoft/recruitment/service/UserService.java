package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.ServiceException;

public interface UserService {
	
	public User findById(Integer idUser) throws ServiceException;

	public User doLogin(String username, String password) throws ServiceException;

	public List<User> findByUserType(UserTypeEnum userType) throws ServiceException;
	
	public List<User> findList(String username, String names) throws ServiceException;
	
	public void add(User user) throws ServiceException;
	
	public User edit(User user) throws ServiceException;

}
