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

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.ReferenceMap;

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
	 * This method deletes the all repositories associated with this group. It
	 * purges the default repository but does not purge any mapped repositories.
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

	public LocalRepository getLocalRepositoryImpl(long repositoryId)
		throws SystemException {

		LocalRepository localRepositoryImpl =
			_localRepositoryMap.get(repositoryId);

		if (localRepositoryImpl == null) {
			int type = _getRepositoryType(repositoryId);

			if (type == 0) {
				localRepositoryImpl = new LiferayLocalRepository(repositoryId);
			}
			else {
				BaseRepositoryImpl baseRepository = _createRepositoryImpl(
					repositoryId, type);

				localRepositoryImpl = baseRepository.getLocalRepository();
			}

			checkRepository(repositoryId);

			_localRepositoryMap.put(repositoryId, localRepositoryImpl);
		}

		return localRepositoryImpl;
	}

	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		long entryId = _getEntryId(folderId, fileEntryId, fileVersionId);

		LocalRepository localRepositoryImpl =
			_localRepositoryEntryMap.get(entryId);

		if (localRepositoryImpl == null) {
			localRepositoryImpl = new LiferayLocalRepository(
				folderId, fileEntryId, fileVersionId);

			if (localRepositoryImpl.getRepositoryId() == 0) {
				localRepositoryImpl = null;
			}

			if (localRepositoryImpl == null) {
				BaseRepositoryImpl baseRepository = _createRepositoryImpl(
					entryId);

				localRepositoryImpl = baseRepository.getLocalRepository();
			}
			else {
				checkRepository(localRepositoryImpl.getRepositoryId());
			}

			_localRepositoryEntryMap.put(entryId, localRepositoryImpl);
		}

		return localRepositoryImpl;
	}

	public Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryPersistence.findByPrimaryKey(repositoryId);
	}

	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long repositoryId)
		throws SystemException {

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoryMap.get(repositoryId);

		if (repositoryImpl == null) {
			int type = _getRepositoryType(repositoryId);

			if (type == 0) {
				repositoryImpl = new LiferayRepository(repositoryId);
			}
			else {
				repositoryImpl = _createRepositoryImpl(repositoryId, type);
			}

			checkRepository(repositoryId);

			_repositoryMap.put(repositoryId, repositoryImpl);
		}

		return repositoryImpl;
	}

	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		long entryId = _getEntryId(folderId, fileEntryId, fileVersionId);

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoryEntryMap.get(entryId);

		if (repositoryImpl == null) {
			repositoryImpl = new LiferayRepository(
				folderId, fileEntryId, fileVersionId);

			if (repositoryImpl.getRepositoryId() == 0) {
				repositoryImpl = null;
			}

			if (repositoryImpl == null) {
				repositoryImpl = _createRepositoryImpl(entryId);
			}
			else {
				checkRepository(repositoryImpl.getRepositoryId());
			}

			_repositoryEntryMap.put(entryId, repositoryImpl);
		}

		return repositoryImpl;
	}

	public String[] getSupportedConfigurations(int type)
		throws SystemException {

		try {
			Properties repositoryImpls = PropsUtil.getProperties(
				PropsKeys.DL_REPOSITORY_IMPL, true);

			String repositoryImpl = repositoryImpls.getProperty(
				Long.toString(type));

			Class<BaseRepositoryImpl> repositoryImplClass =
				(Class<BaseRepositoryImpl>)Class.forName(repositoryImpl);

			return (String[])repositoryImplClass.getField(
				"SUPPORTED_CONFIGURATIONS").get(null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public String[] getSupportedParameters(int type, String configuration)
		throws SystemException {

		try {
			Properties repositoryImpls = PropsUtil.getProperties(
				PropsKeys.DL_REPOSITORY_IMPL, true);

			String repositoryImpl = repositoryImpls.getProperty(
				Long.toString(type));

			Class<BaseRepositoryImpl> repositoryImplClass =
				(Class<BaseRepositoryImpl>)Class.forName(repositoryImpl);

			String[] supportedConfigurations =
				(String[])repositoryImplClass.getField(
					"SUPPORTED_CONFIGURATIONS").get(null);

			String[][] supportedParameters =
				(String[][])repositoryImplClass.getField(
					"SUPPORTED_PARAMETERS").get(null);

			for (int i = 0; i < supportedConfigurations.length; i++) {
				if (supportedConfigurations[i].equals(configuration)) {
					return supportedParameters[i];
				}
			}

			throw new RepositoryException(
				"Configuration not found for repository type " + type);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		return repository.getTypeSettingsProperties();
	}

	public void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException, SystemException {

		// Repository

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setModifiedDate(new Date());
		repository.setName(name);
		repository.setDescription(description);

		repositoryPersistence.update(repository, false);
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

	private BaseRepositoryImpl _createRepositoryImpl(long entryId)
		throws SystemException, RepositoryException {

		BaseRepositoryImpl baseRepository;

		try {
			RepositoryEntry repositoryEntry =
				repositoryEntryPersistence.findByPrimaryKey(entryId);

			long repositoryId = repositoryEntry.getRepositoryId();

			baseRepository = (BaseRepositoryImpl)getRepositoryImpl(
				repositoryId);
		}
		catch (NoSuchRepositoryEntryException nsree) {
			throw new RepositoryException(nsree);
		}

		return baseRepository;
	}

	private BaseRepositoryImpl _createRepositoryImpl(
			long repositoryId, int type)
		throws RepositoryException {

		Properties repositoryImpls = PropsUtil.getProperties(
			PropsKeys.DL_REPOSITORY_IMPL, true);

		String repositoryImpl = repositoryImpls.getProperty(
			Long.toString(type));

		try {
			BaseRepositoryImpl baseRepository = (BaseRepositoryImpl)
				Class.forName(repositoryImpl).newInstance();

			UnicodeProperties typeSettingsProperties = getRepository(
				repositoryId).getTypeSettingsProperties();

			baseRepository.initRepository(
				repositoryId, typeSettingsProperties,
				repositoryEntryPersistence, counterLocalService);

			return baseRepository;
		}
		catch (Exception e) {
			throw new RepositoryException(
				"There is no valid repository class for type " + type,
				e);
		}
	}

	private long _getEntryId(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		long entryId = 0;

		if (folderId != 0) {
			entryId = folderId;
		}
		else if (fileEntryId != 0) {
			entryId = fileEntryId;
		}
		else if (fileVersionId != 0) {
			entryId = fileVersionId;
		}

		if (entryId == 0) {
			throw new RepositoryException(
				"Missing a valid ID for folder, fileEntry or fileVersion");
		}

		return entryId;
	}

	private int _getRepositoryType(long repositoryId) throws SystemException {
		int type = 0;

		Repository repositoryObj = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repositoryObj != null) {
			type = repositoryObj.getType();
		}

		return type;
	}

	private Map<Long, LocalRepository> _localRepositoryEntryMap =
		Collections.synchronizedMap(new ReferenceMap());

	private Map<Long, LocalRepository> _localRepositoryMap =
		Collections.synchronizedMap(new HashMap());

	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoryEntryMap = Collections.synchronizedMap(new ReferenceMap());

	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoryMap = Collections.synchronizedMap(new HashMap());

}