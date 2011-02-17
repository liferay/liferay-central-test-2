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
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Chow
 */
public class RepositoryServiceImpl extends RepositoryServiceBaseImpl {

	public long addRepository(
			long groupId, long classNameId, long parentFolderId, String name,
			String description, String portletId,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = getUser();
		Date now = new Date();

		long repositoryId = counterLocalService.increment();

		Repository repository = repositoryPersistence.create(repositoryId);

		repository.setGroupId(groupId);
		repository.setCompanyId(user.getCompanyId());
		repository.setCreateDate(now);
		repository.setClassNameId(classNameId);
		repository.setModifiedDate(now);
		repository.setName(name);
		repository.setDescription(description);
		repository.setPortletId(portletId);
		repository.setTypeSettingsProperties(typeSettingsProperties);
		repository.setDlFolderId(
			getDLFolderId(
				user, groupId, repositoryId, parentFolderId, name, description,
				serviceContext));

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
			LocalRepository localRepositoryImpl = getLocalRepositoryImpl(
				repositoryId);

			localRepositoryImpl.deleteAll();
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
			_localRepositoriesByRepositoryId.get(repositoryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == 0) {
			localRepositoryImpl = new LiferayLocalRepository(repositoryId);
		}
		else {
			BaseRepositoryImpl baseRepositoryImpl = createRepositoryImpl(
				repositoryId, classNameId);

			localRepositoryImpl = baseRepositoryImpl.getLocalRepository();
		}

		checkRepository(repositoryId);

		_localRepositoriesByRepositoryId.put(repositoryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		long repositoryEntryId = getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryEntryId.get(repositoryEntryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		localRepositoryImpl = new LiferayLocalRepository(
			repositoryService, dlRepositoryLocalService, dlRepositoryService,
			folderId, fileEntryId, fileVersionId);

		if (localRepositoryImpl.getRepositoryId() == 0) {
			localRepositoryImpl = null;
		}

		if (localRepositoryImpl == null) {
			BaseRepositoryImpl baseRepositoryImpl = createRepositoryImpl(
				repositoryEntryId);

			localRepositoryImpl = baseRepositoryImpl.getLocalRepository();
		}
		else {
			checkRepository(localRepositoryImpl.getRepositoryId());
		}

		_localRepositoriesByRepositoryEntryId.put(
			repositoryEntryId, localRepositoryImpl);

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
			_repositoriesByRepositoryId.get(repositoryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == 0) {
			repositoryImpl = new LiferayRepository(repositoryId);
		}
		else {
			repositoryImpl = createRepositoryImpl(repositoryId, classNameId);
		}

		checkRepository(repositoryId);

		_repositoriesByRepositoryId.put(repositoryId, repositoryImpl);

		return repositoryImpl;
	}

	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws SystemException {

		long repositoryEntryId = getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByEntryId.get(repositoryEntryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		repositoryImpl = new LiferayRepository(
			repositoryService, dlRepositoryLocalService, dlRepositoryService,
			folderId, fileEntryId, fileVersionId);

		if (repositoryImpl.getRepositoryId() == 0) {
			repositoryImpl = null;
		}

		if (repositoryImpl == null) {
			repositoryImpl = createRepositoryImpl(repositoryEntryId);
		}
		else {
			checkRepository(repositoryImpl.getRepositoryId());
		}

		_repositoriesByEntryId.put(repositoryEntryId, repositoryImpl);

		return repositoryImpl;
	}

	public String[] getSupportedConfigurations(long classNameId)
		throws SystemException {

		try {
			String repositoryImplClassName = PortalUtil.getClassName(
				classNameId);

			Class<BaseRepositoryImpl> repositoryImplClass =
				(Class<BaseRepositoryImpl>)Class.forName(
					repositoryImplClassName);

			return (String[])repositoryImplClass.getField(
				"SUPPORTED_CONFIGURATIONS").get(null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public String[] getSupportedParameters(
			long classNameId, String configuration)
		throws SystemException {

		try {
			String repositoryImplClassName = PortalUtil.getClassName(
				classNameId);

			Class<BaseRepositoryImpl> repositoryImplClass =
				(Class<BaseRepositoryImpl>)Class.forName(
					repositoryImplClassName);

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
				"Configuration not found for repository classNameId " +
					classNameId);
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

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setModifiedDate(new Date());
		repository.setName(name);
		repository.setDescription(description);

		repositoryPersistence.update(repository, false);
	}

	protected BaseRepositoryImpl createRepositoryImpl(long repositoryEntryId)
		throws SystemException, RepositoryException {

		try {
			RepositoryEntry repositoryEntry =
				repositoryEntryPersistence.findByPrimaryKey(repositoryEntryId);

			long repositoryId = repositoryEntry.getRepositoryId();

			return (BaseRepositoryImpl)getRepositoryImpl(repositoryId);
		}
		catch (NoSuchRepositoryEntryException nsree) {
			throw new RepositoryException(nsree);
		}
	}

	protected BaseRepositoryImpl createRepositoryImpl(
			long repositoryId, long classNameId)
		throws RepositoryException {

		try {
			Repository repository = getRepository(repositoryId);

			String repositoryImplClassName = PortalUtil.getClassName(
				classNameId);

			BaseRepositoryImpl baseRepositoryImpl =
				(BaseRepositoryImpl)InstanceFactory.newInstance(
					repositoryImplClassName);

			baseRepositoryImpl.setCounterLocalService(counterLocalService);
			baseRepositoryImpl.setDLAppHelperLocalService(
				dlAppHelperLocalService);
			baseRepositoryImpl.setRepositoryId(repositoryId);
			baseRepositoryImpl.setTypeSettingsProperties(
				repository.getTypeSettingsProperties());

			baseRepositoryImpl.initRepository();

			return baseRepositoryImpl;
		}
		catch (Exception e) {
			throw new RepositoryException(
				"There is no valid repository class for classNameId " +
					classNameId,
				e);
		}
	}

	protected long getDLFolderId(
			User user, long groupId, long repositoryId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		serviceContext.setAttribute("mountPoint", Boolean.TRUE);

		DLFolder dlFolder = dlRepositoryLocalService.addFolder(
			user.getUserId(), groupId, repositoryId, parentFolderId, name,
			description, serviceContext);

		return dlFolder.getFolderId();
	}

	protected long getRepositoryEntryId(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		long repositoryEntryId = 0;

		if (folderId != 0) {
			repositoryEntryId = folderId;
		}
		else if (fileEntryId != 0) {
			repositoryEntryId = fileEntryId;
		}
		else if (fileVersionId != 0) {
			repositoryEntryId = fileVersionId;
		}

		if (repositoryEntryId == 0) {
			throw new RepositoryException(
				"Missing a valid ID for folder, fileEntry or fileVersion");
		}

		return repositoryEntryId;
	}

	protected long getRepositoryClassNameId(long repositoryId)
		throws SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return 0;
	}

	private Map<Long, LocalRepository> _localRepositoriesByRepositoryEntryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, LocalRepository> _localRepositoriesByRepositoryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByEntryId = new ConcurrentHashMap
			<Long, com.liferay.portal.kernel.repository.Repository>();
	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByRepositoryId = new ConcurrentHashMap
			<Long, com.liferay.portal.kernel.repository.Repository>();

}