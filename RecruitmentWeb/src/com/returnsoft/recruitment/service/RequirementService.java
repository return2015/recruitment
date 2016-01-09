package com.returnsoft.recruitment.service;

import java.util.Date;
import java.util.List;

import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.exception.ServiceException;

public interface RequirementService {
	
	public List<Requirement> findList(Date period,Integer areaId, Integer subAreaId, Integer recruiterId) throws ServiceException;
	
	public void add(Requirement requirement) throws ServiceException;
	
	//public Requirement findById(Integer idrequirement) throws RecruitmentException;
	
	//public List<Requirement> findListDetail(String periodFormatted,Integer areaId, Integer subAreaId, Integer recruiterId) throws RecruitmentException;
	
	//public List<Requirement> findListSummaryByRecruiter(String periodFormatted,Integer areaId, Integer subAreaId, Integer recruiterId) throws RecruitmentException;
	
	//public List<Requirement> findListSummaryByAreaParent(String periodFormatted,Integer areaId, Integer subAreaId, Integer recruiterId) throws RecruitmentException;

}