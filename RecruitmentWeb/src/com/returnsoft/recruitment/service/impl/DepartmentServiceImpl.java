package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.DepartmentEao;
import com.returnsoft.recruitment.entity.Department;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.DepartmentService;


/**
 * Session Bean implementation class DepartmentBean
 */
@Stateless
public class DepartmentServiceImpl implements DepartmentService {

	@EJB
	private DepartmentEao departmentEao;
	

	/**
	 * Default constructor.
	 */
	public DepartmentServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Department> findByCountry(Integer countryId)
			throws ServiceException {
		try {
			List<Department> departments = departmentEao
					.findByCountry(countryId);
			
			return departments;
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
