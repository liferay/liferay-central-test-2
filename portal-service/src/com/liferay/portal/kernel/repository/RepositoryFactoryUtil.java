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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alexander Chow
 */
public class RepositoryFactoryUtil {

	public static long createRepository(
			long companyId, long groupId, String name, String description,
			String portletKey, int type,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		return getRepositoryFactory().createRepository(
			companyId, groupId, name, description, portletKey, type,
			typeSettingsProperties);
	}

	public static void deleteRepositories(
			long companyId, long groupId, int purge)
		throws RepositoryException {

		getRepositoryFactory().deleteRepositories(companyId, groupId, purge);
	}

	public static void deleteRepository(long repositoryId)
		throws RepositoryException {

		getRepositoryFactory().deleteRepository(repositoryId);
	}

	public static LocalRepository getLocalRepository(long repositoryId) {
		return getRepositoryFactory().getLocalRepository(repositoryId);
	}

	public static Repository getRepository(long repositoryId) {
		return getRepositoryFactory().getRepository(repositoryId);
	}

	public static UnicodeProperties getProperties(long repositoryId)
		throws RepositoryException {

		return getRepositoryFactory().getProperties(repositoryId);
	}

	public static void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		getRepositoryFactory().updateRepository(
			repositoryId, name, description, typeSettingsProperties);
	}

	public static RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private static RepositoryFactory _repositoryFactory;

}