/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service;


/**
 * <a href="OrgLaborLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link OrgLaborLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborLocalService
 * @generated
 */
public class OrgLaborLocalServiceWrapper implements OrgLaborLocalService {
	public OrgLaborLocalServiceWrapper(
		OrgLaborLocalService orgLaborLocalService) {
		_orgLaborLocalService = orgLaborLocalService;
	}

	public com.liferay.portal.model.OrgLabor addOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.addOrgLabor(orgLabor);
	}

	public com.liferay.portal.model.OrgLabor createOrgLabor(long orgLaborId) {
		return _orgLaborLocalService.createOrgLabor(orgLaborId);
	}

	public void deleteOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_orgLaborLocalService.deleteOrgLabor(orgLaborId);
	}

	public void deleteOrgLabor(com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.kernel.exception.SystemException {
		_orgLaborLocalService.deleteOrgLabor(orgLabor);
	}

	public java.util.List<com.liferay.portal.model.OrgLabor> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.OrgLabor> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.OrgLabor> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.OrgLabor getOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.getOrgLabor(orgLaborId);
	}

	public java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.getOrgLabors(start, end);
	}

	public int getOrgLaborsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.getOrgLaborsCount();
	}

	public com.liferay.portal.model.OrgLabor updateOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.updateOrgLabor(orgLabor);
	}

	public com.liferay.portal.model.OrgLabor updateOrgLabor(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.updateOrgLabor(orgLabor, merge);
	}

	public com.liferay.portal.model.OrgLabor addOrgLabor(long organizationId,
		int typeId, int sunOpen, int sunClose, int monOpen, int monClose,
		int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen,
		int thuClose, int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.addOrgLabor(organizationId, typeId,
			sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
			wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose);
	}

	public java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.getOrgLabors(organizationId);
	}

	public com.liferay.portal.model.OrgLabor updateOrgLabor(long orgLaborId,
		int typeId, int sunOpen, int sunClose, int monOpen, int monClose,
		int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen,
		int thuClose, int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _orgLaborLocalService.updateOrgLabor(orgLaborId, typeId,
			sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen,
			wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose);
	}

	public OrgLaborLocalService getWrappedOrgLaborLocalService() {
		return _orgLaborLocalService;
	}

	private OrgLaborLocalService _orgLaborLocalService;
}