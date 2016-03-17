package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.RecruitmentSourceEao;
import com.returnsoft.recruitment.entity.RecruitmentSource;
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
	public List<RecruitmentSource> findAll() throws ServiceException {
		try {
			List<RecruitmentSource> recruimentsSource = recruimentSourceEao
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
	public RecruitmentSource findById(Integer recruimentSourceId)
			throws ServiceException {
		
		try {

			RecruitmentSource recruimentSource = recruimentSourceEao.findById(recruimentSourceId);
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
