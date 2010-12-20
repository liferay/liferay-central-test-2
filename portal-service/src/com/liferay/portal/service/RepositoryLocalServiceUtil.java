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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the repository local service. This utility wraps {@link com.liferay.portal.service.impl.RepositoryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryLocalService
 * @see com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RepositoryLocalServiceImpl
 * @generated
 */
public class RepositoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RepositoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the repository to the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to add
	* @return the repository that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Repository addRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRepository(repository);
	}

	/**
	* Creates a new repository with the primary key. Does not add the repository to the database.
	*
	* @param repositoryId the primary key for the new repository
	* @return the new repository
	*/
	public static com.liferay.portal.model.Repository createRepository(
		long repositoryId) {
		return getService().createRepository(repositoryId);
	}

	/**
	* Deletes the repository with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param repositoryId the primary key of the repository to delete
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRepository(long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepository(repositoryId);
	}

	/**
	* Deletes the repository from the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepository(repository);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the repository with the primary key.
	*
	* @param repositoryId the primary key of the repository to get
	* @return the repository
	* @throws PortalException if a repository with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Repository getRepository(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepository(repositoryId);
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
	public static java.util.List<com.liferay.portal.model.Repository> getRepositories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositories(start, end);
	}

	/**
	* Gets the number of repositories.
	*
	* @return the number of repositories
	* @throws SystemException if a system exception occurred
	*/
	public static int getRepositoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRepositoriesCount();
	}

	/**
	* Updates the repository in the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to update
	* @return the repository that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRepository(repository);
	}

	/**
	* Updates the repository in the database. Also notifies the appropriate model listeners.
	*
	* @param repository the repository to update
	* @param merge whether to merge the repository with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the repository that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Repository updateRepository(
		com.liferay.portal.model.Repository repository, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRepository(repository, merge);
	}

	public static long addRepository(long companyId, long groupId,
		java.lang.String name, java.lang.String description,
		java.lang.String portletKey, int type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRepository(companyId, groupId, name, description,
			portletKey, type, typeSettingsProperties);
	}

	public static void deleteRepositories(long companyId, long groupId,
		int purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepositories(companyId, groupId, purge);
	}

	public static void deleteRepository(long repositoryId, boolean purgeData)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRepository(repositoryId, purgeData);
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties getProperties(
		long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProperties(repositoryId);
	}

	public static void updateRepository(long repositoryId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateRepository(repositoryId, name, description,
			typeSettingsProperties);
	}

	public static RepositoryLocalService getService() {
		if (_service == null) {
			_service = (RepositoryLocalService)PortalBeanLocatorUtil.locate(RepositoryLocalService.class.getName());

			ReferenceRegistry.registerReference(RepositoryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(RepositoryLocalService.class);
		}

		return _service;
	}

	public void setService(RepositoryLocalService service) {
		MethodCache.remove(RepositoryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(RepositoryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(RepositoryLocalService.class);
	}

	private static RepositoryLocalService _service;
}