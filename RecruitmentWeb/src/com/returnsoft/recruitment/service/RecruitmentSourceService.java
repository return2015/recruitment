package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.RecruitmentSource;
import com.returnsoft.recruitment.exception.ServiceException;



public interface RecruitmentSourceService {
	
	public List<RecruitmentSource> findAll() throws ServiceException;
	
	public RecruitmentSource findById(Integer recruitmentSourceId)
			throws ServiceException;

}
