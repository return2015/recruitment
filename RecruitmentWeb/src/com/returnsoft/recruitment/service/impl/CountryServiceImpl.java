package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.CountryEao;
import com.returnsoft.recruitment.entity.Country;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.CountryService;


/**
 * Session Bean implementation class CountryBean
 */
@Stateless
public class CountryServiceImpl implements CountryService {

	@EJB
	private CountryEao countryEao;
	

	/**
	 * Default constructor.
	 */
	public CountryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Country> findAll() throws ServiceException {
		try {
			List<Country> countries = countryEao.findAll();
			
			return countries;
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
