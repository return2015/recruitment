package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Department;
import com.returnsoft.recruitment.exception.ServiceException;



public interface DepartmentService {
	
	public List<Department> findByCountry(Integer countryId)
			throws ServiceException;

}
