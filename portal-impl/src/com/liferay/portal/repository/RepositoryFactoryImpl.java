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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;

/**
 * @author Alexander Chow
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	public void checkRepository(long repositoryId) throws RepositoryException {
		try {
			RepositoryServiceUtil.checkRepository(repositoryId);
		}
		catch (SystemException se) {
			if (se instanceof RepositoryException) {
				throw (RepositoryException)se;
			}
			else {
				throw new RepositoryException(se);
			}
		}
	}

	public long createRepository(
			long groupId, String name, String description, String portletId,
			int type, UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		try {
			return RepositoryServiceUtil.addRepository(
				groupId, name, description, portletId, type,
				typeSettingsProperties);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void deleteRepositories(long groupId, int purge)
		throws RepositoryException {

		try {
			RepositoryServiceUtil.deleteRepositories(groupId, purge);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void deleteRepository(long repositoryId, boolean purge)
		throws RepositoryException {

		try {
			RepositoryServiceUtil.deleteRepository(repositoryId, purge);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public LocalRepository getLocalRepository(long repositoryId)
		throws RepositoryException {

		LocalRepository localRepository = new LiferayLocalRepository(
			repositoryId);

		checkRepository(repositoryId);

		return localRepository;
	}

	public LocalRepository getLocalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		LocalRepository localRepository = new LiferayLocalRepository(
			folderId, fileEntryId, fileVersionId);

		checkRepository(localRepository.getRepositoryId());

		return localRepository;
	}

	public Repository getRepository(long repositoryId)
		throws RepositoryException {

		Repository repository = new LiferayRepository(repositoryId);

		checkRepository(repositoryId);

		return repository;
	}

	public Repository getRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		Repository repository = new LiferayRepository(
			folderId, fileEntryId, fileVersionId);

		checkRepository(repository.getRepositoryId());

		return repository;
	}

	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws RepositoryException {

		try {
			return RepositoryServiceUtil.getTypeSettingsProperties(
				repositoryId);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		try {
			RepositoryServiceUtil.updateRepository(
				repositoryId, name, description, typeSettingsProperties);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

}