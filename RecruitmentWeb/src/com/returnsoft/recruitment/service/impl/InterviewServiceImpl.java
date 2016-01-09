package com.returnsoft.recruitment.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.InterviewEao;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.InterviewService;

@Stateless
public class InterviewServiceImpl  implements InterviewService{
	
	@EJB
	private InterviewEao interviewEao;
	
	public List<Interview> findByCandidate(Long candidateId) throws ServiceException{
		
		try {
			
			return interviewEao.findByCandidateId(candidateId);
			
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
	public List<Interview> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date createdAt, String documentNumber,
			String names, Integer first, Integer limit) throws ServiceException {
		try {

			List<Interview> interviews = interviewEao.findListLimit(
					areasId, subAreasId, interviewStateId,
					scheduledAt,createdAt, documentNumber, names, first, limit);

			return interviews;
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
	public Integer findListCount(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date createdAt, String documentNumber,
			String names) throws ServiceException {
		try {

			Integer interviewsCount = interviewEao.findListCount(
					areasId, subAreasId, interviewStateId,
					scheduledAt,createdAt, documentNumber, names);

			return interviewsCount;
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
