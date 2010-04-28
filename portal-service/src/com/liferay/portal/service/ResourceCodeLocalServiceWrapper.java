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
 * <a href="ResourceCodeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceCodeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodeLocalService
 * @generated
 */
public class ResourceCodeLocalServiceWrapper implements ResourceCodeLocalService {
	public ResourceCodeLocalServiceWrapper(
		ResourceCodeLocalService resourceCodeLocalService) {
		_resourceCodeLocalService = resourceCodeLocalService;
	}

	public com.liferay.portal.model.ResourceCode addResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.addResourceCode(resourceCode);
	}

	public com.liferay.portal.model.ResourceCode createResourceCode(long codeId) {
		return _resourceCodeLocalService.createResourceCode(codeId);
	}

	public void deleteResourceCode(long codeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourceCodeLocalService.deleteResourceCode(codeId);
	}

	public void deleteResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceCodeLocalService.deleteResourceCode(resourceCode);
	}

	public java.util.List<com.liferay.portal.model.ResourceCode> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portal.model.ResourceCode> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<com.liferay.portal.model.ResourceCode> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ResourceCode getResourceCode(long codeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.getResourceCode(codeId);
	}

	public java.util.List<com.liferay.portal.model.ResourceCode> getResourceCodes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.getResourceCodes(start, end);
	}

	public int getResourceCodesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.getResourceCodesCount();
	}

	public com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.updateResourceCode(resourceCode);
	}

	public com.liferay.portal.model.ResourceCode updateResourceCode(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.updateResourceCode(resourceCode, merge);
	}

	public com.liferay.portal.model.ResourceCode addResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.addResourceCode(companyId, name, scope);
	}

	public void checkResourceCodes()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceCodeLocalService.checkResourceCodes();
	}

	public void checkResourceCodes(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceCodeLocalService.checkResourceCodes(companyId, name);
	}

	public com.liferay.portal.model.ResourceCode getResourceCode(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourceCodeLocalService.getResourceCode(companyId, name, scope);
	}

	public ResourceCodeLocalService getWrappedResourceCodeLocalService() {
		return _resourceCodeLocalService;
	}

	private ResourceCodeLocalService _resourceCodeLocalService;
}