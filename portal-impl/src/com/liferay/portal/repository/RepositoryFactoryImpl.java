/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl extends BaseRepositoryFactory<Repository>
	implements RepositoryFactory {

	@Override
	protected Repository createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException {

		Repository repository = createExternalRepositoryImpl(
			repositoryId, classNameId);

		return new CapabilityRepository(
			repository, getExternalSupportedCapabilities(),
			getExternalExportedCapabilityClasses());
	}

	@Override
	protected Repository createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId);

		Repository repository = create(repositoryId);

		return new CapabilityRepository(
			repository, getExternalSupportedCapabilities(),
			getExternalExportedCapabilityClasses());
	}

	@Override
	protected Repository createInternalRepositoryInstance(
		long groupId, long repositoryId, long dlFolderId) {

		Repository repository = createLiferayInternalRepository(
			groupId, repositoryId, dlFolderId);

		return new CapabilityRepository(
			repository, getInternalSupportedCapabilities(),
			getInternalExportedCapabilityClasses());
	}

	protected Repository createLiferayInternalRepository(
		long groupId, long repositoryId, long dlFolderId) {

		return new LiferayRepository(
			getRepositoryLocalService(), getRepositoryService(),
			getDlAppHelperLocalService(), getDlFileEntryLocalService(),
			getDlFileEntryService(), getDlFileEntryTypeLocalService(),
			getDlFileVersionLocalService(), getDlFileVersionService(),
			getDlFolderLocalService(), getDlFolderService(),
			getResourceLocalService(), groupId, repositoryId, dlFolderId);
	}

	@Override
	protected long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException {

		DLFileEntryService dlFileEntryService = getDlFileEntryService();

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(fileEntryId);

		return dlFileEntry.getRepositoryId();
	}

	@Override
	protected long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException {

		DLFileVersionService dlFileVersionService = getDlFileVersionService();

		DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
			fileVersionId);

		return dlFileVersion.getRepositoryId();
	}

	@Override
	protected long getFolderRepositoryId(long folderId) throws PortalException {
		DLFolderService dlFolderService = getDlFolderService();

		DLFolder dlFolder = dlFolderService.getFolder(folderId);

		return dlFolder.getRepositoryId();
	}

	@Override
	protected com.liferay.portal.model.Repository getRepository(
		long repositoryId) {

		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		return repositoryLocalService.fetchRepository(repositoryId);
	}

}