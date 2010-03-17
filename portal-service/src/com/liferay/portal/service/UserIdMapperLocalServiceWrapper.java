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
 * <a href="UserIdMapperLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserIdMapperLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapperLocalService
 * @generated
 */
public class UserIdMapperLocalServiceWrapper implements UserIdMapperLocalService {
	public UserIdMapperLocalServiceWrapper(
		UserIdMapperLocalService userIdMapperLocalService) {
		_userIdMapperLocalService = userIdMapperLocalService;
	}

	public com.liferay.portal.model.UserIdMapper addUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.addUserIdMapper(userIdMapper);
	}

	public com.liferay.portal.model.UserIdMapper createUserIdMapper(
		long userIdMapperId) {
		return _userIdMapperLocalService.createUserIdMapper(userIdMapperId);
	}

	public void deleteUserIdMapper(long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userIdMapperLocalService.deleteUserIdMapper(userIdMapperId);
	}

	public void deleteUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userIdMapperLocalService.deleteUserIdMapper(userIdMapper);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.UserIdMapper getUserIdMapper(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMapper(userIdMapperId);
	}

	public java.util.List<com.liferay.portal.model.UserIdMapper> getUserIdMappers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMappers(start, end);
	}

	public int getUserIdMappersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMappersCount();
	}

	public com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.updateUserIdMapper(userIdMapper);
	}

	public com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.updateUserIdMapper(userIdMapper, merge);
	}

	public void deleteUserIdMappers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userIdMapperLocalService.deleteUserIdMappers(userId);
	}

	public com.liferay.portal.model.UserIdMapper getUserIdMapper(long userId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMapper(userId, type);
	}

	public com.liferay.portal.model.UserIdMapper getUserIdMapperByExternalUserId(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMapperByExternalUserId(type,
			externalUserId);
	}

	public java.util.List<com.liferay.portal.model.UserIdMapper> getUserIdMappers(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.getUserIdMappers(userId);
	}

	public com.liferay.portal.model.UserIdMapper updateUserIdMapper(
		long userId, java.lang.String type, java.lang.String description,
		java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapperLocalService.updateUserIdMapper(userId, type,
			description, externalUserId);
	}

	public UserIdMapperLocalService getWrappedUserIdMapperLocalService() {
		return _userIdMapperLocalService;
	}

	private UserIdMapperLocalService _userIdMapperLocalService;
}