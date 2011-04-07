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

package com.liferay.portlet.dynamicdatalists.service;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntryService
 * @generated
 */
public class DDLEntryServiceWrapper implements DDLEntryService {
	public DDLEntryServiceWrapper(DDLEntryService ddlEntryService) {
		_ddlEntryService = ddlEntryService;
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntry addEntry(
		long groupId, long ddmStructureId, java.lang.String entryKey,
		boolean autoEntryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryService.addEntry(groupId, ddmStructureId, entryKey,
			autoEntryKey, nameMap, description, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryService.deleteEntry(entryId);
	}

	public void deleteEntry(long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryService.deleteEntry(groupId, entryKey);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryService.getEntry(entryId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryService.getEntry(groupId, entryKey);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntry updateEntry(
		long groupId, long ddmStructureId, java.lang.String entryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryService.updateEntry(groupId, ddmStructureId, entryKey,
			nameMap, description, serviceContext);
	}

	public DDLEntryService getWrappedDDLEntryService() {
		return _ddlEntryService;
	}

	public void setWrappedDDLEntryService(DDLEntryService ddlEntryService) {
		_ddlEntryService = ddlEntryService;
	}

	private DDLEntryService _ddlEntryService;
}