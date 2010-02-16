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
 * <a href="PasswordTrackerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordTrackerLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTrackerLocalService
 * @generated
 */
public class PasswordTrackerLocalServiceWrapper
	implements PasswordTrackerLocalService {
	public PasswordTrackerLocalServiceWrapper(
		PasswordTrackerLocalService passwordTrackerLocalService) {
		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	public com.liferay.portal.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.addPasswordTracker(passwordTracker);
	}

	public com.liferay.portal.model.PasswordTracker createPasswordTracker(
		long passwordTrackerId) {
		return _passwordTrackerLocalService.createPasswordTracker(passwordTrackerId);
	}

	public void deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTrackerId);
	}

	public void deletePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTracker);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portal.model.PasswordTracker getPasswordTracker(
		long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTracker(passwordTrackerId);
	}

	public java.util.List<com.liferay.portal.model.PasswordTracker> getPasswordTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackers(start, end);
	}

	public int getPasswordTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackersCount();
	}

	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker);
	}

	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker,
			merge);
	}

	public void deletePasswordTrackers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTrackers(userId);
	}

	public boolean isSameAsCurrentPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isSameAsCurrentPassword(userId,
			newClearTextPwd);
	}

	public boolean isValidPassword(long userId, java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isValidPassword(userId,
			newClearTextPwd);
	}

	public void trackPassword(long userId, java.lang.String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.trackPassword(userId, encPassword);
	}

	public PasswordTrackerLocalService getWrappedPasswordTrackerLocalService() {
		return _passwordTrackerLocalService;
	}

	private PasswordTrackerLocalService _passwordTrackerLocalService;
}