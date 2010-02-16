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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBStatsUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBStatsUserLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUserLocalService
 * @generated
 */
public class MBStatsUserLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBStatsUser addMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBStatsUser(mbStatsUser);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser createMBStatsUser(
		long statsUserId) {
		return getService().createMBStatsUser(statsUserId);
	}

	public static void deleteMBStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBStatsUser(statsUserId);
	}

	public static void deleteMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBStatsUser(mbStatsUser);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser getMBStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBStatsUser(statsUserId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getMBStatsUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBStatsUsers(start, end);
	}

	public static int getMBStatsUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBStatsUsersCount();
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBStatsUser(mbStatsUser);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateMBStatsUser(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBStatsUser(mbStatsUser, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser addStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addStatsUser(groupId, userId);
	}

	public static void deleteStatsUsersByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStatsUsersByGroupId(groupId);
	}

	public static void deleteStatsUsersByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStatsUsersByUserId(userId);
	}

	public static int getMessageCountByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMessageCountByUserId(userId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatsUser(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatsUsersByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> getStatsUsersByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatsUsersByUserId(userId);
	}

	public static int getStatsUsersByGroupIdCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStatsUsersByGroupIdCount(groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatsUser(groupId, userId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateStatsUser(
		long groupId, long userId, java.util.Date lastPostDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatsUser(groupId, userId, lastPostDate);
	}

	public static MBStatsUserLocalService getService() {
		if (_service == null) {
			_service = (MBStatsUserLocalService)PortalBeanLocatorUtil.locate(MBStatsUserLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBStatsUserLocalService service) {
		_service = service;
	}

	private static MBStatsUserLocalService _service;
}