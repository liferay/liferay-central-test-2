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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link RepositoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RepositoryLocalService
 * @generated
 */
public class RepositoryLocalServiceWrapper implements RepositoryLocalService {
	public RepositoryLocalServiceWrapper(
		RepositoryLocalService repositoryLocalService) {
		_repositoryLocalService = repositoryLocalService;
	}

	/**
	* Adds the repository to the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to add
	* @return the repository that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Repository addRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.addRepository(repository);
	}

	/**
	* Creates a new repository with the primary key. Does not add the repository to the database.
	*
	* @param repositoryId the primary key for the new repository
	* @return the new repository
	*/
	public com.liferay.portal.model.Repository createRepository(
		long repositoryId) {
		return _repositoryLocalService.createRepository(repositoryId);
	}

	/**
	* Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryId the primary key of the repository to delete
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.deleteRepository(repositoryId);
	}

	/**
	* Deletes the repository from the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRepository(com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.deleteRepository(repository);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the repository with the primary key.
	*
	* @param repositoryId the primary key of the repository to get
	* @return the repository
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Repository getRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepository(repositoryId);
	}

	/**
	* Gets a range of all the repositories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of repositories to return
	* @param end the upper bound of the range of repositories to return (not inclusive)
	* @return the range of repositories
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Repository> getRepositories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositories(start, end);
	}

	/**
	* Gets the number of repositories.
	*
	* @return the number of repositories
	* @throws SystemException if a system exception occurred
	*/
	public int getRepositoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getRepositoriesCount();
	}

	/**
	* Updates the repository in the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to update
	* @return the repository that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.updateRepository(repository);
	}

	/**
	* Updates the repository in the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to update
	* @param merge whether to merge the repository with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the repository that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.updateRepository(repository, merge);
	}

	public long addRepository(long companyId, long groupId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletKey, int type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.addRepository(companyId, groupId, name,
			description, portletKey, type, typeSettingsProperties);
	}

	public void deleteRepositories(long companyId, long groupId, int purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.deleteRepositories(companyId, groupId, purge);
	}

	public void deleteRepository(long repositoryId, boolean purgeData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.deleteRepository(repositoryId, purgeData);
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _repositoryLocalService.getProperties(repositoryId);
	}

	public void updateRepository(long repositoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_repositoryLocalService.updateRepository(repositoryId, name,
			description, typeSettingsProperties);
	}

	public RepositoryLocalService getWrappedRepositoryLocalService() {
		return _repositoryLocalService;
	}

	public void setWrappedRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {
		_repositoryLocalService = repositoryLocalService;
	}

	private RepositoryLocalService _repositoryLocalService;
}