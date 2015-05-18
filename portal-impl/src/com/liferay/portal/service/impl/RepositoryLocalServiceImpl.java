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

package com.liferay.portal.service.impl;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.documentlibrary.RepositoryNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Chow
 */
public class RepositoryLocalServiceImpl
	extends RepositoryLocalServiceBaseImpl implements CacheRegistryItem {

	@Override
	public Repository addRepository(
			long userId, long groupId, long classNameId, long parentFolderId,
			String name, String description, String portletId,
			UnicodeProperties typeSettingsProperties, boolean hidden,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long repositoryId = counterLocalService.increment();

		Repository repository = repositoryPersistence.create(repositoryId);

		repository.setUuid(serviceContext.getUuid());
		repository.setGroupId(groupId);
		repository.setCompanyId(user.getCompanyId());
		repository.setUserId(user.getUserId());
		repository.setUserName(user.getFullName());
		repository.setClassNameId(classNameId);
		repository.setName(name);
		repository.setDescription(description);
		repository.setPortletId(portletId);
		repository.setTypeSettingsProperties(typeSettingsProperties);
		repository.setDlFolderId(
			getDLFolderId(
				user, groupId, repositoryId, parentFolderId, name, description,
				hidden, serviceContext));

		repositoryPersistence.update(repository);

		try {
			RepositoryFactoryUtil.createRepository(repositoryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			throw new InvalidRepositoryException(e);
		}

		return repository;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addRepository(long, long,
	 *             long, long, String, String, String, UnicodeProperties,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Repository addRepository(
			long userId, long groupId, long classNameId, long parentFolderId,
			String name, String description, String portletId,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return addRepository(
			userId, groupId, classNameId, parentFolderId, name, description,
			portletId, typeSettingsProperties, false, serviceContext);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		CacheRegistryUtil.register(this);
	}

	@Override
	public void checkRepository(long repositoryId) {
		Group group = groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			return;
		}

		try {
			repositoryPersistence.findByPrimaryKey(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new InvalidRepositoryIdException(nsre.getMessage());
		}
	}

	@Override
	public void deleteRepositories(long groupId) {
		List<Repository> repositories = repositoryPersistence.findByGroupId(
			groupId);

		for (Repository repository : repositories) {
			deleteRepository(repository.getRepositoryId());
		}
	}

	@Override
	public Repository deleteRepository(long repositoryId) {
		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			repositoryLocalService.deleteRepository(repository);
		}

		_localRepositoriesByRepositoryId.remove(repositoryId);

		_repositoriesByRepositoryId.remove(repositoryId);

		return repository;
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public Repository deleteRepository(Repository repository) {
		expandoValueLocalService.deleteValues(
			Repository.class.getName(), repository.getRepositoryId());

		DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(
			repository.getDlFolderId());

		if (dlFolder != null) {
			dlFolderLocalService.deleteDLFolder(dlFolder);
		}

		repositoryPersistence.remove(repository);

		repositoryEntryPersistence.removeByRepositoryId(
			repository.getRepositoryId());

		return repository;
	}

	@Override
	public Repository fetchRepository(long groupId, String portletId) {
		return fetchRepository(groupId, portletId, portletId);
	}

	@Override
	public Repository fetchRepository(
		long groupId, String name, String portletId) {

		return repositoryPersistence.fetchByG_N_P(groupId, name, portletId);
	}

	@Override
	public List<LocalRepository> getGroupLocalRepositoryImpl(long groupId)
		throws PortalException {

		List<Repository> repositories = repositoryPersistence.findByGroupId(
			groupId);

		List<LocalRepository> localRepositories = new ArrayList<>(
			repositories.size() + 1);

		for (Repository repository : repositories) {
			localRepositories.add(
				getLocalRepositoryImpl(repository.getRepositoryId()));
		}

		localRepositories.add(getLocalRepositoryImpl(groupId));

		return localRepositories;
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(long repositoryId)
		throws PortalException {

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryId.get(repositoryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		localRepositoryImpl = RepositoryFactoryUtil.createLocalRepository(
			repositoryId);

		checkRepository(repositoryId);

		_localRepositoriesByRepositoryId.put(repositoryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId,
			long fileShortcutId)
		throws PortalException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId, fileShortcutId);

		return getLocalRepositoryImpl(repositoryId);
	}

	@Override
	public String getRegistryName() {
		return RepositoryLocalServiceImpl.class.getName();
	}

	@Override
	public Repository getRepository(long groupId, String portletId)
		throws PortalException {

		return getRepository(groupId, portletId, portletId);
	}

	@Override
	public Repository getRepository(long groupId, String name, String portletId)
		throws PortalException {

		return repositoryPersistence.findByG_N_P(groupId, name, portletId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long repositoryId)
		throws PortalException {

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByRepositoryId.get(repositoryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		repositoryImpl = RepositoryFactoryUtil.createRepository(repositoryId);

		checkRepository(repositoryId);

		_repositoriesByRepositoryId.put(repositoryId, repositoryImpl);

		return repositoryImpl;
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId,
			long fileShortcutId)
		throws PortalException {

		long repositoryId = getRepositoryId(
			folderId, fileEntryId, fileVersionId, fileShortcutId);

		return getRepositoryImpl(repositoryId);
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		return repository.getTypeSettingsProperties();
	}

	@Override
	public void invalidate() {
		_localRepositoriesByRepositoryId.clear();
		_repositoriesByRepositoryId.clear();
	}

	@Override
	public void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setName(name);
		repository.setDescription(description);

		repositoryPersistence.update(repository);

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
			repository.getDlFolderId());

		dlFolder.setName(name);
		dlFolder.setDescription(description);

		dlFolderPersistence.update(dlFolder);
	}

	@Override
	public void updateRepository(
			long repositoryId, UnicodeProperties typeSettingsProperties)
		throws PortalException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setTypeSettingsProperties(typeSettingsProperties);

		repositoryPersistence.update(repository);
	}

	protected long getDLFolderId(
			User user, long groupId, long repositoryId, long parentFolderId,
			String name, String description, boolean hidden,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new RepositoryNameException();
		}

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			user.getUserId(), groupId, repositoryId, true, parentFolderId, name,
			description, hidden, serviceContext);

		return dlFolder.getFolderId();
	}

	protected long getExternalRepositoryId(
		long folderId, long fileEntryId, long fileVersionId) {

		long repositoryEntryId = RepositoryUtil.getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(repositoryEntryId);

		if (repositoryEntry == null) {
			return 0;
		}

		return repositoryEntry.getRepositoryId();
	}

	protected long getInternalRepositoryId(
		long folderId, long fileEntryId, long fileVersionId,
		long fileShortcutId) {

		long repositoryId = 0;

		if (folderId != 0) {
			DLFolder dlFolder = dlFolderLocalService.fetchDLFolder(folderId);

			if (dlFolder != null) {
				if (dlFolder.isMountPoint()) {
					repositoryId = dlFolder.getGroupId();
				}
				else {
					repositoryId = dlFolder.getRepositoryId();
				}
			}
		}
		else if (fileEntryId != 0) {
			DLFileEntry dlFileEntry = dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId);

			if (dlFileEntry != null) {
				repositoryId = dlFileEntry.getRepositoryId();
			}
		}
		else if (fileVersionId != 0) {
			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.fetchDLFileVersion(fileVersionId);

			if (dlFileVersion != null) {
				repositoryId = dlFileVersion.getRepositoryId();
			}
		}
		else if (fileShortcutId != 0) {
			DLFileShortcut dlFileShortcut =
				dlFileShortcutLocalService.fetchDLFileShortcut(fileShortcutId);

			if (dlFileShortcut != null) {
				repositoryId = dlFileShortcut.getRepositoryId();
			}
		}
		else {
			throw new InvalidRepositoryIdException(
				"Missing a valid ID for folder, file entry, or file " +
					"version");
		}

		return repositoryId;
	}

	protected long getRepositoryId(
		long folderId, long fileEntryId, long fileVersionId,
		long fileShortcutId) {

		long repositoryId = getInternalRepositoryId(
			folderId, fileEntryId, fileVersionId, fileShortcutId);

		if (repositoryId != 0) {
			return repositoryId;
		}

		if (fileShortcutId != 0) {
			throw new IllegalArgumentException();
		}

		repositoryId = getExternalRepositoryId(
			folderId, fileEntryId, fileVersionId);

		if (repositoryId == 0) {
			throw new InvalidRepositoryIdException(
				String.format(
					"No folder or repository entry found with folder ID %s",
					folderId));
		}

		return repositoryId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryLocalServiceImpl.class);

	private final Map<Long, LocalRepository> _localRepositoriesByRepositoryId =
		new ConcurrentHashMap<>();
	private final Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByRepositoryId = new ConcurrentHashMap<>();

}