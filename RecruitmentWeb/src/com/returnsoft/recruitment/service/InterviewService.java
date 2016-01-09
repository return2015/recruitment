package com.returnsoft.recruitment.service;

import java.util.Date;
import java.util.List;

import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.ServiceException;

public interface InterviewService {
	
	public List<Interview> findByCandidate(Long candidateId) throws ServiceException;
	
	public List<Interview> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date createdAt, String documentNumber,
			String names, Integer first, Integer limit) throws ServiceException;
	
	public Integer findListCount(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date createdAt, String documentNumber,
			String names) throws ServiceException;

}
