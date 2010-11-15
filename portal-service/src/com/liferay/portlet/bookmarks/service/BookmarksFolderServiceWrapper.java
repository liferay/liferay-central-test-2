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

package com.liferay.portlet.bookmarks.service;

/**
 * <p>
 * This class is a wrapper for {@link BookmarksFolderService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolderService
 * @generated
 */
public class BookmarksFolderServiceWrapper implements BookmarksFolderService {
	public BookmarksFolderServiceWrapper(
		BookmarksFolderService bookmarksFolderService) {
		_bookmarksFolderService = bookmarksFolderService;
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.addFolder(parentFolderId, name,
			description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.deleteFolder(folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolder(folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	public BookmarksFolderService getWrappedBookmarksFolderService() {
		return _bookmarksFolderService;
	}

	public void setWrappedBookmarksFolderService(
		BookmarksFolderService bookmarksFolderService) {
		_bookmarksFolderService = bookmarksFolderService;
	}

	private BookmarksFolderService _bookmarksFolderService;
}