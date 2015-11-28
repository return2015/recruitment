package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.District;
import com.returnsoft.recruitment.exception.ServiceException;



public interface DistrictService {
	
	public List<District> findByProvince(Integer provinceId)
			throws ServiceException;

}
