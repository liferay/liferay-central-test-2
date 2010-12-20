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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class RepositoryLocalServiceImpl extends RepositoryLocalServiceBaseImpl {

	public long addRepository(
			long companyId, long groupId, String name, String description,
			String portletKey, int type,
			UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		// Remote folder

		DLFolder mappedFolder = dlRepositoryLocalService.addFolder(
			getUserId(), groupId, DLFolderConstants.MAPPED_FOLDER_ID,
			name, description, new ServiceContext());

		// Repository

		Date now = new Date();

		long repositoryId = counterLocalService.increment();

		Repository repository = repositoryPersistence.create(repositoryId);

		repository.setCompanyId(companyId);
		repository.setGroupId(groupId);
		repository.setCreateDate(now);
		repository.setModifiedDate(now);
		repository.setName(name);
		repository.setDescription(description);
		repository.setPortletKey(portletKey);
		repository.setMappedFolderId(mappedFolder.getFolderId());
		repository.setType(type);
		repository.setTypeSettingsProperties(typeSettingsProperties);

		repositoryPersistence.update(repository, false);

		// Sync

		getLocalRepository(repositoryId).addRepository(
			companyId, groupId, name, description, portletKey,
			typeSettingsProperties);

		return repositoryId;
	}

	public void deleteRepositories(long companyId, long groupId, int purge)
		throws PortalException, SystemException {

		// Default repository

		boolean purgeDefaultData = false;

		if (purge == RepositoryConstants.PURGE_ALL ||
			purge == RepositoryConstants.PURGE_DEFAULT) {

			purgeDefaultData = true;
		}

		deleteRepository(groupId, purgeDefaultData);

		// Mapped repository

		List<Repository> list = repositoryPersistence.findByGroupId(groupId);

		boolean purgeNonDefaultData = false;

		if (purge == RepositoryConstants.PURGE_ALL ||
			purge == RepositoryConstants.PURGE_MAPPED) {

			purgeNonDefaultData = true;
		}

		for (Repository repository : list) {
			deleteRepository(repository.getRepositoryId(), purgeNonDefaultData);
		}
	}

	public void deleteRepository(long repositoryId, boolean purgeData)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			repositoryPersistence.remove(repository);

			expandoValueLocalService.deleteValues(
				Repository.class.getName(), repositoryId);
		}

		if (purgeData) {
			getLocalRepository(repositoryId).deleteAll();
		}
	}

	public UnicodeProperties getProperties(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		return repository.getTypeSettingsProperties();
	}

	public void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		// Sync

		typeSettingsProperties =
			getLocalRepository(repositoryId).updateRepository(
				typeSettingsProperties);

		// Update

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setModifiedDate(new Date());
		repository.setTypeSettingsProperties(typeSettingsProperties);

		repositoryPersistence.update(repository, false);
	}

	protected LocalRepository getLocalRepository(long repositoryId) {
		return RepositoryFactoryUtil.getLocalRepository(repositoryId);
	}

	protected long getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (name == null) {
			throw new PrincipalException();
		}

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal cannot be null");
		}

		return GetterUtil.getLong(name);
	}

}