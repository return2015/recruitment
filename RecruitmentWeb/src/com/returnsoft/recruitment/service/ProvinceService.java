package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Province;
import com.returnsoft.recruitment.exception.ServiceException;



public interface ProvinceService {
	
	public List<Province> findByDepartment(Integer departmentId)
			throws ServiceException;

}
