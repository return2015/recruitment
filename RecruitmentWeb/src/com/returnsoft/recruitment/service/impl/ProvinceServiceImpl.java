package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.ProvinceEao;
import com.returnsoft.recruitment.entity.Province;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.ProvinceService;


/**
 * Session Bean implementation class ProvinceBean
 */
@Stateless
public class ProvinceServiceImpl implements ProvinceService {

	@EJB
	private ProvinceEao provinceEao;
	

	/**
	 * Default constructor.
	 */
	public ProvinceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Province> findByDepartment(Integer departmentId)
			throws ServiceException {
		try {
			List<Province> provinces = provinceEao
					.findByDepartment(departmentId);
			
			return provinces;
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
