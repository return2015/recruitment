package com.returnsoft.recruitment.service.impl;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.TrainingStateEao;
import com.returnsoft.recruitment.entity.TrainingState;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.TrainingStateService;


/**
 * Session Bean implementation class TrainingStateBean
 */
@Stateless
public class TrainingStateServiceImpl implements TrainingStateService {

	@EJB
	private TrainingStateEao eao;

	/**
	 * Default constructor.
	 */
	public TrainingStateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TrainingState> findAll() throws ServiceException {
		try {
			List<TrainingState> entities = eao.findAll();
			
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
	public TrainingState findIsPendingNotReady() throws ServiceException {
		try {
			TrainingState entity = eao.findIsPendingNotReady();
			
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	
	/*@Override
	public List<TrainingState> findIsPending() throws ServiceException {
		try {
			List<TrainingState> entities = eao.findIsPending();
			
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}*/
	
	@Override
	public TrainingState findById(Integer stateId)
			throws ServiceException {
		try {
			TrainingState state = eao.findById(stateId);
			
			
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
