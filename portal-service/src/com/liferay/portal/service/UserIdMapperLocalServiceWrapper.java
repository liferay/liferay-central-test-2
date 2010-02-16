/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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