/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class DLFileVersionImpl extends DLFileVersionBaseImpl {

	public DLFileVersionImpl() {
	}

	@Override
	public String getChecksum() throws PortalException, SystemException {
		String dirName = "checksum/" + getFileEntryId();
		String fileName = dirName + "/" + getFileVersionId() + ".hash";

		String checkSum = null;

		try {
			byte[] bytes = DLStoreUtil.getFileAsBytes(
				getCompanyId(), CompanyConstants.SYSTEM, fileName);

			checkSum = new String(bytes);
		}
		catch (NoSuchFileException nsfe) {
		}

		return checkSum;
	}

	@Override
	public InputStream getContentStream(boolean incrementCounter)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFileAsStream(
			PrincipalThreadLocal.getUserId(), getFileEntryId(), getVersion(),
			incrementCounter);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				getCompanyId(), DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return _extraSettingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new UnicodeProperties(true);

			try {
				_extraSettingsProperties.load(super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _extraSettingsProperties;
	}

	@Override
	public DLFileEntry getFileEntry() throws PortalException, SystemException {
		return DLFileEntryLocalServiceUtil.getFileEntry(getFileEntryId());
	}

	@Override
	public DLFolder getFolder() {
		DLFolder dlFolder = null;

		if (getFolderId() > 0) {
			try {
				dlFolder = DLFolderLocalServiceUtil.getFolder(getFolderId());
			}
			catch (Exception e) {
				dlFolder = new DLFolderImpl();

				_log.error(e, e);
			}
		}
		else {
			dlFolder = new DLFolderImpl();
		}

		return dlFolder;
	}

	@Override
	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	@Override
	public void setChecksum(String checksum)
		throws PortalException, SystemException {

		String dirName = "checksum/" + getFileEntryId();
		String fileName = dirName + "/" + getFileVersionId() + ".hash";

		try {
			DLStoreUtil.addDirectory(
				getCompanyId(), CompanyConstants.SYSTEM, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		try {
			DLStoreUtil.deleteFile(
				getCompanyId(), CompanyConstants.SYSTEM, fileName);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		DLStoreUtil.addFile(
			getCompanyId(), CompanyConstants.SYSTEM, fileName,
			checksum.getBytes());
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	@Override
	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsProperties) {

		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(_extraSettingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileVersionImpl.class);

	private transient ExpandoBridge _expandoBridge;
	private UnicodeProperties _extraSettingsProperties;

}