package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.CandidateState;
import com.returnsoft.recruitment.exception.ServiceException;


public interface CandidateStateService {
	
	public List<CandidateState> findAll() throws ServiceException;
	public List<CandidateState> findActiveList() throws ServiceException;
	public CandidateState findById(Integer stateId) throws ServiceException;
	

}
