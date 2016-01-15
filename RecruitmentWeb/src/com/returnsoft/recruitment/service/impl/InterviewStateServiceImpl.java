package com.returnsoft.recruitment.service.impl;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.InterviewStateEao;
import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.InterviewStateService;


/**
 * Session Bean implementation class InterviewStateBean
 */
@Stateless
public class InterviewStateServiceImpl implements InterviewStateService {

	@EJB
	private InterviewStateEao eao;


	/**
	 * Default constructor.
	 */
	public InterviewStateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<InterviewState> findAll() throws ServiceException {
		try {
			List<InterviewState> entities = eao.findAll();
			
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	
	@Override
	public List<InterviewState> findIsPendingAndScheduled() throws ServiceException {
		try {
			List<InterviewState> entities = eao.findIsPendingAndScheduled();
			
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	@Override
	public InterviewState findById(Integer stateId)
			throws ServiceException {
		try {
			InterviewState state = eao.findById(stateId);
			
			
			return state;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
		
	}
	

}
