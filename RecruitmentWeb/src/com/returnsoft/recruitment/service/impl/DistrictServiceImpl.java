package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.DistrictEao;
import com.returnsoft.recruitment.entity.District;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.DistrictService;


/**
 * Session Bean implementation class DistrictBean
 */
@Stateless
public class DistrictServiceImpl implements DistrictService {

	@EJB
	private DistrictEao districtEao;
	

	/**
	 * Default constructor.
	 */
	public DistrictServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<District> findByProvince(Integer provinceId)
			throws ServiceException {
		try {
			List<District> districts = districtEao.findByProvince(provinceId);
			
			return districts;
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
