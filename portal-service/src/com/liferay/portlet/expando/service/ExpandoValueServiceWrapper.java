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

package com.liferay.portlet.expando.service;


/**
 * <a href="ExpandoValueServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoValueService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoValueService
 * @generated
 */
public class ExpandoValueServiceWrapper implements ExpandoValueService {
	public ExpandoValueServiceWrapper(ExpandoValueService expandoValueService) {
		_expandoValueService = expandoValueService;
	}

	public com.liferay.portlet.expando.model.ExpandoValue addValue(
		long companyId, java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.Object data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueService.addValue(companyId, className, tableName,
			columnName, classPK, data);
	}

	public java.io.Serializable getData(long companyId,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValueService.getData(companyId, className, tableName,
			columnName, classPK);
	}

	public ExpandoValueService getWrappedExpandoValueService() {
		return _expandoValueService;
	}

	private ExpandoValueService _expandoValueService;
}