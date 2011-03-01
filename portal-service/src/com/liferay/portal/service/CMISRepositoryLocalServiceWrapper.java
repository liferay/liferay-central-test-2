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
 * This class is a wrapper for {@link CMISRepositoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CMISRepositoryLocalService
 * @generated
 */
public class CMISRepositoryLocalServiceWrapper
	implements CMISRepositoryLocalService {
	public CMISRepositoryLocalServiceWrapper(
		CMISRepositoryLocalService cmisRepositoryLocalService) {
		_cmisRepositoryLocalService = cmisRepositoryLocalService;
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _cmisRepositoryLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_cmisRepositoryLocalService.setIdentifier(identifier);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry toFileEntry(
		long repositoryId, java.lang.Object object)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _cmisRepositoryLocalService.toFileEntry(repositoryId, object);
	}

	public com.liferay.portal.kernel.repository.model.FileVersion toFileVersion(
		long repositoryId, java.lang.Object object)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _cmisRepositoryLocalService.toFileVersion(repositoryId, object);
	}

	public com.liferay.portal.kernel.repository.model.Folder toFolder(
		long repositoryId, java.lang.Object object)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _cmisRepositoryLocalService.toFolder(repositoryId, object);
	}

	public CMISRepositoryLocalService getWrappedCMISRepositoryLocalService() {
		return _cmisRepositoryLocalService;
	}

	public void setWrappedCMISRepositoryLocalService(
		CMISRepositoryLocalService cmisRepositoryLocalService) {
		_cmisRepositoryLocalService = cmisRepositoryLocalService;
	}

	private CMISRepositoryLocalService _cmisRepositoryLocalService;
}