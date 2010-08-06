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

/**
 * The utility for the user id mapper local service. This utility wraps {@link com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperLocalService
 * @see com.liferay.portal.service.base.UserIdMapperLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl
 * @generated
 */
public class UserIdMapperLocalServiceUtil {
	/**
	* Adds the user id mapper to the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapper the user id mapper to add
	* @return the user id mapper that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserIdMapper addUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserIdMapper(userIdMapper);
	}

	/**
	* Creates a new user id mapper with the primary key. Does not add the user id mapper to the database.
	*
	* @param userIdMapperId the primary key for the new user id mapper
	* @return the new user id mapper
	*/
	public static com.liferay.portal.model.UserIdMapper createUserIdMapper(
		long userIdMapperId) {
		return getService().createUserIdMapper(userIdMapperId);
	}

	/**
	* Deletes the user id mapper with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapperId the primary key of the user id mapper to delete
	* @throws PortalException if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserIdMapper(long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserIdMapper(userIdMapperId);
	}

	/**
	* Deletes the user id mapper from the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapper the user id mapper to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserIdMapper(userIdMapper);
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
	* Gets the user id mapper with the primary key.
	*
	* @param userIdMapperId the primary key of the user id mapper to get
	* @return the user id mapper
	* @throws PortalException if a user id mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserIdMapper getUserIdMapper(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMapper(userIdMapperId);
	}

	/**
	* Gets a range of all the user id mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user id mappers to return
	* @param end the upper bound of the range of user id mappers to return (not inclusive)
	* @return the range of user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserIdMapper> getUserIdMappers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMappers(start, end);
	}

	/**
	* Gets the number of user id mappers.
	*
	* @return the number of user id mappers
	* @throws SystemException if a system exception occurred
	*/
	public static int getUserIdMappersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMappersCount();
	}

	/**
	* Updates the user id mapper in the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapper the user id mapper to update
	* @return the user id mapper that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserIdMapper(userIdMapper);
	}

	/**
	* Updates the user id mapper in the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapper the user id mapper to update
	* @param merge whether to merge the user id mapper with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the user id mapper that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserIdMapper(userIdMapper, merge);
	}

	public static void deleteUserIdMappers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserIdMappers(userId);
	}

	public static com.liferay.portal.model.UserIdMapper getUserIdMapper(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMapper(userId, type);
	}

	public static com.liferay.portal.model.UserIdMapper getUserIdMapperByExternalUserId(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMapperByExternalUserId(type, externalUserId);
	}

	public static java.util.List<com.liferay.portal.model.UserIdMapper> getUserIdMappers(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdMappers(userId);
	}

	public static com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		long userId, java.lang.String type, java.lang.String description,
		java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUserIdMapper(userId, type, description, externalUserId);
	}

	public static UserIdMapperLocalService getService() {
		if (_service == null) {
			_service = (UserIdMapperLocalService)PortalBeanLocatorUtil.locate(UserIdMapperLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserIdMapperLocalService service) {
		_service = service;
	}

	private static UserIdMapperLocalService _service;
}