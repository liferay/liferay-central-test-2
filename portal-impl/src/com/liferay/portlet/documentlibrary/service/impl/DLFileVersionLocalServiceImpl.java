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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Jorge Ferrer
 */
public class DLFileVersionLocalServiceImpl
	extends DLFileVersionLocalServiceBaseImpl {

	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByPrimaryKey(fileVersionId);
	}

	public DLFileVersion getFileVersion(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		return dlFileVersionPersistence.findByG_F_N_V(
			groupId, folderId, name, version);
	}

	public List<DLFileVersion> getFileVersions(
			long groupId, long folderId, String name, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return dlFileVersionPersistence.findByG_F_N(
				groupId, folderId, name);
		}
		else {
			return dlFileVersionPersistence.findByG_F_N_S(
				groupId, folderId, name, status);
		}
	}

	public DLFileVersion getLatestFileVersion(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByG_F_N(
			groupId, folderId, name, 0, 1, new FileVersionVersionComparator());

		if (fileVersions.isEmpty()) {
			throw new NoSuchFileVersionException();
		}

		return fileVersions.get(0);
	}

	public DLFileVersion updateDescription(
			long fileVersionId, String description)
		throws PortalException, SystemException {

		DLFileVersion fileVersion = dlFileVersionPersistence.findByPrimaryKey(
			fileVersionId);

		fileVersion.setDescription(description);

		dlFileVersionPersistence.update(fileVersion, false);

		return fileVersion;
	}

}