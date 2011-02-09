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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	public long addRepository(
			long groupId, long parentFolderId, String name, String description,
			String portletId, int type,
			UnicodeProperties typeSettingsProperties)
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
		repository.setDlFolderId(getDLFolderId(
			user, groupId, repositoryId, parentFolderId, name, description));

		repositoryPersistence.update(repository, false);

		// Repository Implementation

		getRepositoryImpl(repositoryId).addRepository(
			groupId, name, description, portletId, typeSettingsProperties);

		return repositoryId;
	}

	public void checkRepository(long repositoryId) throws SystemException {
		Group group = groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			return;
		}

		try {
			repositoryPersistence.findByPrimaryKey(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new RepositoryException(nsre.getMessage());
		}
	}

	/**
	 * This method deletes the all repositories associated with this group.
	 * It purges the default repository but does not purge any mapped
	 * repositories.
	 *
	 * @param groupId identifier of group to delete from
	 */
	public void deleteRepositories(long groupId)
		throws PortalException, SystemException {

		// Mapped repositories

		List<Repository> repositories = repositoryPersistence.findByGroupId(
			groupId);

		for (Repository repository : repositories) {
			deleteRepository(repository.getRepositoryId(), false);
		}

		// Default repository

		deleteRepository(groupId, true);
	}

	public void deleteRepository(long repositoryId, boolean purge)
		throws PortalException, SystemException {

		if (purge) {
			getLocalRepositoryImpl(repositoryId).deleteAll();
		}

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			expandoValueLocalService.deleteValues(
				Repository.class.getName(), repositoryId);

			try {
				dlRepositoryLocalService.deleteFolder(
					repository.getDlFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}

			repositoryPersistence.remove(repository);
		}
	}

	public Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryPersistence.findByPrimaryKey(repositoryId);
	}

	public LocalRepository getLocalRepositoryImpl(long repositoryId)
		throws SystemException {

		LocalRepository localRepository = new LiferayLocalRepository(
			repositoryId);

		checkRepository(repositoryId);

		return localRepository;
	}

	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		LocalRepository localRepository = new LiferayLocalRepository(
			folderId, fileEntryId, fileVersionId);

		checkRepository(localRepository.getRepositoryId());

		return localRepository;
	}

	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long repositoryId)
		throws SystemException {

		com.liferay.portal.kernel.repository.Repository repository =
			new LiferayRepository(repositoryId);

		checkRepository(repositoryId);

		return repository;
	}

	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		com.liferay.portal.kernel.repository.Repository repository =
			new LiferayRepository(folderId, fileEntryId, fileVersionId);

		checkRepository(repository.getRepositoryId());

		return repository;
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
		repository.setName(name);
		repository.setDescription(description);
		repository.setTypeSettingsProperties(typeSettingsProperties);

		repositoryPersistence.update(repository, false);

		// Repository Implementation

		getRepositoryImpl(repositoryId).updateRepository(
			typeSettingsProperties);
	}

	protected long getDLFolderId(
			User user, long groupId, long repositoryId, long parentFolderId,
			String name, String description)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("mountPoint", Boolean.TRUE);

		DLFolder dlFolder = dlRepositoryLocalService.addFolder(
			user.getUserId(), groupId, repositoryId, parentFolderId, name,
			description, serviceContext);

		return dlFolder.getFolderId();
	}

}