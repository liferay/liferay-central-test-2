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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryConstants;
import com.liferay.portal.kernel.repository.RepositoryFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	public long addRepository(
			long groupId, String name, String description, String portletId,
			int type, UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		// Repository

		User user = getUser();
		Date now = new Date();

		long repositoryId = counterLocalService.increment();

		Repository repository = repositoryPersistence.create(repositoryId);

		repository.setGroupId(groupId);
		repository.setCompanyId(user.getCompanyId());
		repository.setCreateDate(now);
		repository.setModifiedDate(now);
		repository.setName(name);
		repository.setDescription(description);
		repository.setPortletId(portletId);
		repository.setType(type);
		repository.setTypeSettingsProperties(typeSettingsProperties);
		repository.setDlFolderId(
			getDLFolderId(user, groupId, name, description));

		repositoryPersistence.update(repository, false);

		// Local repository

		LocalRepository localRepository = getLocalRepository(repositoryId);

		localRepository.addRepository(
			groupId, name, description, portletId, typeSettingsProperties);

		return repositoryId;
	}

	public void deleteRepositories(long groupId, int purge)
		throws PortalException, SystemException {

		// Default repository

		boolean purgeDefault = false;

		if ((purge == RepositoryConstants.PURGE_ALL) ||
			(purge == RepositoryConstants.PURGE_DEFAULT)) {

			purgeDefault = true;
		}

		deleteRepository(groupId, purgeDefault);

		// Mapped repositories

		List<Repository> repositories = repositoryPersistence.findByGroupId(
			groupId);

		boolean purgeNonDefault = false;

		if ((purge == RepositoryConstants.PURGE_ALL) ||
			(purge == RepositoryConstants.PURGE_MAPPED)) {

			purgeNonDefault = true;
		}

		for (Repository repository : repositories) {
			deleteRepository(repository.getRepositoryId(), purgeNonDefault);
		}
	}

	public void deleteRepository(long repositoryId, boolean purge)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			repositoryPersistence.remove(repository);

			expandoValueLocalService.deleteValues(
				Repository.class.getName(), repositoryId);
		}

		if (purge) {
			LocalRepository localRepository = getLocalRepository(repositoryId);

			localRepository.deleteAll();
		}
	}

	public Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryPersistence.findByPrimaryKey(repositoryId);
	}

	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		return repository.getTypeSettingsProperties();
	}

	public void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		// Repository

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setModifiedDate(new Date());
		repository.setTypeSettingsProperties(typeSettingsProperties);

		repositoryPersistence.update(repository, false);

		// Local repository

		LocalRepository localRepository = getLocalRepository(repositoryId);

		typeSettingsProperties = localRepository.updateRepository(
			typeSettingsProperties);
	}

	protected long getDLFolderId(
			User user, long groupId, String name, String description)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlRepositoryLocalService.addFolder(
			user.getUserId(), groupId,
			DLFolderConstants.MAPPED_PARENT_FOLDER_ID, name, description,
			new ServiceContext());

		return dlFolder.getFolderId();
	}

	protected LocalRepository getLocalRepository(long repositoryId) {
		return RepositoryFactoryUtil.getLocalRepository(repositoryId);
	}

}