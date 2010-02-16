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
 * <a href="UserTrackerPathLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserTrackerPathLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathLocalService
 * @generated
 */
public class UserTrackerPathLocalServiceWrapper
	implements UserTrackerPathLocalService {
	public UserTrackerPathLocalServiceWrapper(
		UserTrackerPathLocalService userTrackerPathLocalService) {
		_userTrackerPathLocalService = userTrackerPathLocalService;
	}

	public com.liferay.portal.model.UserTrackerPath addUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.addUserTrackerPath(userTrackerPath);
	}

	public com.liferay.portal.model.UserTrackerPath createUserTrackerPath(
		long userTrackerPathId) {
		return _userTrackerPathLocalService.createUserTrackerPath(userTrackerPathId);
	}

	public void deleteUserTrackerPath(long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_userTrackerPathLocalService.deleteUserTrackerPath(userTrackerPathId);
	}

	public void deleteUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		_userTrackerPathLocalService.deleteUserTrackerPath(userTrackerPath);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portal.model.UserTrackerPath getUserTrackerPath(
		long userTrackerPathId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPath(userTrackerPathId);
	}

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPaths(start, end);
	}

	public int getUserTrackerPathsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPathsCount();
	}

	public com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.updateUserTrackerPath(userTrackerPath);
	}

	public com.liferay.portal.model.UserTrackerPath updateUserTrackerPath(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.updateUserTrackerPath(userTrackerPath,
			merge);
	}

	public java.util.List<com.liferay.portal.model.UserTrackerPath> getUserTrackerPaths(
		long userTrackerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userTrackerPathLocalService.getUserTrackerPaths(userTrackerId,
			start, end);
	}

	public UserTrackerPathLocalService getWrappedUserTrackerPathLocalService() {
		return _userTrackerPathLocalService;
	}

	private UserTrackerPathLocalService _userTrackerPathLocalService;
}