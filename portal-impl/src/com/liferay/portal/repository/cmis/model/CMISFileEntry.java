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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;

/**
 * @author Alexander Chow
 */
public class CMISFileEntry extends CMISModel implements FileEntry {

	public CMISFileEntry(
		CMISRepository cmisRepository, String uuid, long fileEntryId,
		Document document) {

		_cmisRepository = cmisRepository;
		_uuid = uuid;
		_fileEntryId = fileEntryId;
		_document = document;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws SystemException {

		return containsPermission(_document, actionId);
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	public InputStream getContentStream() {
		return _document.getContentStream().getStream();
	}

	public InputStream getContentStream(String version) {
		for (Document document : _document.getAllVersions()) {
			if (version.equals(document.getVersionLabel())) {
				ContentStream contentStream = document.getContentStream();

				return contentStream.getStream();
			}
		}

		return null;
	}

	public Date getCreateDate() {
		return _document.getCreationDate().getTime();
	}

	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public FileVersion getFileVersion() {
		return new CMISFileVersion(_cmisRepository, _fileEntryId, _document);
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		for (Document document : _document.getAllVersions()) {
			if (version.equals(document.getVersionLabel())) {
				return CMISRepositoryLocalServiceUtil.toFileVersion(
					getRepositoryId(), document);
			}
		}

		return null;
	}

	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		List<Document> documents = _document.getAllVersions();

		List<FileVersion> fileVersions = new ArrayList<FileVersion>(
			documents.size());

		try {
			for (Document document : documents) {
				FileVersion fileVersion =
					CMISRepositoryLocalServiceUtil.toFileVersion(
						getRepositoryId(), document);

				fileVersions.add(fileVersion);
			}
		}
		catch (PortalException pe) {
			throw new RepositoryException(pe);
		}

		return fileVersions;
	}

	public Folder getFolder() {
		try {
			return CMISRepositoryLocalServiceUtil.toFolder(
				getRepositoryId(), _document.getParents().get(0));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public long getFolderId() {
		Folder folder = getFolder();

		return folder.getFolderId();
	}

	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return CMISRepositoryLocalServiceUtil.toFileVersion(
			getRepositoryId(), _document.getObjectOfLatestVersion(false));
	}

	public Lock getLock() {
		Lock lock = new LockImpl();

		lock.setCreateDate(new Date());

		return lock;
	}

	public Object getModel() {
		return _document;
	}

	public Date getModifiedDate() {
		return _document.getLastModificationDate().getTime();
	}

	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public int getReadCount() {
		return 0;
	}

	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	public long getSize() {
		return _document.getContentStreamLength();
	}

	public String getTitle() {
		return _document.getName();
	}

	public long getUserId() {
		try {
			return UserLocalServiceUtil.getDefaultUserId(getCompanyId());
		}
		catch (Exception e) {
			return 0;
		}
	}

	public String getUserName() {
		return _document.getCreatedBy();
	}

	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getDefaultUser(
				getCompanyId());

			return user.getUserUuid();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public String getVersion() {
		return GetterUtil.get(
			_document.getVersionLabel(), DLFileEntryConstants.DEFAULT_VERSION);
	}

	public long getVersionUserId() {
		return 0;
	}

	public String getVersionUserName() {
		return _document.getLastModifiedBy();
	}

	public String getVersionUserUuid() {
		return StringPool.BLANK;
	}

	public boolean hasLock() {
		return false;
	}

	public boolean isDefaultRepository() {
		return false;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isLocked() {
		String checkedOutId = _document.getVersionSeriesCheckedOutId();

		if (Validator.isNotNull(checkedOutId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSupportsLocking() {
		return false;
	}

	public boolean isSupportsMetadata() {
		return false;
	}

	public boolean isSupportsSocial() {
		return false;
	}

	public void prepare() {
	}

	public FileEntry toEscapedModel() {
		return this;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISFileEntry.class);

	private CMISRepository _cmisRepository;
	private Document _document;
	private long _fileEntryId;
	private String _uuid;

}