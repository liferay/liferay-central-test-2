/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service;

/**
 * <p>
 * This class is a wrapper for {@link DDMListService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMListService
 * @generated
 */
public class DDMListServiceWrapper implements DDMListService {
	public DDMListServiceWrapper(DDMListService ddmListService) {
		_ddmListService = ddmListService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList addList(
		java.lang.String listKey, boolean autoListKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListService.addList(listKey, autoListKey, nameMap,
			description, structureId, serviceContext);
	}

	public void deleteList(long listId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmListService.deleteList(listId);
	}

	public void deleteList(long groupId, java.lang.String listKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmListService.deleteList(groupId, listKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList getList(
		long listId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListService.getList(listId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList getList(
		long groupId, java.lang.String listKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListService.getList(groupId, listKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList updateList(
		long groupId, java.lang.String listKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListService.updateList(groupId, listKey, nameMap,
			description, structureId, serviceContext);
	}

	public DDMListService getWrappedDDMListService() {
		return _ddmListService;
	}

	public void setWrappedDDMListService(DDMListService ddmListService) {
		_ddmListService = ddmListService;
	}

	private DDMListService _ddmListService;
}