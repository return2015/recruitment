package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.RecruimentSource;
import com.returnsoft.recruitment.exception.ServiceException;



public interface RecruitmentSourceService {
	
	public List<RecruimentSource> findAll() throws ServiceException;
	
	public RecruimentSource findById(Integer recruitmentSourceId)
			throws ServiceException;

}
