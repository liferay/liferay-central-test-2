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
			long groupId, String name, String description, String portletId,
			int type, UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		return getRepositoryFactory().createRepository(
			groupId, name, description, portletId, type,
			typeSettingsProperties);
	}

	public static void deleteRepositories(long groupId, int purge)
		throws RepositoryException {

		getRepositoryFactory().deleteRepositories(groupId, purge);
	}

	public static void deleteRepository(long repositoryId, boolean purge)
		throws RepositoryException {

		getRepositoryFactory().deleteRepository(repositoryId, purge);
	}

	public static LocalRepository getLocalRepository(long repositoryId) {
		return getRepositoryFactory().getLocalRepository(repositoryId);
	}

	public static Repository getRepository(long repositoryId) {
		return getRepositoryFactory().getRepository(repositoryId);
	}

	public static RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	public static UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws RepositoryException {

		return getRepositoryFactory().getTypeSettingsProperties(repositoryId);
	}

	public static void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException {

		getRepositoryFactory().updateRepository(
			repositoryId, name, description, typeSettingsProperties);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private static RepositoryFactory _repositoryFactory;

}