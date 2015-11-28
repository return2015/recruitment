package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.AreaEao;
import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.service.AreaService;


/**
 * Session Bean implementation class DepartmentBean
 */

@Stateless
public class AreaServiceImpl implements AreaService {

	@EJB
	private AreaEao areaEao;

	@Override
	public List<Area> findAreasParent() throws ServiceException {
		try {
			List<Area> areas = areaEao.findAreasParent();
			
			return areas;
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
	public List<Area> findAreasChild(Integer areaId)
			throws ServiceException {
		try {
			
			List<Area> areas = areaEao.findAreasChild(areaId);
			return areas;
			
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
	public List<Area> findAreasByRecruiter(Integer recruiterId)
			throws ServiceException {
		try {
			List<Area> areas = areaEao.findAreasByRecruiter(recruiterId);
			return areas;
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
	public List<Area> findSubAreasByRecruiter(Integer areaParentId, Integer recruiterId)
			throws ServiceException {
		try {
			List<Area> areas = areaEao.findSubAreasByRecruiter(areaParentId,recruiterId);
			return areas;
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
