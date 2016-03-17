package com.returnsoft.recruitment.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.CandidateEao;
import com.returnsoft.recruitment.eao.InterviewEao;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.InterviewService;

@Stateless
public class InterviewServiceImpl  implements InterviewService{
	
	@EJB
	private InterviewEao interviewEao;
	
	@EJB
	private CandidateEao candidateEao;
	
	@Override
	public Interview edit(Interview interview) throws ServiceException{
		try {
			
			//VALIDA SI ES EL MISMO DNI
			Candidate candidateSource = candidateEao.findById(interview.getCandidate().getId());
			Candidate candidateTarget = candidateEao.findByDocumentNumber(interview.getCandidate().getDocumentNumber());
			
			if (!candidateSource.getDocumentNumber().equals(interview.getCandidate().getDocumentNumber())) {
				if (candidateTarget!=null) {
					throw new ServiceException("El postulante con DNI "+interview.getCandidate().getDocumentNumber()+" ya existe.",new ServiceException());
				}
			}
			
			//EDITA LA ENTREVISTA
			interview = interviewEao.edit(interview);
			
			return interview;
			
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
	public Interview findById(Long interviewId)
			throws ServiceException {
		try {
			
			Interview interview = interviewEao.findById(interviewId);
			
			return interview;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	public List<Interview> findByRequirement(Integer requirementId) throws ServiceException{
		try {
			
			return interviewEao.findByRequirementId(requirementId);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	
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
			Date interviewedAt,Date scheduledAt, Date createdAt, String documentNumber,
			String names,Integer userId, Integer first, Integer limit) throws ServiceException {
		try {

			List<Interview> interviews = interviewEao.findListLimit(
					areasId, subAreasId, interviewStateId,
					interviewedAt, scheduledAt,createdAt, documentNumber, names, userId, first, limit);

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
			Date interviewedAt, Date scheduledAt, Date createdAt, String documentNumber,
			String names,Integer userId) throws ServiceException {
		try {

			Integer interviewsCount = interviewEao.findListCount(
					areasId, subAreasId, interviewStateId,
					interviewedAt, scheduledAt,createdAt, documentNumber, names,userId);

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


	@Override
	public void add(Interview interview) throws ServiceException {
		try {
			
			if (interview.getCandidate().getId()!=null) {
				candidateEao.update(interview.getCandidate());
			}else{
				candidateEao.add(interview.getCandidate());
			}
			
			
			interviewEao.add(interview);
			
			//Candidate candidate = interview.getCandidate();
			
			interview.getCandidate().setInterview(interview);
			candidateEao.update(interview.getCandidate());
			
			
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
