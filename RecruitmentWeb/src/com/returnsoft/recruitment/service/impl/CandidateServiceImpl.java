package com.returnsoft.recruitment.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.CandidateEao;
import com.returnsoft.recruitment.eao.InterviewEao;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.CandidateService;


/**
 * Session Bean implementation class CandidateBean
 */
@Stateless
public class CandidateServiceImpl implements CandidateService {

	@EJB
	private CandidateEao candidateEao;
	
	@EJB
	private InterviewEao interviewEao;

	/**
	 * Default constructor.
	 */
	public CandidateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Candidate> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Integer trainingStateId,Integer ojtStateId, 
			Date scheduledAt, Date createdAt, String documentNumber,
			String name, Integer first, Integer limit) throws ServiceException {
		try {

			List<Candidate> candidates = candidateEao.findListLimit(
					areasId, subAreasId, interviewStateId,trainingStateId,ojtStateId,
					scheduledAt,createdAt, documentNumber, name, first, limit);

			return candidates;
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
			Integer trainingStateId,Integer ojtStateId, 
			Date scheduledAt, Date createdAt, String documentNumber,
			String name) throws ServiceException {
		try {

			Integer candidatesCount = candidateEao.findListCount(
					areasId, subAreasId, interviewStateId,trainingStateId,ojtStateId,
					scheduledAt,createdAt, documentNumber, name);

			return candidatesCount;
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
	public void add(Candidate candidate)
			throws ServiceException {
		try {
			//System.out.println();
			Candidate candidateFound = candidateEao.findByDocumentNumber(candidate.getDocumentNumber());
			
			if (candidateFound==null) {
				//se agrega entrevista
//				if (candidate.getInterview()!=null) {
//					// SE CREA CANDIDATO
//					System.out.println("Se crea el candidato");
//					Interview interview = candidate.getInterview();
//					candidate.setInterview(null);
//					candidateEao.add(candidate);
//					
//					//SE CREA ENTREVISTA
//					System.out.println("Se crea la entrevista");
//					interview.setCandidate(candidate);
//					interviewEao.add(interview);
//					
//					
//					//SEASIGNA ENTREVISTA A CANDIDATO
//					System.out.println("Se asigna entrevista");
//					candidate.setInterview(interview);
//					candidateEao.update(candidate);
					
					
					
				//}else{
					System.out.println("Solo se crea el candidato");
					candidateEao.add(candidate);
				//}
				
			}else{
				throw new ServiceException("El postulante con DNI "+candidate.getDocumentNumber()+" ya existe.",new ServiceException());
			}
			
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
	public Candidate edit(Candidate newCandidate)
			throws ServiceException {
		try {
			
			if (newCandidate!=null && newCandidate.getId()>0) {
				//System.out.println();
				Candidate candidateSource = candidateEao.findById(newCandidate.getId());
				
				Candidate candidateTarget = candidateEao.findByDocumentNumber(newCandidate.getDocumentNumber());
				
				//VALIDA SI ES EL MISMO DNI
				if (candidateSource.getDocumentNumber().equals(newCandidate.getDocumentNumber())) {
					//EDITAR
					//Candidate createdCandidate = conv.toCandidate(newCandidate);
					newCandidate = candidateEao.update(newCandidate);
					/*CandidateDto createdCandidateDto = conv
							.fromCandidate(createdCandidate);*/
					return newCandidate;
				}else{
					//VALIDA SI EL NUEVO DNI ESTA DISPONIBLE
					if (candidateTarget==null || candidateTarget.getId()==0) {
						//EDITAR
						//Candidate createdCandidate = conv.toCandidate(newCandidate);
						newCandidate = candidateEao.update(newCandidate);
						/*CandidateDto createdCandidateDto = conv
								.fromCandidate(createdCandidate);*/
						return newCandidate;
					}else{
						//mensaje: ya existe el postulante
						throw new ServiceException("El postulante con DNI "+newCandidate.getDocumentNumber()+" ya existe.",new ServiceException());
					}
				}
			}else{
				throw new ServiceException("El ID del postulante es 0.",new ServiceException());
			}
			
			
			//List<CandidateDto> candidateFound = candidateEao.findByDocumentNumber(newCandidate.getDocumentNumber());
			
			
			
			/*if (candidateFound==null || candidateFound.size()==0) {
				Candidate createdCandidate = conv.toCandidate(newCandidate);
				createdCandidate = candidateEao.add(createdCandidate);
				CandidateDto createdCandidateDto = conv
						.fromCandidate(createdCandidate);
				return createdCandidateDto;
			}else{
				
				for (CandidateDto candidateDto : candidateFound) {
					System.out.println(candidateDto.getArea().getName());
					System.out.println(candidateDto.getArea().getArea().getName());
				}
				
				throw new RecruitmentException("El postulante con DNI "+newCandidate.getDocumentNumber()+" ya existe en el área "+candidateFound.get(0).getArea().getArea().getName());
			}*/
			
			

			

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
	public Candidate findById(Long candidateId)
			throws ServiceException {
		try {
			Candidate candidate = candidateEao.findById(candidateId);
			return candidate;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}

	/*@Override
	public List<Candidate> findByDocumentNumberList(String documentNumber)
			throws ServiceException {
		try {
			List<Candidate> candidatesDto = candidateEao
					.findByDocumentNumberList(documentNumber);
			
			return candidatesDto;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}*/
	
	@Override
	public Candidate findByDocumentNumber(String documentNumber)
			throws ServiceException {
		try {
			Candidate candidateDto = candidateEao
					.findByDocumentNumber(documentNumber);
			
			return candidateDto;
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
