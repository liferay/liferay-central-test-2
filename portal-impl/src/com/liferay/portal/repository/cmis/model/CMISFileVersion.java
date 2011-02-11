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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;

/**
 * @author Alexander Chow
 */
public class CMISFileVersion extends CMISModel implements FileVersion {

	public CMISFileVersion(
		CMISRepository cmisRepository, long repositoryId, long fileVersionId,
		Document document) {

		_cmisRepository = cmisRepository;
		_repositoryId = repositoryId;
		_fileVersionId = fileVersionId;
		_document = document;
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	public String getChangeLog() {
		return null;
	}

	public long getCompanyId() {
		return 0;
	}

	public Date getCreateDate() {
		return _document.getCreationDate().getTime();
	}

	public String getDescription() {
		return getDescription((CmisObject)getModel());
	}

	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	public String getExtraSettings() {
		return null;
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		return _cmisRepository.toFileEntry(_document.getAllVersions().get(0));
	}

	public long getFileEntryId() {
		try {
			return getFileEntry().getFileEntryId();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public long getGroupId() {
		return 0;
	}

	public String getIcon() {
		return null;
	}

	public Object getModel() {
		return _document;
	}

	public long getPrimaryKey() {
		return _fileVersionId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getSize() {
		return _document.getContentStreamLength();
	}

	public int getStatus() {
		return 0;
	}

	public long getStatusByUserId() {
		return 0;
	}

	public String getStatusByUserName() {
		return null;
	}

	public String getStatusByUserUuid() {
		return null;
	}

	public Date getStatusDate() {
		return null;
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

	public String getVersion() {
		return _document.getVersionLabel();
	}

	public boolean isApproved() {
		return false;
	}

	public boolean isDefaultRepository() {
		return false;
	}

	public boolean isDraft() {
		return false;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isExpired() {
		return false;
	}

	public boolean isPending() {
		return false;
	}

	public void prepare() {
	}

	public FileVersion toEscapedModel() {
		return this;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISFileVersion.class);

	private CMISRepository _cmisRepository;
	private Document _document;
	private long _fileVersionId;
	private long _repositoryId;

}