package com.returnsoft.recruitment.service.impl;


import java.util.List;

import javax.ejb.EJB;

import javax.ejb.Stateless;

import com.returnsoft.recruitment.exception.UserInactiveException;
import com.returnsoft.recruitment.exception.UserNotFoundException;
import com.returnsoft.recruitment.exception.UserWrongPasswordException;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.eao.UserEao;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.ServiceException;



/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class UserServiceImpl implements UserService {

	
	@EJB
	private UserEao userEao;

	/*@EJB
	private Conversion conv;*/

	@Override
	public User doLogin(String username, String password) throws ServiceException {
		try {

			User user = userEao.findByUsername(username);

			if (user==null) {
				throw new UserNotFoundException();
			}
			
			if (!user.getPassword().equals(password)) {
				throw new UserWrongPasswordException();
			}
			
			if (user.getIsActive().equals(false)) {
				throw new UserInactiveException();
			}

			/*UserType userType = userEao.findUserType(user.getId());
			if (userType == null) {
				throw new ServiceException("El tipo de usuario es nulo.");
			}

			if (!Arrays.asList(USERTYPES).contains(userType.getCode())) {
				throw new ServiceException("El tipo de usuario es inválido.");
			}

			if (userType.getCode().equals(USERTYPES[0])) {

			} else if (userType.getCode().equals(USERTYPES[1])) {

			} else if (userType.getCode().equals(USERTYPES[2])) {

			}*/

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	public List<User> findByUserType(UserTypeEnum userType) throws ServiceException{
		try {
			
			return userEao.findByUserType(userType);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
		
	}

	
}
