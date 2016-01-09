package com.returnsoft.recruitment.service;

import java.util.Date;
import java.util.List;

import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.exception.ServiceException;


public interface CandidateService {
	
	
	public List<Candidate> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,Integer trainingStateId,Integer ojtStateId, 
			Date scheduledAt, Date createdAt,
			String documentNumber,String name, Integer first, Integer limit) throws ServiceException;
	
	public Integer findListCount(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,Integer trainingStateId,Integer ojtStateId, 
			Date scheduledAt, Date createdAt,
			String documentNumber,String name) throws ServiceException;
	
	public void add(Candidate candidateDto) throws ServiceException;
	
	public Candidate edit(Candidate candidateDto) throws ServiceException;
	
	public Candidate findById(Long idCandidate) throws ServiceException;
	
	public Candidate findByDocumentNumber(String documentNumber) throws ServiceException;
	
	//public List<Candidate> findByDocumentNumberList(String documentNumber) throws ServiceException;
	

}
