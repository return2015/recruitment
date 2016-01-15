package com.returnsoft.recruitment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.recruitment.eao.AreaEao;
import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.ServiceException;
import com.returnsoft.recruitment.exception.UserNotFoundException;
import com.returnsoft.recruitment.service.AreaService;

/**
 * Session Bean implementation class DepartmentBean
 */

@Stateless
public class AreaServiceImpl implements AreaService {

	@EJB
	private AreaEao areaEao;

	@Override
	public Area findById(Integer areaId) throws ServiceException {
		try {
			Area area = areaEao.findById(areaId);
			return area;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<Area> findAreasParentAll() throws ServiceException {
		try {
			List<Area> areas = areaEao.findAreasParent();

			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}
	
	@Override
	public List<Area> findAreasParentActive() throws ServiceException {
		try {
			List<Area> areas = areaEao.findAreasParentActive();

			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<Area> findAreasChildActive(Integer areaId) throws ServiceException {
		try {

			List<Area> areas = areaEao.findAreasChildActive(areaId);
			return areas;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}
	
	@Override
	public List<Area> findAreasChildAll() throws ServiceException {
		try {

			List<Area> subAreas = areaEao.findAreasChild();
			return subAreas;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<Area> findAreasByRecruiter(Integer recruiterId) throws ServiceException {
		try {
			List<Area> areas = areaEao.findAreasByRecruiter(recruiterId);
			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<Area> findSubAreasByRecruiter(Integer areaParentId, Integer recruiterId) throws ServiceException {
		try {
			List<Area> areas = areaEao.findSubAreasByRecruiter(areaParentId, recruiterId);
			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	@Override
	public void add(Area area) throws ServiceException {
		try {

			areaEao.add(area);

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}

	}

	@Override
	public Area edit(Area area) throws ServiceException {
		try {
			Area areaFound = areaEao.findById(area.getId());

			if (areaFound == null) {
				throw new UserNotFoundException();
			}

			area = areaEao.edit(area);

			return area;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

	

	

}
