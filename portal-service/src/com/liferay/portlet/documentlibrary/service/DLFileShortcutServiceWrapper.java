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
 * <p>
 * This class is a wrapper for {@link DLFileShortcutService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutService
 * @generated
 */
public class DLFileShortcutServiceWrapper implements DLFileShortcutService {
	public DLFileShortcutServiceWrapper(
		DLFileShortcutService dlFileShortcutService) {
		_dlFileShortcutService = dlFileShortcutService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long groupId, long folderId, long toFolderId, java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutService.addFileShortcut(groupId, folderId,
			toFolderId, toName, serviceContext);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long fileShortcutId, long folderId, long toFolderId,
		java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutService.updateFileShortcut(fileShortcutId,
			folderId, toFolderId, toName, serviceContext);
	}

	public DLFileShortcutService getWrappedDLFileShortcutService() {
		return _dlFileShortcutService;
	}

	public void setWrappedDLFileShortcutService(
		DLFileShortcutService dlFileShortcutService) {
		_dlFileShortcutService = dlFileShortcutService;
	}

	private DLFileShortcutService _dlFileShortcutService;
}