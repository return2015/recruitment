package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.TrainingState;
import com.returnsoft.recruitment.exception.ServiceException;


public interface TrainingStateService {
	
	public List<TrainingState> findAll() throws ServiceException;
	public List<TrainingState> findIsPending() throws ServiceException;
	public TrainingState findById(Integer id) throws ServiceException;

}
