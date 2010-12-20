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
public interface RepositoryFactory {

	public long createRepository(
			long companyId, long groupId, String name, String description,
			String portletKey, int type,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException;

	public void deleteRepositories(long companyId, long groupId, int purge)
		throws RepositoryException;

	public void deleteRepository(long repositoryId) throws RepositoryException;

	public LocalRepository getLocalRepository(long repositoryId);

	public Repository getRepository(long repositoryId);

	public UnicodeProperties getProperties(long repositoryId)
		throws RepositoryException;

	public void updateRepository(
			long repositoryId, String name, String description,
			UnicodeProperties typeSettingsProperties)
		throws RepositoryException;

}