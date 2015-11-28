package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.RecruitmentSourceEao;
import com.returnsoft.recruitment.entity.RecruimentSource;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.RecruitmentSourceService;


/**
 * Session Bean implementation class RecruimentSourceBean
 */
@Stateless
public class RecruitmentSourceServiceImpl implements RecruitmentSourceService {

	@EJB
	private RecruitmentSourceEao recruimentSourceEao;


	@Override
	public List<RecruimentSource> findAll() throws ServiceException {
		try {
			List<RecruimentSource> recruimentsSource = recruimentSourceEao
					.findAll();
			
			return recruimentsSource;
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
	public RecruimentSource findById(Integer recruimentSourceId)
			throws ServiceException {
		
		try {

			RecruimentSource recruimentSource = recruimentSourceEao.findById(recruimentSourceId);
			return recruimentSource;

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
