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
import com.liferay.portal.model.Lock;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;

/**
 * @author Alexander Chow
 */
public class CMISFileEntry extends CMISModel implements FileEntry {

	public CMISFileEntry(
		CMISRepository cmisRepository, long repositoryId, long fileEntryId,
		Document document) {

		_cmisRepository = cmisRepository;
		_repositoryId = repositoryId;
		_fileEntryId = fileEntryId;
		_document = document;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException, SystemException {

		return containsPermission(_document, actionId);
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	public long getCompanyId() {
		return 0;
	}

	public InputStream getContentStream() {
		return _document.getContentStream().getStream();
	}

	public InputStream getContentStream(String version) {
		List<Document> versions = _document.getAllVersions();

		for (Document document : versions) {
			if (document.getVersionLabel().equals(version)) {
				return document.getContentStream().getStream();
			}
		}

		return null;
	}

	public Date getCreateDate() {
		return _document.getCreationDate().getTime();
	}

	public String getDescription() {
		return getDescription((CmisObject)getModel());
	}

	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public FileVersion getFileVersion() {
		return new CMISFileVersion(
			_cmisRepository, _repositoryId, _fileEntryId, _document);
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		List<Document> versions = _document.getAllVersions();

		for (Document document : versions) {
			if (document.getVersionLabel().equals(version)) {
				return _cmisRepository.toFileVersion(document);
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
				fileVersions.add(_cmisRepository.toFileVersion(document));
			}
		}
		catch (PortalException pe) {
			throw new RepositoryException(pe);
		}

		return fileVersions;
	}

	public Folder getFolder() {
		try {
			return _cmisRepository.toFolder(_document.getParents().get(0));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public long getFolderId() {
		return getFolder().getFolderId();
	}

	public long getGroupId() {
		return 0;
	}

	public String getIcon() {
		return null;
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		return _cmisRepository.toFileVersion(_document);
	}

	public Lock getLock() {
		return null;
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
		return _repositoryId;
	}

	public long getSize() {
		return _document.getContentStreamLength();
	}

	public String getTitle() {
		return _document.getName();
	}

	public long getUserId() {
		return 0;
	}

	public String getUserName() {
		return _document.getCreatedBy();
	}

	public String getUserUuid() {
		return null;
	}

	public String getUuid() {
		return _document.getId();
	}

	public String getVersion() {
		return _document.getVersionLabel();
	}

	public long getVersionUserId() {
		return 0;
	}

	public String getVersionUserName() {
		return _document.getLastModifiedBy();
	}

	public String getVersionUserUuid() {
		return null;
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
	private long _repositoryId;

}