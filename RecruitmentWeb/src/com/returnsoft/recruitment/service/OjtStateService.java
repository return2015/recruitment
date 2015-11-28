package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.OjtState;
import com.returnsoft.recruitment.exception.ServiceException;


public interface OjtStateService {
	
	public List<OjtState> findAll() throws ServiceException;
	public List<OjtState> findIsPending() throws ServiceException;

}
