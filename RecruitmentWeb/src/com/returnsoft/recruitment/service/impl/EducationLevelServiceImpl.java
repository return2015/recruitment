package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.EducationLevelEao;
import com.returnsoft.recruitment.entity.EducationLevel;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.EducationLevelService;


/**
 * Session Bean implementation class EducationLevelBean
 */
@Stateless
public class EducationLevelServiceImpl implements EducationLevelService {
	
	@EJB
	private EducationLevelEao eao;
	

    /**
     * Default constructor. 
     */
    public EducationLevelServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
   	public List<EducationLevel> findAll() throws ServiceException {
   		try {
   			List<EducationLevel> entities = eao.findAll();
   			
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

}
