package com.returnsoft.recruitment.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.InterviewService;

public class InterviewLazyModel extends LazyDataModel<Interview> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6519938140881699119L;

	private InterviewService interviewService;

	// INTERVIEW DATA
	private List<Integer> areasId;
	private List<Integer> subAreasId;
	private Integer interviewStateId;

	private Date scheduledAt;
	private Date createdAt;
	private Date interviewedAt;
	private Integer userId;
	// PERSONAL DATA
	private String documentNumber;
	private String names;
	

	public InterviewLazyModel(InterviewService interviewService, List<Integer> areasId, List<Integer> subAreasId,
			Integer interviewStateId, Date interviewedAt, Date scheduledAt, Date createdAt, String documentNumber, String names, Integer userId) {
		super();

		this.interviewService = interviewService;
		this.areasId = areasId;
		this.subAreasId = subAreasId;
		this.interviewStateId = interviewStateId;
		this.scheduledAt = scheduledAt;
		this.createdAt = createdAt;
		this.interviewedAt = interviewedAt;
		this.userId = userId;

		this.documentNumber = documentNumber;
		this.names = names;

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
	public Interview getRowData() {
		// TODO Auto-generated method stub
		return super.getRowData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Interview getRowData(String rowKey) {
		Long interviewId = Long.parseLong(rowKey);
		List<Interview> list = (List<Interview>) getWrappedData();
		for (Interview interview : list) {
			if (interview.getId().equals(interviewId)) {
				return interview;
			}
		}
		return null;
	}

	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return super.getRowIndex();
	}

	@Override
	public Object getRowKey(Interview object) {
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
	public List<Interview> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		// TODO Auto-generated method stub
		System.out.println("ingreso a load1");
		return new ArrayList<Interview>();
	}

	@Override
	public List<Interview> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		// TODO Auto-generated method stub
		System.out.println("ingreso a load2");
		List<Interview> interviews = null;
		// System.out.println("first"+first);
		// System.out.println("pageSize"+pageSize);

		try {

			/*
			 * sales = saleService.findSalesBySaleDataLimit(dateOfSaleStarted,
			 * dateOfSaleEnded, bankId, productId, saleState, first, pageSize);
			 */

			interviews = interviewService.findListLimit(areasId, subAreasId, interviewStateId,
					interviewedAt, scheduledAt, createdAt, documentNumber, names,userId, first, pageSize);

			Integer interviewsCount = interviewService.findListCount(areasId, subAreasId, interviewStateId,
					interviewedAt, scheduledAt, createdAt, documentNumber, names, userId);

			this.setRowCount(interviewsCount);

		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return interviews;
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
