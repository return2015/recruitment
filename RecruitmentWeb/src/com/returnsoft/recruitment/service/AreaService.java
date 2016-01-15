package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.ServiceException;


public interface AreaService {
	
	public void add(Area area) throws ServiceException;
	
	public Area edit(Area area) throws ServiceException;
	
	public Area findById(Integer areaId) throws ServiceException;
	
	public List<Area> findAreasParentAll() throws ServiceException;
	public List<Area> findAreasParentActive() throws ServiceException;
	
	public List<Area> findAreasChildActive(Integer areaId) throws ServiceException;
	public List<Area> findAreasChildAll() throws ServiceException;
	
	public List<Area> findAreasByRecruiter(Integer recruiterId) throws ServiceException;
	public List<Area> findSubAreasByRecruiter(Integer areaId,Integer recruiterId) throws ServiceException;
	
	//public List<DepartmentDto> findByUser(Integer userId) throws CustomException;

}
