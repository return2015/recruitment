package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.ServiceException;

public interface UserService {

	public User doLogin(String username, String password) throws ServiceException;

	public List<User> findByUserType(UserTypeEnum userType) throws ServiceException;

}
