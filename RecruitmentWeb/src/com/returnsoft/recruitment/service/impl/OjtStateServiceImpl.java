package com.returnsoft.recruitment.service.impl;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.OjtStateEao;
import com.returnsoft.recruitment.entity.OjtState;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.OjtStateService;

/**
 * Session Bean implementation class OjtStateBean
 */
@Stateless
public class OjtStateServiceImpl implements OjtStateService {

	@EJB
	private OjtStateEao eao;
	

	/**
	 * Default constructor.
	 */
	public OjtStateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<OjtState> findAll() throws ServiceException {
		try {
			List<OjtState> entities = eao.findAll();
			
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
	public OjtState findIsPending() throws ServiceException {
		try {
			OjtState entity = eao.findIsPending();
			
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
	
	@Override
	public OjtState findById(Integer stateId)
			throws ServiceException {
		try {
			OjtState state = eao.findById(stateId);
			
			
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
