package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.exception.ServiceException;


public interface InterviewStateService {
	
	public List<InterviewState> findAll() throws ServiceException;
	
	public List<InterviewState> findIsPending() throws ServiceException;
	public InterviewState findById(Integer stateId) throws ServiceException;
	

}
