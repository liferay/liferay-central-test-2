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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public abstract class LiferayRepositoryBase extends LiferayBase {

	public LiferayRepositoryBase(long repositoryId) {
		initByRepositoryId(repositoryId);
	}

	public LiferayRepositoryBase(
		long folderId, long fileEntryId, long fileVersionId) {

		if (folderId != 0) {
			initByFolderId(folderId);
		}
		else if (fileEntryId != 0) {
			initByFileEntryId(fileEntryId);
		}
		else if (fileVersionId != 0) {
			initByFileVersionId(fileVersionId);
		}
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	protected long getGroupId() {
		return _groupId;
	}

	protected void initByFileEntryId(long fileEntryId) {
		try {
			DLFileEntry dlFileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
				fileEntryId);

			initByRepositoryId(dlFileEntry.getRepositoryId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initByFileVersionId(long fileVersionId) {
		try {
			DLFileVersion dlFileVersion =
				DLRepositoryLocalServiceUtil.getFileVersion(fileVersionId);

			initByRepositoryId(dlFileVersion.getRepositoryId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initByFolderId(long folderId) {
		try {
			DLFolder dlFolder = DLRepositoryLocalServiceUtil.getFolder(
				folderId);

			initByRepositoryId(dlFolder.getRepositoryId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initByRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
		_groupId = repositoryId;

		try {
			Repository repository = RepositoryServiceUtil.getRepository(
				repositoryId);

			_repositoryId = repository.getRepositoryId();
			_groupId = repository.getGroupId();
			_dlFolderId = repository.getDlFolderId();
		}
		catch (Exception e) {
		}
	}

	protected boolean isDefaultRepository() {
		if (_groupId == _repositoryId) {
			return false;
		}
		else {
			return true;
		}
	}

	protected long toFolderId(long folderId) {
		if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			isDefaultRepository()) {

			return _dlFolderId;
		}
		else {
			return folderId;
		}
	}

	protected List<Long> toFolderIds(List<Long> folderIds) {
		List<Long> toFolderIds = new ArrayList<Long>(folderIds.size());

		for (long folderId : folderIds) {
			toFolderIds.add(toFolderId(folderId));
		}

		return toFolderIds;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayRepositoryBase.class);

	private long _dlFolderId;
	private long _groupId;
	private long _repositoryId;

}