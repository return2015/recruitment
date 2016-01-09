package com.returnsoft.recruitment.service;

import java.util.List;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.ServiceException;


public interface AreaService {
	
	public Area findById(Integer areaId) throws ServiceException;
	
	public List<Area> findAreasParent() throws ServiceException;
	
	public List<Area> findAreasChild(Integer areaId) throws ServiceException;
	
	public List<Area> findAreasByRecruiter(Integer recruiterId) throws ServiceException;
	
	public List<Area> findSubAreasByRecruiter(Integer areaId,Integer recruiterId) throws ServiceException;
	
	//public List<DepartmentDto> findByUser(Integer userId) throws CustomException;

}
