package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.exception.ServiceException;


public interface CandidateService {
	
	
	public List<Candidate> findList(List<Integer> areasId,
			List<Integer> subAreasId, Integer recruiterId, Integer interviewStateId,Integer trainingStateId,Integer ojtStateId, 
			String scheduledAtFormatted, String createdAtFormatted,
			String documentNumber,String name) throws ServiceException;
	
	public Candidate add(Candidate candidateDto) throws ServiceException;
	
	public Candidate edit(Candidate candidateDto) throws ServiceException;
	
	public Candidate findById(Integer idCandidate) throws ServiceException;
	
	public Candidate findByDocumentNumber(String documentNumber) throws ServiceException;
	
	public List<Candidate> findByDocumentNumberList(String documentNumber) throws ServiceException;
	

}
