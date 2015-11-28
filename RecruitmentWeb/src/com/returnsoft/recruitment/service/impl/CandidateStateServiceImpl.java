package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.CandidateStateEao;
import com.returnsoft.recruitment.entity.CandidateState;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.CandidateStateService;

/**
 * Session Bean implementation class CandidateStateBean
 */
@Stateless
public class CandidateStateServiceImpl implements CandidateStateService {
	
	@EJB
	private CandidateStateEao eao;

    /**
     * Default constructor. 
     */
    public CandidateStateServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public List<CandidateState> findAll() throws ServiceException {
		try {
			List<CandidateState> entities = eao.findAll();
			
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
    
    @Override
   	public List<CandidateState> findActiveList() throws ServiceException {
   		try {
   			List<CandidateState> entities = eao.findEnablesList();
   			
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
    
    /*@Override
   	public List<CandidateStateDto> findInterviewList() throws RecruitmentException {
   		try {
   			List<CandidateState> entities = eao.findInterviewList();
   			List<CandidateStateDto> dtos = new ArrayList<CandidateStateDto>();
   			for (CandidateState entity : entities) {
   				CandidateStateDto dto = conv.fromCandidateState(entity);
   				dtos.add(dto);
   			}
   			return dtos;
   		} catch (EaoExcepcion e) {
   			throw new RecruitmentException(e.getMessage());
   		} catch (ConversionExcepcion e) {
   			throw new RecruitmentException(e.getMessage());
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new RecruitmentException(e.getMessage());
   		}
   	}*/

	@Override
	public CandidateState findById(Integer stateId)
			throws ServiceException {
		try {
			CandidateState state = eao.findById(stateId);
			
			return state;
			
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
