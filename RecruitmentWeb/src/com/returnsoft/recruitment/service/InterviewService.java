package com.returnsoft.recruitment.service;

import java.util.Date;
import java.util.List;

import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.ServiceException;

public interface InterviewService {
	
	public void add(Interview interview) throws ServiceException;
	
	public Interview edit(Interview interview) throws ServiceException;
	
	public Interview findById(Long interviewId) throws ServiceException;
	
	public List<Interview> findByCandidate(Long candidateId) throws ServiceException;
	
	public List<Interview> findByRequirement(Integer requirementId) throws ServiceException;
	
	public List<Interview> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date interviewedAt, Date createdAt, String documentNumber,
			String names, Integer userId, Integer first, Integer limit) throws ServiceException;
	
	public Integer findListCount(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date scheduledAt, Date interviewedAt, Date createdAt, String documentNumber,
			String names, Integer userId) throws ServiceException;

}
