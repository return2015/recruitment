package com.returnsoft.recruitment.util;

import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.CandidateService;

//@Singleton
public class CandidateLazyModel extends LazyDataModel<Candidate>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5095693738556548196L;
	
	private CandidateService candidateService;
	
	private List<Integer> areasId;
	private List<Integer> subAreasId;
	private Integer interviewStateId;
	private Integer trainingStateId;
	private Integer ojtStateId; 
	private Date scheduledAt;
	private Date createdAt; 
	private String documentNumber;
	private String name;
	  
	
	//DATOS DE VENTA
	/*private Date dateOfSaleStarted;
	private Date dateOfSaleEnded;
	private Short bankId;
	private Short productId;
	private SaleStateEnum saleState;*/
	
	//DATOS PERSONALES DE CONTRATANTE
	/*sales = saleService.findSalesByNamesContractor(nuicContractorLong, firstnameContractor,
	lastnamePaternalContractor, lastnameMaternalContractor);*/
	/*private String searchType;
	private String personType;
	private Long nuic;
	private String firstname;
	private String lastnamePaternal;
	private String lastnameMaternal;*/
	
	/*private Long number;
	
	private String SALESDATA="searchData";
	private String PERSONALDATA="personalData";
	private String DNI="dni";
	private String CREDITCARD="creditCard";
	private String NOTIFICATIONDATA="notificationData";*/
	
	///////////////
	
	/*private Date sendingDate;
	private List<NotificationStateEnum> notificationStates;
	private NotificationTypeEnum notificationType; 
	private Boolean withoutMail; 
	private Boolean withoutAddress;
	private Boolean withoutNotification;*/
	
	public CandidateLazyModel(CandidateService candidateService, List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Integer trainingStateId,Integer ojtStateId, 
			Date scheduledAt, Date createdAt, String documentNumber,
			String name) {
		super();
		
		this.candidateService=candidateService;
		
		 this.areasId=areasId;
		 this.subAreasId=subAreasId;
		 this.interviewStateId=interviewStateId;
		 this.trainingStateId=trainingStateId;
		 this.ojtStateId=ojtStateId; 
		 this.scheduledAt=scheduledAt;
		 this.createdAt=createdAt; 
		 this.documentNumber=documentNumber;
		 this.name=name;
		
	}
	
	
	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return super.getPageSize();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return super.getRowCount();
	}

	@Override
	public Candidate getRowData() {
		// TODO Auto-generated method stub
		return super.getRowData();
	}

	@Override
	public Candidate getRowData(String rowKey) {
		//System.out.println("ingreso a getRowData"+rowKey);
		Long candidateId = Long.parseLong(rowKey);
		List<Candidate> list = (List<Candidate>) getWrappedData();
		//System.out.println("tamano:"+list.size());
		for (Candidate candidate : list) {
			//System.out.println("candidate.getId():"+candidate.getId());
			//System.out.println("candidateId():"+candidateId);
			if (candidate.getId().equals(candidateId)) {
				//System.out.println("ingreso!");
				return candidate;
			}
		}
		//System.out.println("return null...");
		return null;
		//return super.getRowData(rowKey);
	}

	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return super.getRowIndex();
	}

	@Override
	public Object getRowKey(Candidate object) {
		// TODO Auto-generated method stub
		//return super.getRowKey(object);
		return object.getId();
	}

	@Override
	public Object getWrappedData() {
		// TODO Auto-generated method stub
		return super.getWrappedData();
	}

	@Override
	public boolean isRowAvailable() {
		// TODO Auto-generated method stub
		return super.isRowAvailable();
	}

	@Override
	public List<Candidate> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		System.out.println("ingreso a load1");
		return new ArrayList<Candidate>();
	}

	@Override
	public List<Candidate> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		// TODO Auto-generated method stub
		System.out.println("ingreso a load2");
		List<Candidate> candidates = null;
		//System.out.println("first"+first);
		//System.out.println("pageSize"+pageSize);
		
		
		try {
			
				
				/*sales = saleService.findSalesBySaleDataLimit(dateOfSaleStarted, dateOfSaleEnded, bankId,
						productId, saleState, first, pageSize);*/
				
				candidates = candidateService.findListLimit(areasId,subAreasId,
						  interviewStateId, trainingStateId, ojtStateId,
						  scheduledAt, createdAt,
						  documentNumber, name, first, pageSize);
				
				
				Integer candidatesCount = candidateService.findListCount(areasId,subAreasId,
						  interviewStateId, trainingStateId, ojtStateId,
						  scheduledAt, createdAt,
						  documentNumber, name);
				
				this.setRowCount(candidatesCount);
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return candidates;
	}

	@Override
	public void setPageSize(int pageSize) {
		// TODO Auto-generated method stub
		super.setPageSize(pageSize);
	}

	@Override
	public void setRowCount(int rowCount) {
		// TODO Auto-generated method stub
		super.setRowCount(rowCount);
	}

	@Override
	public void setRowIndex(int arg0) {
		// TODO Auto-generated method stub
		super.setRowIndex(arg0);
	}

	@Override
	public void setWrappedData(Object list) {
		// TODO Auto-generated method stub
		super.setWrappedData(list);
	}

	
	
}
