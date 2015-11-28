package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Country;
import com.returnsoft.recruitment.exception.ServiceException;



public interface CountryService {
	
	public List<Country> findAll() throws ServiceException;

}
