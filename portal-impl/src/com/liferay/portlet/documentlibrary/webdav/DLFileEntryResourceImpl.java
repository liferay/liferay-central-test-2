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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.model.Lock;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryResourceImpl extends BaseResourceImpl {

	public DLFileEntryResourceImpl(
		WebDAVRequest webDavRequest, DLFileEntry fileEntry, String parentPath,
		String name) {

		super(
			parentPath, name, fileEntry.getTitle(), fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), fileEntry.getSize());

		setModel(fileEntry);
		setClassName(DLFileEntry.class.getName());
		setPrimaryKey(fileEntry.getPrimaryKey());

		//_webDavRequest = webDavRequest;
		_fileEntry = fileEntry;
	}

	public boolean isCollection() {
		return false;
	}

	public Lock getLock() {
		try {
			return DLAppServiceUtil.getFileEntryLock(
				_fileEntry.getGroupId(), _fileEntry.getFolderId(),
				_fileEntry.getName());
		}
		catch (Exception e) {
		}

		return null;
	}

	public boolean isLocked() {
		try {
			return DLAppServiceUtil.hasFileEntryLock(
				_fileEntry.getGroupId(), _fileEntry.getFolderId(),
				_fileEntry.getName());
		}
		catch (Exception e) {
		}

		return false;
	}

	public String getContentType() {
		return MimeTypesUtil.getContentType(_fileEntry.getTitle());
	}

	public InputStream getContentAsStream() throws WebDAVException {
		try {
			String version = StringPool.BLANK;

			if (PropsValues.DL_WEBDAV_HOLD_LOCK) {

				// Get last version regardless of status

				DLFileVersion fileVersion = _fileEntry.getLatestFileVersion();

				version = fileVersion.getVersion();
			}

			return DLAppServiceUtil.getFileAsStream(
				_fileEntry.getGroupId(), _fileEntry.getFolderId(),
				_fileEntry.getName(), version);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private DLFileEntry _fileEntry;
	//private WebDAVRequest _webDavRequest;

}