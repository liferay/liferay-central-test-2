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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileVersionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersionLocalService
 * @generated
 */
public class DLFileVersionLocalServiceWrapper
	implements DLFileVersionLocalService {
	public DLFileVersionLocalServiceWrapper(
		DLFileVersionLocalService dlFileVersionLocalService) {
		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion addDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.addDLFileVersion(dlFileVersion);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion createDLFileVersion(
		long fileVersionId) {
		return _dlFileVersionLocalService.createDLFileVersion(fileVersionId);
	}

	public void deleteDLFileVersion(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileVersionLocalService.deleteDLFileVersion(fileVersionId);
	}

	public void deleteDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileVersionLocalService.deleteDLFileVersion(dlFileVersion);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersion(fileVersionId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersions(start, end);
	}

	public int getDLFileVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getDLFileVersionsCount();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.updateDLFileVersion(dlFileVersion,
			merge);
	}

	public void deleteFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileVersionLocalService.deleteFileVersion(fileVersion);
	}

	public void deleteFileVersion(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileVersionLocalService.deleteFileVersion(groupId, folderId, name,
			version);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long groupId, long folderId, java.lang.String name,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersion(groupId, folderId,
			name, version);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long groupId, long folderId, java.lang.String name, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getFileVersions(groupId, folderId,
			name, status);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.getLatestFileVersion(groupId,
			folderId, name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateDescription(
		long fileVersionId, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersionLocalService.updateDescription(fileVersionId,
			description);
	}

	public DLFileVersionLocalService getWrappedDLFileVersionLocalService() {
		return _dlFileVersionLocalService;
	}

	private DLFileVersionLocalService _dlFileVersionLocalService;
}