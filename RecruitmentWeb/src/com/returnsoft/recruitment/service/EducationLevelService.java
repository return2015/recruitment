package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.EducationLevel;
import com.returnsoft.recruitment.exception.ServiceException;



public interface EducationLevelService {
	
	public List<EducationLevel> findAll() throws ServiceException;

}
