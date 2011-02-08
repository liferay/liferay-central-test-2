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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RepositoryService
 * @generated
 */
public class RepositoryServiceWrapper implements RepositoryService {
	public RepositoryServiceWrapper(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	public long addRepository(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletId, int type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryService.addRepository(groupId, parentFolderId, name,
			description, portletId, type, typeSettingsProperties);
	}

	public void checkRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_repositoryService.checkRepository(repositoryId);
	}

	public void deleteRepositories(long groupId, int purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryService.deleteRepositories(groupId, purge);
	}

	public void deleteRepository(long repositoryId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryService.deleteRepository(repositoryId, purge);
	}

	public com.liferay.portal.model.Repository getRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryService.getRepository(repositoryId);
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryService.getTypeSettingsProperties(repositoryId);
	}

	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryService.updateRepository(repositoryId, name, description,
			typeSettingsProperties);
	}

	public RepositoryService getWrappedRepositoryService() {
		return _repositoryService;
	}

	public void setWrappedRepositoryService(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	private RepositoryService _repositoryService;
}