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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
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
		init(repositoryId);
	}

	protected long getFolderId(long folderId) {
		if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			isMappedRepository()) {

			return _mappedFolderId;
		}
		else {
			return folderId;
		}
	}

	protected List<Long> getFolderIds(List<Long> folderIds) {
		List<Long> list = new ArrayList<Long>(folderIds.size());

		for (long folderId : folderIds) {
			list.add(getFolderId(folderId));
		}

		return list;
	}

	protected long getGroupId() {
		return _groupId;
	}

	protected long getRepositoryId() {
		return _repositoryId;
	}

	protected void init(long repositoryId) {
		_repositoryId = repositoryId;
		_groupId = repositoryId;

		try {
			com.liferay.portal.model.Repository repository =
				RepositoryLocalServiceUtil.getRepository(repositoryId);

			_groupId = repository.getGroupId();
			_repositoryId = repository.getRepositoryId();
			_mappedFolderId = repository.getMappedFolderId();
		}
		catch (Exception e) {
		}
	}

	protected void initByFolder(long folderId) {
		try {
			DLFolder folder = DLRepositoryLocalServiceUtil.getFolder(
				folderId);

			init(folder.getGroupId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initByFileEntry(long fileEntryId) {
		try {
			DLFileEntry fileEntry = DLRepositoryLocalServiceUtil.getFileEntry(
				fileEntryId);

			init(fileEntry.getGroupId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected boolean isMappedRepository() {
		if (_groupId == _repositoryId) {
			return false;
		}
		else {
			return true;
		}
	}

	private long _groupId;
	private long _mappedFolderId;
	private long _repositoryId;

	private static Log _log = LogFactoryUtil.getLog(
		LiferayRepositoryBase.class);

}