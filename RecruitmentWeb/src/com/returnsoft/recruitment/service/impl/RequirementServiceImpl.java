package com.returnsoft.recruitment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.RequirementEao;
import com.returnsoft.recruitment.eao.RequirementUserEao;
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.RequirementDto;
import com.returnsoft.recruitment.entity.RequirementUser;
import com.returnsoft.recruitment.enumeration.MonthEnum;
import com.returnsoft.recruitment.enumeration.YearEnum;
import com.returnsoft.recruitment.exception.RequirementNotFoundException;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.RequirementService;

/**
 * Session Bean implementation class RequirementBean
 */
@Stateless
public class RequirementServiceImpl implements RequirementService {



	@EJB
	private RequirementEao requirementEao;
	
	@EJB
	private RequirementUserEao requirementUserEao;
	
	
    @Override
    public List<Requirement> findList(YearEnum periodYear, MonthEnum periodMonth,Integer areaId, Integer subAreaId, Integer recruiterId) throws ServiceException{
    	
    	try {
    		List<Requirement> requirements = requirementEao.findList(periodYear,periodMonth, areaId, subAreaId, recruiterId);
        	return requirements;	
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
    public List<RequirementDto> findListDetail(YearEnum yearEnum,MonthEnum monthEnum,Integer areaId, Integer subAreaId, Integer recruiterId){
    	
    	List<RequirementDto> requirements = requirementEao.findListDetail(yearEnum,monthEnum, areaId, subAreaId, recruiterId);
    	
    	for (int i = 0; i < requirements.size(); i++) {
    		RequirementDto requirement = requirements.get(i);
    		
    		//INDICATORS
    		Double startTrainingRequisition = getStartTrainingRequisition(requirement.getRecruiterAmount(), requirement.getAmountStartTraining());
    		System.out.println("1:"+startTrainingRequisition);
    		requirement.setStartTrainingRequisition(startTrainingRequisition);
    		
    		Double endowmentIndicator = getEndowmentIndicator(startTrainingRequisition);
    		System.out.println("2:"+endowmentIndicator);
    		requirement.setEndowmentIndicator(endowmentIndicator);
    		
    		Double endowmentIndicatorReached = getEndowmentIndicatorReached(endowmentIndicator);
    		System.out.println("3:"+endowmentIndicatorReached);
    		requirement.setEndowmentIndicatorReached(endowmentIndicatorReached);
    		
    		Double rotationAllowed = getRotationAllowed(requirement.getAmountStartTraining().doubleValue());
    		requirement.setRotationAllowed(rotationAllowed);
    		
    		Double rotationAllowedReached = getRotationAllowedReached(requirement.getAmountStartTraining().doubleValue(),rotationAllowed);
    		requirement.setRotationAllowedReached(rotationAllowedReached);
    		
    		Double rotationIndicator = getRotationIndicator(requirement.getAmountEndTraining().doubleValue(),rotationAllowedReached);
    		requirement.setRotationIndicator(rotationIndicator);
    		
    		Double rotationIndicatorReached = getRotationIndicatorReached(rotationIndicator);
    		requirement.setRotationIndicatorReached(rotationIndicatorReached);
    		
    		Double startOjtStartTraining = getStartOjtStartTraining(requirement.getAmountStartOjt().doubleValue(),requirement.getAmountStartTraining().doubleValue());
    		requirement.setStartOjtStartTraining(startOjtStartTraining);
    		
    		Double endOjtRequisition = getEndOjtRequisition(requirement.getAmountEndOjt().doubleValue(),requirement.getRecruiterAmount().doubleValue());
    		requirement.setEndOjtRequisition(endOjtRequisition);
    		
    		requirements.set(i, requirement);
		}
    	
    	return requirements;
    	
    }
    
    @Override
    public List<RequirementDto> findListSummaryByRecruiter(YearEnum periodYear, MonthEnum periodMonth,Integer areaId, Integer subAreaId, Integer recruiterId){
    	
    	System.out.println("Ingreso a findListSummaryByRecruiter");
    	
    	List<RequirementDto> requirements = requirementEao.findListByRecruiter(periodYear,periodMonth, areaId, subAreaId, recruiterId);
    	
    	System.out.println("requerimientos:"+requirements.size());
    	
    	for (int i = 0; i < requirements.size(); i++) {
    		RequirementDto requirement = requirements.get(i);
    		//System.out.println("---"+requirement.getId()+"---");
    		//System.out.println("cantidad:"+requirement.getAmount());
    		/*RequirementDto requirementDetail = requirementEao.findRequirementDetail(requirement.getStartTraining(), 
    				requirement.getEndTraining(), 
    				requirement.getStartOjt(), requirement.getArea().getId(), 
    				requirement.getRecruiter().getId());
    		requirement.setAmountStartTraining(requirementDetail.getAmountStartTraining());
    		System.out.println("inician capa:"+requirementDetail.getAmountStartTraining());
    		requirement.setAmountEndTraining(requirementDetail.getAmountEndTraining());
    		System.out.println("finalizan capa:"+requirementDetail.getAmountEndTraining());
    		requirement.setAmountStartOjt(requirementDetail.getAmountStartOjt());
    		requirement.setAmountEndOjt(requirementDetail.getAmountEndOjt());*/
    		//requirement.setAmountStartOjtLesser(requirementDetail.getAmountStartOjtLesser());
    		//requirement.setAmountStartOjtGreater(requirementDetail.getAmountStartOjtGreater());
    		
    		//INDICATORS
    		Double startTrainingRequisition = getStartTrainingRequisition(requirement.getRecruiterAmount(), requirement.getAmountStartTraining());
    		System.out.println("1:"+startTrainingRequisition);
    		requirement.setStartTrainingRequisition(startTrainingRequisition);
    		
    		Double endowmentIndicator = getEndowmentIndicator(startTrainingRequisition);
    		System.out.println("2:"+endowmentIndicator);
    		requirement.setEndowmentIndicator(endowmentIndicator);
    		
    		Double endowmentIndicatorReached = getEndowmentIndicatorReached(endowmentIndicator);
    		System.out.println("3:"+endowmentIndicatorReached);
    		requirement.setEndowmentIndicatorReached(endowmentIndicatorReached);
    		
    		Double rotationAllowed = getRotationAllowed(requirement.getAmountStartTraining().doubleValue());
    		requirement.setRotationAllowed(rotationAllowed);
    		
    		Double rotationAllowedReached = getRotationAllowedReached(requirement.getAmountStartTraining().doubleValue(),rotationAllowed);
    		requirement.setRotationAllowedReached(rotationAllowedReached);
    		
    		Double rotationIndicator = getRotationIndicator(requirement.getAmountEndTraining().doubleValue(),rotationAllowedReached);
    		requirement.setRotationIndicator(rotationIndicator);
    		
    		Double rotationIndicatorReached = getRotationIndicatorReached(rotationIndicator);
    		requirement.setRotationIndicatorReached(rotationIndicatorReached);
    		
    		Double startOjtStartTraining = getStartOjtStartTraining(requirement.getAmountStartOjt().doubleValue(),requirement.getAmountStartTraining().doubleValue());
    		requirement.setStartOjtStartTraining(startOjtStartTraining);
    		
    		Double endOjtRequisition = getEndOjtRequisition(requirement.getAmountEndOjt().doubleValue(),requirement.getRecruiterAmount().doubleValue());
    		requirement.setEndOjtRequisition(endOjtRequisition);
    		
    		
    		requirements.set(i, requirement);
		}
    	
    	/*List<RequirementDto> requirementsSummary=new ArrayList<RequirementDto>();
    	
    	for (int i = 0; i < requirements.size(); i++) {
    		RequirementDto requirement = requirements.get(i);
    		//System.out.println("recruiter detallado:"+requirement.getRecruiter().getId());
    		//BUSCA SI YA EXISTE
    		Boolean exist=Boolean.FALSE;
			for (int j = 0; j < requirementsSummary.size(); j++) {
				RequirementDto requirementSummary = requirementsSummary.get(j);
				//System.out.println("recruiter resumido :"+requirementSummary.getRecruiter().getId());
				if (requirement.getRecruiter().getId().equals(requirementSummary.getRecruiter().getId())) {
					//System.out.println("INGRESA A IGUALES");
					exist=Boolean.TRUE;
					requirementSummary.setAmount(requirementSummary.getAmount()+requirement.getAmount());
					requirementSummary.setAmountOfRequirements(requirementSummary.getAmountOfRequirements()+1);
					requirementSummary.setAmountStartTraining(requirementSummary.getAmountStartTraining()+requirement.getAmountStartTraining());
					requirementSummary.setAmountEndTraining(requirementSummary.getAmountEndTraining()+requirement.getAmountEndTraining());
					requirementSummary.setAmountStartOjt(requirementSummary.getAmountStartOjt()+requirement.getAmountStartOjt());
					requirementSummary.setAmountEndOjt(requirementSummary.getAmountEndOjt()+requirement.getAmountEndOjt());
					//requirementSummary.setAmountStartOjtLesser(requirementSummary.getAmountStartOjtLesser()+requirement.getAmountStartOjtLesser());
					//requirementSummary.setAmountStartOjtGreater(requirementSummary.getAmountStartOjtGreater()+requirement.getAmountStartOjtGreater());
					
					requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()+requirement.getStartTrainingRequisition());
					requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()+requirement.getEndowmentIndicator());
					requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()+requirement.getEndowmentIndicatorReached());
					requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()+requirement.getRotationAllowed());
					requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()+requirement.getRotationAllowedReached());
					requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()+requirement.getRotationIndicator());
					requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()+requirement.getRotationIndicatorReached());
					requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()+requirement.getStartOjtStartTraining());
					requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()+requirement.getEndOjtRequisition());
					
					requirementsSummary.set(j, requirementSummary);
				}
			}
			if (!exist) {
				//System.out.println("INGRESA A DIFERENTES");
				requirement.setAmountOfRequirements(1);
				requirementsSummary.add(requirement);
			}
		}*/
    	
    	//CALCULA PROMEDIOS
    	
    	/*for (int j = 0; j < requirementsSummary.size(); j++) {
    		RequirementDto requirementSummary = requirementsSummary.get(j);
//    		System.out.println("IDDDDDDDDDDDD"+requirementSummary.getId());
//    		System.out.println("cantidad"+requirementSummary.getAmount());
//    		System.out.println("inician:"+requirementSummary.getAmountStartTraining());
//    		System.out.println("finalizan:"+requirementSummary.getAmountEndTraining());
//    		System.out.println("FINNNNNN");
    		
    		requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()/requirementSummary.getAmountOfRequirements());
			
			requirementsSummary.set(j, requirementSummary);
		}*/
    	
    	
    	return requirements;
    	
    }
    
    
    
    
    @Override
    public List<RequirementDto> findListSummaryByAreaParent(YearEnum periodYear, MonthEnum periodMonth,Integer areaId, Integer subAreaId, Integer recruiterId){
    	
    	System.out.println("Ingreso a findListSummaryByAreaParent");
    	//OBTIENE REQUERIMIENTOS
    	List<RequirementDto> requirements = requirementEao.findListByCoordinator(periodYear,periodMonth, areaId, subAreaId, recruiterId);
    	
    	System.out.println("cantidad de requerimientos encontrados:"+requirements.size());
    	
    	//OBTIENE DETALLE DEL REQUERIMIENTOS
    	for (int i = 0; i < requirements.size(); i++) {
    		RequirementDto requirement = requirements.get(i);
    		//System.out.println("---"+requirement.getId()+"---");
    		//System.out.println("cantidad:"+requirement.getAmount());
    		/*RequirementDto requirementDetail = requirementEao.findRequirementDetail(requirement.getStartTraining(), 
    				requirement.getEndTraining(), 
    				requirement.getStartOjt(), requirement.getArea().getId(), 
    				requirement.getRecruiter().getId());
    		requirement.setAmountStartTraining(requirementDetail.getAmountStartTraining());
    		System.out.println("inician capa:"+requirementDetail.getAmountStartTraining());
    		requirement.setAmountEndTraining(requirementDetail.getAmountEndTraining());
    		System.out.println("finalizan capa:"+requirementDetail.getAmountEndTraining());
    		requirement.setAmountStartOjt(requirementDetail.getAmountStartOjt());
    		requirement.setAmountEndOjt(requirementDetail.getAmountEndOjt());*/
    		//requirement.setAmountStartOjtLesser(requirementDetail.getAmountStartOjtLesser());
    		//requirement.setAmountStartOjtGreater(requirementDetail.getAmountStartOjtGreater());
    		
    		//INDICATORS
    		Double startTrainingRequisition = getStartTrainingRequisition(requirement.getRecruiterAmount(), requirement.getAmountStartTraining());
    		System.out.println("1:"+startTrainingRequisition);
    		requirement.setStartTrainingRequisition(startTrainingRequisition);
    		
    		Double endowmentIndicator = getEndowmentIndicator(startTrainingRequisition);
    		System.out.println("2:"+endowmentIndicator);
    		requirement.setEndowmentIndicator(endowmentIndicator);
    		
    		Double endowmentIndicatorReached = getEndowmentIndicatorReached(endowmentIndicator);
    		System.out.println("3:"+endowmentIndicatorReached);
    		requirement.setEndowmentIndicatorReached(endowmentIndicatorReached);
    		
    		Double rotationAllowed = getRotationAllowed(requirement.getAmountStartTraining().doubleValue());
    		requirement.setRotationAllowed(rotationAllowed);
    		
    		Double rotationAllowedReached = getRotationAllowedReached(requirement.getAmountStartTraining().doubleValue(),rotationAllowed);
    		requirement.setRotationAllowedReached(rotationAllowedReached);
    		
    		Double rotationIndicator = getRotationIndicator(requirement.getAmountEndTraining().doubleValue(),rotationAllowedReached);
    		requirement.setRotationIndicator(rotationIndicator);
    		
    		Double rotationIndicatorReached = getRotationIndicatorReached(rotationIndicator);
    		requirement.setRotationIndicatorReached(rotationIndicatorReached);
    		
    		Double startOjtStartTraining = getStartOjtStartTraining(requirement.getAmountStartOjt().doubleValue(),requirement.getAmountStartTraining().doubleValue());
    		requirement.setStartOjtStartTraining(startOjtStartTraining);
    		
    		Double endOjtRequisition = getEndOjtRequisition(requirement.getAmountEndOjt().doubleValue(),requirement.getRecruiterAmount().doubleValue());
    		requirement.setEndOjtRequisition(endOjtRequisition);
    		
    		requirements.set(i, requirement);
		}
    	
    	//PROCESA SUMATORIA  
    	/*List<RequirementDto> requirementsSummary=new ArrayList<RequirementDto>();
    	
    	for (int i = 0; i < requirements.size(); i++) {
    		RequirementDto requirement = requirements.get(i);
    		//System.out.println("recruiter detallado:"+requirement.getRecruiter().getId());
    		//BUSCA SI YA EXISTE
    		Boolean exist=Boolean.FALSE;
			for (int j = 0; j < requirementsSummary.size(); j++) {
				RequirementDto requirementSummary = requirementsSummary.get(j);
				//System.out.println("recruiter resumido :"+requirementSummary.getRecruiter().getId());
				if (requirement.getArea().getArea().getId().equals(requirementSummary.getArea().getArea().getId())) {
					//System.out.println("INGRESA A IGUALES");
					exist=Boolean.TRUE;
					requirementSummary.setAmount(requirementSummary.getAmount()+requirement.getAmount());
					requirementSummary.setAmountOfRequirements(requirementSummary.getAmountOfRequirements()+1);
					requirementSummary.setAmountStartTraining(requirementSummary.getAmountStartTraining()+requirement.getAmountStartTraining());
					requirementSummary.setAmountEndTraining(requirementSummary.getAmountEndTraining()+requirement.getAmountEndTraining());
					requirementSummary.setAmountStartOjt(requirementSummary.getAmountStartOjt()+requirement.getAmountStartOjt());
					requirementSummary.setAmountEndOjt(requirementSummary.getAmountEndOjt()+requirement.getAmountEndOjt());
					//requirementSummary.setAmountStartOjtLesser(requirementSummary.getAmountStartOjtLesser()+requirement.getAmountStartOjtLesser());
					//requirementSummary.setAmountStartOjtGreater(requirementSummary.getAmountStartOjtGreater()+requirement.getAmountStartOjtGreater());
					
					requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()+requirement.getStartTrainingRequisition());
					requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()+requirement.getEndowmentIndicator());
					requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()+requirement.getEndowmentIndicatorReached());
					requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()+requirement.getRotationAllowed());
					requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()+requirement.getRotationAllowedReached());
					requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()+requirement.getRotationIndicator());
					requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()+requirement.getRotationIndicatorReached());
					requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()+requirement.getStartOjtStartTraining());
					requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()+requirement.getEndOjtRequisition());
					
					requirementsSummary.set(j, requirementSummary);
				}
			}
			if (!exist) {
				//System.out.println("INGRESA A DIFERENTES");
				requirement.setAmountOfRequirements(1);
				requirementsSummary.add(requirement);
			}
		}*/
    	
    	//CALCULA PROMEDIOS
    	
    	/*System.out.println("Calcula promedios:"+requirements.size());
    	
    	for (int j = 0; j < requirementsSummary.size(); j++) {
    		RequirementDto requirementSummary = requirementsSummary.get(j);
    		
    		System.out.println("cantidad"+requirementSummary.getAmount());
    		System.out.println("inician:"+requirementSummary.getAmountStartTraining());
    		System.out.println("finalizan:"+requirementSummary.getAmountEndTraining());
    		
    		System.out.println("1:"+requirementSummary.getStartTrainingRequisition());
    		requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()/requirementSummary.getAmountOfRequirements());
    		System.out.println("2:"+requirementSummary.getEndowmentIndicator());
			requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()/requirementSummary.getAmountOfRequirements());
			System.out.println("3:"+requirementSummary.getEndowmentIndicatorReached());
			requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()/requirementSummary.getAmountOfRequirements());
			requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()/requirementSummary.getAmountOfRequirements());
			
			requirementsSummary.set(j, requirementSummary);
		}*/
    	return requirements;
    	
    }
    
    
//    
//    
//    @Override
//    public List<RequirementDto> findListSummaryByRecruiter(String periodFormatted,Integer areaId, Integer subAreaId, Integer recruiterId){
//    	
//    	System.out.println("Ingreso a findListSummaryByRecruiter");
//    	
//    	List<RequirementDto> requirements = requirementEao.findList(periodFormatted, areaId, subAreaId, recruiterId);
//    	
//    	System.out.println("requerimientos:"+requirements.size());
//    	
//    	for (int i = 0; i < requirements.size(); i++) {
//    		RequirementDto requirement = requirements.get(i);
//    		System.out.println("---"+requirement.getId()+"---");
//    		System.out.println("cantidad:"+requirement.getAmount());
//    		RequirementDto requirementDetail = requirementEao.findRequirementDetail(requirement.getStartTraining(), 
//    				requirement.getEndTraining(), 
//    				requirement.getStartOjt(), requirement.getArea().getId(), 
//    				requirement.getRecruiter().getId());
//    		requirement.setAmountStartTraining(requirementDetail.getAmountStartTraining());
//    		System.out.println("inician capa:"+requirementDetail.getAmountStartTraining());
//    		requirement.setAmountEndTraining(requirementDetail.getAmountEndTraining());
//    		System.out.println("finalizan capa:"+requirementDetail.getAmountEndTraining());
//    		requirement.setAmountStartOjt(requirementDetail.getAmountStartOjt());
//    		requirement.setAmountEndOjt(requirementDetail.getAmountEndOjt());
//    		//requirement.setAmountStartOjtLesser(requirementDetail.getAmountStartOjtLesser());
//    		//requirement.setAmountStartOjtGreater(requirementDetail.getAmountStartOjtGreater());
//    		
//    		//INDICATORS
//    		Double startTrainingRequisition = getStartTrainingRequisition(requirement.getAmount(), requirementDetail.getAmountStartTraining());
//    		System.out.println("1:"+startTrainingRequisition);
//    		requirement.setStartTrainingRequisition(startTrainingRequisition);
//    		
//    		Double endowmentIndicator = getEndowmentIndicator(startTrainingRequisition);
//    		System.out.println("2:"+endowmentIndicator);
//    		requirement.setEndowmentIndicator(endowmentIndicator);
//    		
//    		Double endowmentIndicatorReached = getEndowmentIndicatorReached(endowmentIndicator);
//    		System.out.println("3:"+endowmentIndicatorReached);
//    		requirement.setEndowmentIndicatorReached(endowmentIndicatorReached);
//    		
//    		Double rotationAllowed = getRotationAllowed(requirementDetail.getAmountStartTraining().doubleValue());
//    		requirement.setRotationAllowed(rotationAllowed);
//    		
//    		Double rotationAllowedReached = getRotationAllowedReached(requirementDetail.getAmountStartTraining().doubleValue(),rotationAllowed);
//    		requirement.setRotationAllowedReached(rotationAllowedReached);
//    		
//    		Double rotationIndicator = getRotationIndicator(requirementDetail.getAmountEndTraining().doubleValue(),rotationAllowedReached);
//    		requirement.setRotationIndicator(rotationIndicator);
//    		
//    		Double rotationIndicatorReached = getRotationIndicatorReached(rotationIndicator);
//    		requirement.setRotationIndicatorReached(rotationIndicatorReached);
//    		
//    		Double startOjtStartTraining = getStartOjtStartTraining(requirementDetail.getAmountStartOjt().doubleValue(),requirementDetail.getAmountStartTraining().doubleValue());
//    		requirement.setStartOjtStartTraining(startOjtStartTraining);
//    		
//    		Double endOjtRequisition = getEndOjtRequisition(requirementDetail.getAmountEndOjt().doubleValue(),requirement.getAmount().doubleValue());
//    		requirement.setEndOjtRequisition(endOjtRequisition);
//    		
//    		
//    		requirements.set(i, requirement);
//		}
//    	
//    	List<RequirementDto> requirementsSummary=new ArrayList<RequirementDto>();
//    	
//    	for (int i = 0; i < requirements.size(); i++) {
//    		RequirementDto requirement = requirements.get(i);
//    		//System.out.println("recruiter detallado:"+requirement.getRecruiter().getId());
//    		//BUSCA SI YA EXISTE
//    		Boolean exist=Boolean.FALSE;
//			for (int j = 0; j < requirementsSummary.size(); j++) {
//				RequirementDto requirementSummary = requirementsSummary.get(j);
//				//System.out.println("recruiter resumido :"+requirementSummary.getRecruiter().getId());
//				if (requirement.getRecruiter().getId().equals(requirementSummary.getRecruiter().getId())) {
//					//System.out.println("INGRESA A IGUALES");
//					exist=Boolean.TRUE;
//					requirementSummary.setAmount(requirementSummary.getAmount()+requirement.getAmount());
//					requirementSummary.setAmountOfRequirements(requirementSummary.getAmountOfRequirements()+1);
//					requirementSummary.setAmountStartTraining(requirementSummary.getAmountStartTraining()+requirement.getAmountStartTraining());
//					requirementSummary.setAmountEndTraining(requirementSummary.getAmountEndTraining()+requirement.getAmountEndTraining());
//					requirementSummary.setAmountStartOjt(requirementSummary.getAmountStartOjt()+requirement.getAmountStartOjt());
//					requirementSummary.setAmountEndOjt(requirementSummary.getAmountEndOjt()+requirement.getAmountEndOjt());
//					//requirementSummary.setAmountStartOjtLesser(requirementSummary.getAmountStartOjtLesser()+requirement.getAmountStartOjtLesser());
//					//requirementSummary.setAmountStartOjtGreater(requirementSummary.getAmountStartOjtGreater()+requirement.getAmountStartOjtGreater());
//					
//					requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()+requirement.getStartTrainingRequisition());
//					requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()+requirement.getEndowmentIndicator());
//					requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()+requirement.getEndowmentIndicatorReached());
//					requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()+requirement.getRotationAllowed());
//					requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()+requirement.getRotationAllowedReached());
//					requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()+requirement.getRotationIndicator());
//					requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()+requirement.getRotationIndicatorReached());
//					requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()+requirement.getStartOjtStartTraining());
//					requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()+requirement.getEndOjtRequisition());
//					
//					requirementsSummary.set(j, requirementSummary);
//				}
//			}
//			if (!exist) {
//				//System.out.println("INGRESA A DIFERENTES");
//				requirement.setAmountOfRequirements(1);
//				requirementsSummary.add(requirement);
//			}
//		}
//    	
//    	//CALCULA PROMEDIOS
//    	
//    	for (int j = 0; j < requirementsSummary.size(); j++) {
//    		RequirementDto requirementSummary = requirementsSummary.get(j);
//    		/*System.out.println("IDDDDDDDDDDDD"+requirementSummary.getId());
//    		System.out.println("cantidad"+requirementSummary.getAmount());
//    		System.out.println("inician:"+requirementSummary.getAmountStartTraining());
//    		System.out.println("finalizan:"+requirementSummary.getAmountEndTraining());
//    		System.out.println("FINNNNNN");*/
//    		
//    		requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()/requirementSummary.getAmountOfRequirements());
//			
//			requirementsSummary.set(j, requirementSummary);
//		}
//    	
//    	
//    	return requirementsSummary;
//    	
//    }
//    
//    
//    
//    
//    @Override
//    public List<RequirementDto> findListSummaryByAreaParent(String periodFormatted,Integer areaId, Integer subAreaId, Integer recruiterId){
//    	
//    	System.out.println("Ingreso a findListSummaryByAreaParent");
//    	//OBTIENE REQUERIMIENTOS
//    	List<RequirementDto> requirements = requirementEao.findList(periodFormatted, areaId, subAreaId, recruiterId);
//    	
//    	System.out.println("cantidad de requerimientos encontrados:"+requirements.size());
//    	
//    	//OBTIENE DETALLE DEL REQUERIMIENTOS
//    	for (int i = 0; i < requirements.size(); i++) {
//    		RequirementDto requirement = requirements.get(i);
//    		System.out.println("---"+requirement.getId()+"---");
//    		System.out.println("cantidad:"+requirement.getAmount());
//    		RequirementDto requirementDetail = requirementEao.findRequirementDetail(requirement.getStartTraining(), 
//    				requirement.getEndTraining(), 
//    				requirement.getStartOjt(), requirement.getArea().getId(), 
//    				requirement.getRecruiter().getId());
//    		requirement.setAmountStartTraining(requirementDetail.getAmountStartTraining());
//    		System.out.println("inician capa:"+requirementDetail.getAmountStartTraining());
//    		requirement.setAmountEndTraining(requirementDetail.getAmountEndTraining());
//    		System.out.println("finalizan capa:"+requirementDetail.getAmountEndTraining());
//    		requirement.setAmountStartOjt(requirementDetail.getAmountStartOjt());
//    		requirement.setAmountEndOjt(requirementDetail.getAmountEndOjt());
//    		//requirement.setAmountStartOjtLesser(requirementDetail.getAmountStartOjtLesser());
//    		//requirement.setAmountStartOjtGreater(requirementDetail.getAmountStartOjtGreater());
//    		
//    		//INDICATORS
//    		Double startTrainingRequisition = getStartTrainingRequisition(requirement.getAmount(), requirementDetail.getAmountStartTraining());
//    		System.out.println("1:"+startTrainingRequisition);
//    		requirement.setStartTrainingRequisition(startTrainingRequisition);
//    		
//    		Double endowmentIndicator = getEndowmentIndicator(startTrainingRequisition);
//    		System.out.println("2:"+endowmentIndicator);
//    		requirement.setEndowmentIndicator(endowmentIndicator);
//    		
//    		Double endowmentIndicatorReached = getEndowmentIndicatorReached(endowmentIndicator);
//    		System.out.println("3:"+endowmentIndicatorReached);
//    		requirement.setEndowmentIndicatorReached(endowmentIndicatorReached);
//    		
//    		Double rotationAllowed = getRotationAllowed(requirementDetail.getAmountStartTraining().doubleValue());
//    		requirement.setRotationAllowed(rotationAllowed);
//    		
//    		Double rotationAllowedReached = getRotationAllowedReached(requirementDetail.getAmountStartTraining().doubleValue(),rotationAllowed);
//    		requirement.setRotationAllowedReached(rotationAllowedReached);
//    		
//    		Double rotationIndicator = getRotationIndicator(requirementDetail.getAmountEndTraining().doubleValue(),rotationAllowedReached);
//    		requirement.setRotationIndicator(rotationIndicator);
//    		
//    		Double rotationIndicatorReached = getRotationIndicatorReached(rotationIndicator);
//    		requirement.setRotationIndicatorReached(rotationIndicatorReached);
//    		
//    		Double startOjtStartTraining = getStartOjtStartTraining(requirementDetail.getAmountStartOjt().doubleValue(),requirementDetail.getAmountStartTraining().doubleValue());
//    		requirement.setStartOjtStartTraining(startOjtStartTraining);
//    		
//    		Double endOjtRequisition = getEndOjtRequisition(requirementDetail.getAmountEndOjt().doubleValue(),requirement.getAmount().doubleValue());
//    		requirement.setEndOjtRequisition(endOjtRequisition);
//    		
//    		requirements.set(i, requirement);
//		}
//    	
//    	//PROCESA SUMATORIA  
//    	List<RequirementDto> requirementsSummary=new ArrayList<RequirementDto>();
//    	
//    	for (int i = 0; i < requirements.size(); i++) {
//    		RequirementDto requirement = requirements.get(i);
//    		//System.out.println("recruiter detallado:"+requirement.getRecruiter().getId());
//    		//BUSCA SI YA EXISTE
//    		Boolean exist=Boolean.FALSE;
//			for (int j = 0; j < requirementsSummary.size(); j++) {
//				RequirementDto requirementSummary = requirementsSummary.get(j);
//				//System.out.println("recruiter resumido :"+requirementSummary.getRecruiter().getId());
//				if (requirement.getArea().getArea().getId().equals(requirementSummary.getArea().getArea().getId())) {
//					//System.out.println("INGRESA A IGUALES");
//					exist=Boolean.TRUE;
//					requirementSummary.setAmount(requirementSummary.getAmount()+requirement.getAmount());
//					requirementSummary.setAmountOfRequirements(requirementSummary.getAmountOfRequirements()+1);
//					requirementSummary.setAmountStartTraining(requirementSummary.getAmountStartTraining()+requirement.getAmountStartTraining());
//					requirementSummary.setAmountEndTraining(requirementSummary.getAmountEndTraining()+requirement.getAmountEndTraining());
//					requirementSummary.setAmountStartOjt(requirementSummary.getAmountStartOjt()+requirement.getAmountStartOjt());
//					requirementSummary.setAmountEndOjt(requirementSummary.getAmountEndOjt()+requirement.getAmountEndOjt());
//					//requirementSummary.setAmountStartOjtLesser(requirementSummary.getAmountStartOjtLesser()+requirement.getAmountStartOjtLesser());
//					//requirementSummary.setAmountStartOjtGreater(requirementSummary.getAmountStartOjtGreater()+requirement.getAmountStartOjtGreater());
//					
//					requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()+requirement.getStartTrainingRequisition());
//					requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()+requirement.getEndowmentIndicator());
//					requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()+requirement.getEndowmentIndicatorReached());
//					requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()+requirement.getRotationAllowed());
//					requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()+requirement.getRotationAllowedReached());
//					requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()+requirement.getRotationIndicator());
//					requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()+requirement.getRotationIndicatorReached());
//					requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()+requirement.getStartOjtStartTraining());
//					requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()+requirement.getEndOjtRequisition());
//					
//					requirementsSummary.set(j, requirementSummary);
//				}
//			}
//			if (!exist) {
//				//System.out.println("INGRESA A DIFERENTES");
//				requirement.setAmountOfRequirements(1);
//				requirementsSummary.add(requirement);
//			}
//		}
//    	
//    	//CALCULA PROMEDIOS
//    	
//    	System.out.println("Calcula promedios:"+requirements.size());
//    	
//    	for (int j = 0; j < requirementsSummary.size(); j++) {
//    		RequirementDto requirementSummary = requirementsSummary.get(j);
//    		
//    		System.out.println("cantidad"+requirementSummary.getAmount());
//    		System.out.println("inician:"+requirementSummary.getAmountStartTraining());
//    		System.out.println("finalizan:"+requirementSummary.getAmountEndTraining());
//    		
//    		System.out.println("1:"+requirementSummary.getStartTrainingRequisition());
//    		requirementSummary.setStartTrainingRequisition(requirementSummary.getStartTrainingRequisition()/requirementSummary.getAmountOfRequirements());
//    		System.out.println("2:"+requirementSummary.getEndowmentIndicator());
//			requirementSummary.setEndowmentIndicator(requirementSummary.getEndowmentIndicator()/requirementSummary.getAmountOfRequirements());
//			System.out.println("3:"+requirementSummary.getEndowmentIndicatorReached());
//			requirementSummary.setEndowmentIndicatorReached(requirementSummary.getEndowmentIndicatorReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationAllowed(requirementSummary.getRotationAllowed()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationAllowedReached(requirementSummary.getRotationAllowedReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationIndicator(requirementSummary.getRotationIndicator()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setRotationIndicatorReached(requirementSummary.getRotationIndicatorReached()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setStartOjtStartTraining(requirementSummary.getStartOjtStartTraining()/requirementSummary.getAmountOfRequirements());
//			requirementSummary.setEndOjtRequisition(requirementSummary.getEndOjtRequisition()/requirementSummary.getAmountOfRequirements());
//			
//			requirementsSummary.set(j, requirementSummary);
//		}
//    	return requirementsSummary;
//    	
//    }
//    
    
    
    @Override
	public void add(Requirement requirement) throws ServiceException {
		try {
			
			List<RequirementUser> recruiters = requirement.getUsers();
			requirement.setUsers(null);
			requirementEao.add(requirement);
			
			for (RequirementUser requirementUser : recruiters) {
				requirementUser.setRequirement(requirement);
				requirementUser.setRequirementId(requirement.getId());
				requirementUserEao.add(requirementUser);
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
	public Requirement findById(Integer requirementId)
			throws ServiceException {
		try {
			
			Requirement requirement = requirementEao.findById(requirementId);
			
			return requirement;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
    
    
    
    //////////////////////////////////////////////////////
    ////////////////////// INDICATORS ////////////////////
    //////////////////////////////////////////////////////
    
    public Double getStartTrainingRequisition(Integer amount, Integer amountStartTraining) {
		//System.out.println("Ingreso a getStartTrainingRequisition");
		//System.out.println(amountStartTraining);
		//System.out.println(amount);
		Double startTrainingRequisition=0.0; 
		startTrainingRequisition=amountStartTraining.doubleValue()/amount.doubleValue();
		//System.out.println(startTrainingRequisition);
		return startTrainingRequisition;
	}
	public Double getEndowmentIndicator(Double startTrainingRequisition ) {
		//System.out.println("Ingreso a getEndowmentIndicator");
		Double endowmentIndicator= 0.0; 
		endowmentIndicator=(startTrainingRequisition*100)/180;
		//System.out.println(endowmentIndicator);
		return endowmentIndicator;
	}
	public Double getEndowmentIndicatorReached(Double endowmentIndicator) {
		//System.out.println("Ingreso a getEndowmentIndicatorReached");
		Double endowmentIndicatorReached=0.0;
		endowmentIndicatorReached=endowmentIndicator*0.7;
		//System.out.println(endowmentIndicatorReached);
		return endowmentIndicatorReached;
	}
	public Double getRotationAllowed(Double amountStartTraining) {
		Double rotationAllowed=0.0;
		rotationAllowed=amountStartTraining.doubleValue()*0.3;
		return rotationAllowed;
	}
	public Double getRotationAllowedReached(Double amountStartTraining,Double rotationAllowed ) {
		Double rotationAllowedReached=0.0; 
		rotationAllowedReached=amountStartTraining-rotationAllowed;
		return rotationAllowedReached;
	}
	public Double getRotationIndicator(Double amountEndTraining, Double rotationAllowedReached) {
		Double rotationIndicator = 0.0; 
		rotationIndicator=amountEndTraining/rotationAllowedReached;
		if (rotationIndicator.isNaN()) {
			rotationIndicator=0.0;
		}
		return rotationIndicator;
	}
	public Double getRotationIndicatorReached(Double rotationIndicator) {
		Double rotationIndicatorReached=0.0; 
		rotationIndicatorReached=rotationIndicator*0.1;
		return rotationIndicatorReached;
	}
	public Double getStartOjtStartTraining(Double amountStartOjt, Double amountStartTraining) {
		Double startOjtStartTraining=0.0; 
		startOjtStartTraining=amountStartOjt/amountStartTraining;
		if (startOjtStartTraining.isNaN()) {
			startOjtStartTraining=0.0;
		}
		return startOjtStartTraining;
	}
	public Double getEndOjtRequisition(Double amountEndOjt,Double amount) {
		Double endOjtRequisition=0.0; 
		endOjtRequisition=amountEndOjt/amount;
		return endOjtRequisition;
	}

	@Override
	public Requirement edit(Requirement requirement) throws ServiceException {
		
		try {
			
			Requirement requirementFound = requirementEao.findById(requirement.getId());
			
			if (requirementFound==null) {
				throw new RequirementNotFoundException();
			}
			
			
			requirement = requirementEao.edit(requirement);
			
			return requirement;
			
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
	public List<Requirement> findByRecruiter(Integer recruiterId) throws ServiceException {
		try {
			List<Requirement> requirements = requirementEao.findByRecruiter(recruiterId);

	    	return requirements;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	
	////////////////////////////////////////////////////////
	

}
