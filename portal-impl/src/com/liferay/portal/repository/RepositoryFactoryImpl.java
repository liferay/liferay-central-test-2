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

import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	public long createRepository(
			long companyId, long groupId, String name, String description,
			String portletKey, int type,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		try {
			return RepositoryLocalServiceUtil.addRepository(
				companyId, groupId, name, description, portletKey, type,
				typeSettingsProperties);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void deleteRepositories(long companyId, long groupId, int purge)
		throws RepositoryException {

		try {
			RepositoryLocalServiceUtil.deleteRepositories(
				companyId, groupId, purge);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public void deleteRepository(long repositoryId) throws RepositoryException {
		try {
			RepositoryLocalServiceUtil.deleteRepository(repositoryId);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	public LocalRepository getLocalRepository(long repositoryId) {
		return new LiferayLocalRepository(repositoryId);
	}

	public Repository getRepository(long repositoryId) {
		return new LiferayRepository(repositoryId);
	}

	public UnicodeProperties getProperties(long repositoryId)
		throws RepositoryException {

		try {
			return RepositoryLocalServiceUtil.getProperties(repositoryId);
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
			RepositoryLocalServiceUtil.updateRepository(
				repositoryId, name, description, typeSettingsProperties);
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}

	}

}